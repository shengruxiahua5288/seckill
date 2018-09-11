package dao;

import org.apache.ibatis.annotations.Param;

import entity.SuccessKilled;



/** 
 * @author jiangjia
 * @version 2018年9月4日 下午1:57:34
 * alt+shift+J给方法加注释
 * dao主要是关注数据库的操作
 */


public interface SuccessKilledDao {
	
	/**
	 * 插入购物明细成功，并且过滤重复，联合主键可以过滤重复
	 * 插入行数的记录数，影响了几行
	 * @param seckillId
	 * @param userPhone
	 * @return
	 * 当多个参数时必须用@Param准确标明参数的对应和mybaties的参数进行对应
	 */
	int insertSuccesskilled(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone); 
	
    /**
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
	
}
