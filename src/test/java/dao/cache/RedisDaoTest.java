/**
 * 
 */
package dao.cache;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
import dao.SeckillDao;
import entity.Seckill;

/** 
 * @author jiangjia
 * @version 2018年9月7日 下午5:03:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
	private long id=1001;
   //测试redisdao
    @Autowired
    private RedisDao redisDao;
	@Autowired
	private SeckillDao seckillDao;
	@Test
	public void TestSeckill() throws Exception
	{
		//测试get and put
        Seckill seckill=redisDao.getSeckill(id);
        if(seckill==null){
            seckill = seckillDao.queryById(id);
            if(seckill!=null){
            	//把对象放入缓存
                String result=redisDao.putSeckill(seckill);
                System.out.println(result);
                //再从缓存中拿seckill对象
                seckill=redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
		
	}

	@Test
    public void testConection() {
        Jedis jedis = new Jedis("127.0.0.1");
        System.out.println("Connection to server sucessfully");
        // 查看服务是否运行
        System.out.println("Server is running: " + jedis.ping());
    }

    @Test
    public void testConectionWithPassword() {
        Jedis jedis = new Jedis("127.0.0.1");
       // jedis.auth("ggb654321");
        System.out.println("Connection to server sucessfully");
        // 查看服务是否运行
        System.out.println("Server is running: " + jedis.ping());
    }

}
