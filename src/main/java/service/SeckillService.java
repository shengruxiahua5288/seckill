package service;

import java.util.List;

import dto.Exposer;
import dto.SeckillExcecution;
import entity.Seckill;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;

/** 
 * @author jiangjia
 * @version 2018年9月6日 下午1:49:18
 * 业务接口：站在"使用者"角度设计接口
 * 三个方面：方法定义粒度，定义的粒度尽量细，参数尽量少，返回类型（return 类型要友好），抛出异常
 * 
 */
public interface SeckillService {
	
	/**
	 * 返回所有秒杀记录
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 查询单个秒杀记录
	 * @param seckill_id
	 * @return
	 */
	Seckill getById(long seckill_id);
	
	/**
	 * 秒杀开启时，输出秒杀url地址，否则输出系统时间和秒杀时间，当秒杀还没开启时，谁也不知道我们的秒杀地址，这样别人不会提前开启插件攻击秒杀，因为秒杀要动态给与url
	 * 不要让别人提前猜中你的秒杀url而进行攻击。
	 */
	 Exposer exportSeckillUrl(long seckillId);
	 
	 /**
	   * 执行秒杀操作，MD5加密字符串，这个秒杀要进行验证，如果传递进来的MD5加密和内部的加密规则不匹配，表示被篡改，拒绝秒杀,返回的结果封装成一个返回类
	  */

	 SeckillExcecution executeSeckill(long seckillId,long userPhone,String md5)
	    throws SeckillException, SeckillCloseException, RepeatKillException;
	
	    /**
	     * @param seckillId
	     * @param userPhone
	     * @param md5
	     * 通过存储过程执行秒杀操作
	     * @return 
	     */
	 SeckillExcecution excuteSckillProcedure(long seckillId,long userPhone,String md5)
			 throws SeckillException, SeckillCloseException, RepeatKillException;
}
