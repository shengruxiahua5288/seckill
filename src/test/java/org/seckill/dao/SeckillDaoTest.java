/**
 * 
 */
package org.seckill.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.seckill.entity.Seckill;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** 
 * @author jiangjia
 * @version 2018年9月4日 下午4:14:10
 * 配置spring和junit整合，junit启动时加载springIOC容器
 * spring-test,junit
 */

//告诉junit spring配置文件
public class SeckillDaoTest {

	/**
	 * 减库存，reduceNumber>1表示更新记录成功
	 * @param seckillId
	 * @param killTime
	 * @return
	 */
	@Test
	public void TestreduceNumber(long seckillId, Date killTime) throws Exception
	{
		
	}

	/*alt+shift+J 快捷鍵注釋*/

	/**
	 * @param seckillid
	 * @return
	 */
	@Test
	public void TestqueryById(long seckillid)throws Exception
	{
		
	}

	/**
	 * 根据偏移量查询秒杀商品偏移列表
	 * @param offet
	 * @param limit
	 * @return
	 */
	@Test
	public void TestqueryAll(int offet, int limit)throws Exception
	{
		
	}

}
