package dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.SeckillDao;
import entity.Seckill;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 配置spring 和junit整合，junit启动时加载springIOC容器
 * spring-test,junit
 * 告诉junit spring 配置文件
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;
    @Test
    public void reduceNumber() throws Exception {
        Date killTime=new Date();
        int updateCount=seckillDao.reduceNumber(1000L,killTime);
        System.out.println("updateCount="+updateCount);
    }

    @Test
    public void queryById() throws Exception {
        long id=1000;
        Seckill seckill=seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
       // Seckill [seckillId=1000, name=1000元秒杀iphoneX, number=100, startTime=Thu Sep 13 05:34:00 CST 2018, endTime=Fri Nov 02 00:00:00 CST 2018, createTime=Thu Aug 02 11:24:22 CST 2018]
    }

    @Test
    public void queryAll() throws Exception {
    	//如果这样写会报一个错误：
    	//org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.binding.BindingException: Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
        //List<Seckill> queryAll(int offet, int limit); java编译以后没有保存形参的记录，List<Seckill> queryAll(int offet, int limit)->queryAll(arg0,arg1)
    	//传递多个参数给mybaties，要告诉mybaties哪个参数是对应哪个，要说明
    	List<Seckill> seckills=seckillDao.queryAll(0,100);
        for(Seckill seckill:seckills){
            System.out.println(seckill);
        }
    }
    
    @Test
    public void killByProducedure()throws Exception{
    	    Date killtime=new Date();
			Map<String,Object>map=new HashMap<String,Object>();
			//执行数据库的存储过程
			map.put("seckillId",1005L);
			map.put("userPhone",15112534641L);
			map.put("killTime",killtime);
			//未执行之前，返回的结果为null
			map.put("result",null);
    	    seckillDao.killByProducedure(map);
    	    System.out.print("killByProducedure()"+map);
        
    }

}