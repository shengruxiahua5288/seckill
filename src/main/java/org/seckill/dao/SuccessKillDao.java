package org.seckill.dao;

import org.seckill.entity.SuccessKilled;

/** 
 * @author jiangjia
 * @version 2018年9月4日 下午1:57:34
 * alt+shift+J给方法加注释
 * dao主要是关注数据库的操作
 */


public interface SuccessKillDao {
	
	/**
	 * 插入购物明细成功，并且过滤重复，联合主键可以过滤重复
	 * 插入行数的记录数，影响了几行
	 * @param seckillId
	 * @param userPhone
	 * @return
	 */
	int insertSuccesskilled(long seckillId,long userPhone); 
	
    /**
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(long seckillId);
	
}
