package service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;






import dao.SeckillDao;
import dao.SuccessKilledDao;
import dao.cache.RedisDao;
import dto.Exposer;
import dto.SeckillExcecution;
import entity.Seckill;
import entity.SuccessKilled;
import enums.SeckillStatEnum;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;
import service.SeckillService;

/** 
 * @author jiangjia
 * @version 2018年9月6日 下午2:55:06
 * 实现这个接口，主要是实现这个类中的接口
 * @service 表示业务层组件
 * @Autowired 就相当于自动注入Serivice依赖，常见的注入依赖还有:
 * @Inject,@Resource
 */
/**
 * @author D-D
 *
 */
@Service
public class SeckillServiceImpl implements SeckillService{
    //用org.slf4j中的日志类来打印日志
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	@Autowired
	private RedisDao redisDao;
	//MD5 盐值字符串，用于混淆md5,越复杂越好
	private final String slat="dsgdsgfgdf&*%^$^%@$&";
	public SeckillServiceImpl() {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public List<Seckill> getSeckillList() {
		// TODO Auto-generated method stub
		
		return seckillDao.queryAll(0,6);
	}

	@Override
	public Seckill getById(long seckill_id) {
		// TODO Auto-generated method stub
		
		logger.info("类SeckillServiceImpl"
				+ "方法getById():",seckillDao.queryById(seckill_id)+"seckillDao"+seckillDao);
		return seckillDao.queryById(seckill_id);
	}

	@Override
	 public Exposer exportSeckillUrl(long seckillId) {
		// TODO Auto-generated method stub
		//获取一个seckill实体
		Seckill seckill=redisDao.getSeckill(seckillId);
		if(seckill==null)
		{
			//这个说明redisDao缓存里面没有该秒杀产品的缓存，所以访问数据库去读取秒杀产品
			seckill=seckillDao.queryById(seckillId);
			if(seckill==null)
			{
				//如果数据库里面也没有，抛出异常，并且返回要暴露的数据给客户端,false表示不开启秒杀，因为已经抛异常了
				return new Exposer(false,seckillId);
			}
			else
			{
				//放入redis
				redisDao.putSeckill(seckill);
			}
		}
		//引用java.util包里面的Date
		Date startTime=seckill.getStartTime();
		Date endTime=seckill.getEndTime();
		Date nowTime=new Date();
		if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime())
		{
			//表示秒杀时间还没开始或者秒杀时间还没结束,不开启秒杀，返回结果
			 return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime()
			            ,endTime.getTime());
		}
		//转化为特定字符串，过程，不可逆
		String md5=getMD5(seckillId);
		//开启秒杀，并且返回seckillId
		return new Exposer(true,md5,seckillId);
		
	}
	private String getMD5(long seckillId)
	{
		//加点盐混淆，MD5加密不可逆，希望用户不要猜到结果
        String base=seckillId+"/"+slat;
        //spring自带的md5生成方法
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
	}

	/* (non-Javadoc)
	 * @see service.SeckillService#executeSeckill(long, long, java.lang.String)
	 * 执行秒杀
	 *  使用注解控制事务方法的优点：
     * 1：开发团队达成一致约定，明确标注事务方法的编程风格
     * 2：保证事务方法的执行时间尽可能短，不要穿插其他的网咯操作，RPC/HTTP请求/或者剥离到事务方法外
     * 3：不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制。
     * 4:加上Transactional注解表示该方法是在事物的控制下完成，要么执行成功，要么执行失败
     * 5、数据库一旦用不好会导致数据库阻塞和延迟
     */
	
	@Override
	@Transactional
	public SeckillExcecution executeSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, SeckillCloseException,
			RepeatKillException {
		// TODO Auto-generated method stub
		if(md5==null||!md5.equals(getMD5(seckillId)))
		{
			//如果md5加密已经被篡改了，则表明不能执行秒杀
			return new SeckillExcecution(seckillId,SeckillStatEnum.DATA_REWRITE);
			
		}
		Date nowTime=new Date();
		try
		{
			//开启秒杀，记录购买行为，先执行insert再执行秒杀，只有一步的网络延迟，因为先检查该用户有没有对该商品进行过秒杀，如果已经进行过秒杀了，就不要重复秒杀了，也不要执行秒杀的动作了
			//如果一上来就执行秒杀的动作，然后客户秒杀成功了，然后插入数据库，才发现该用户已经进行过秒杀了，这样做了多余的步骤会影响性能。有时sql语句的逻辑执行先后顺序也会影响性能，能够减少数据库的操作步骤
			//尽量要减少，虽然有时调换sql操作的先后顺序逻辑都能达到目的，但是优化效果就没那么好了，中间要考虑到尽量少的步骤。
			//当然进一步的优化尽量将事物直接在mysql的客户端写存储过程，这样中间少了很多因为spring框架层的封装而影响的性能，因为本身框架封装的越好，那么编译时耗时就越多，内存中占用的框架层的对象也会很多
			//这样更加容易触发GC,gc太频繁就会影响性能瓶颈
			int insertCount=successKilledDao.insertSuccesskilled(seckillId, userPhone);
			//唯一的seckillId ,uerPhone,//重复秒杀,insert插入时会被ignore
			if(insertCount<=0)
			{
				throw new RepeatKillException("seckill repeat");
			}
			else
			{
				int updateCount=seckillDao.reduceNumber(seckillId, nowTime);
				if(updateCount<=0){
					//减库存失败，说明该产品已经不在秒杀的活动中了
					throw new SeckillCloseException("seckill is closed");
				}
				else{
					//秒杀成功
					SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					//返回秒杀成功的记录
					return new SeckillExcecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
				}
			}
		}catch (SeckillCloseException e1)
		{ 
			//先优先抛出自定义的异常
			throw e1;
		}catch(RepeatKillException e2)
		{
			throw e2;
		}catch(Exception e)
		{
			//运行时的其它异常，捕获出来，并且打印出日志信息，所有异常都变成了运行器异常
			 logger.error(e.getMessage(), e);
             throw new SeckillException("seckill inner error: " + e.getMessage());
		}
		
	}
	//运行时异常一定会回滚
	
	//
    @Override
	public SeckillExcecution excuteSckillProcedure(long seckillId,long userPhone,String md5)
			throws SeckillException, SeckillCloseException, RepeatKillException 
	{
		if(md5==null||!md5.equals(getMD5(seckillId)))
		{
			//如果md5加密已经被篡改了，则表明不能执行秒杀
			return new SeckillExcecution(seckillId,SeckillStatEnum.DATA_REWRITE);
		}
		else
		{
		    Date killtime=new Date();
			Map<String,Object>map=new HashMap<String,Object>();
			//执行数据库的存储过程
			map.put("seckillId",seckillId);
			map.put("userPhone",userPhone);
			map.put("killTime",killtime);
			//未执行之前，返回的结果为null
			map.put("result",null);
		
				try
				{
					seckillDao.killByProducedure(map);
					//默认值为-2
					int result=MapUtils.getIntValue(map, "result", -2);
					if(result==1)
					{
						//result=1，表示执行存储过程成功，然后返回秒杀成功的记录
						SuccessKilled  successkilled=successKilledDao.queryByIdWithSeckill(MapUtils.getLongValue(map, "seckillId"),MapUtils.getLongValue(map,"userPhone"));
	
	                    return new SeckillExcecution(seckillId,SeckillStatEnum.SUCCESS,successkilled);
					}
			
					else
					{
					   return new SeckillExcecution(seckillId,SeckillStatEnum.stateOf(result));
					}
				}catch (SeckillCloseException e1)
				{ 
					//先优先抛出自定义的异常
					throw e1;
				}catch(RepeatKillException e2)
				{
					throw e2;
				}catch(Exception e)
				{
					//运行时的其它异常，捕获出来，并且打印出日志信息，所有异常都变成了运行器异常
					 logger.error(e.getMessage(), e);
		             throw new SeckillException("seckill inner error: " + SeckillStatEnum.INNER_ERROR);
				}
				
			
		}
	}
}
