package org.seckill.dao;

import java.util.Date;
import java.util.List;

import org.seckill.entity.Seckill;

/** 
 * @author jiangjia
 * @version 2018年9月4日 下午4:00:26
 */
public interface SeckillDao {

	/**
	 * 减库存，reduceNumber>1表示更新记录成功
	 * @param seckillId
	 * @param killTime
	 * @return
	 */
	public abstract int reduceNumber(long seckillId, Date killTime);

	/*alt+shift+J 快捷鍵注釋*/

	/**
	 * @param seckillid
	 * @return
	 */
	public abstract Seckill queryById(long seckillid);

	/**
	 * 根据偏移量查询秒杀商品偏移列表
	 * @param offet
	 * @param limit
	 * @return
	 */
	public abstract List<Seckill> queryAll(int offet, int limit);

}