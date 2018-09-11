package dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import entity.Seckill;



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
	 * 用@Param主要是告诉mybaties多个参数传递时的一一对应
	 */
	public abstract int reduceNumber(@Param("seckillId")long seckillId, @Param("killTime")Date killTime);

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
	public abstract List<Seckill> queryAll(@Param("offset")int offset, @Param("limit")int limit);

    public abstract void killByProducedure(Map<String,Object>map);
}