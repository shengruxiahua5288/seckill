package dao.cache;


import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import entity.Seckill;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisDao {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    //客户端缓冲池
    private final JedisPool jedisPool;

    public RedisDao(String ip,int port){
        jedisPool =new JedisPool(ip,port);
    }
    //schema是相当于一个模式，根据相关模式赋予一定的值，就是按照Seckill.class的模式赋予相关的值，然后当创建一个对象时，就会根据模式反射性的拿到赋予一定的值
    private RuntimeSchema<Seckill> schema=RuntimeSchema.createFrom(Seckill.class);
   //当缓存有，就直接getSeckill
    public Seckill getSeckill(long seckillId){
        //redis操作逻辑
        try {
            Jedis jedis=jedisPool.getResource();
            //先不进行验证
           // jedis.auth("ggb654321");
            try{
            	//之所以key要加一个前缀，因为redis可以缓存很多对象，加个前缀用来区分其它的缓存类的对象，比如seckill+seckillId来获取不同的redis缓存对象
                String key="seckill:"+seckillId;
                /*jedis客户端并没有实现内部序列化操作
                * 在存入redis前需要将对象序列化，取出时反序列化成对象
                * 由于Java自身serilize效率不高，采用自定义序列化方法提高效率*/
                //protostuff:pojo
                //对象存放在redis以字节数组形式,二进制存储
                byte[] bytes=jedis.get(key.getBytes());
                //缓存中获取到
                if(bytes!=null){
                    //先给一个空对象，根据模式先给一个空对象，根据空对象然后建立一个seckill对象
                    Seckill seckill=schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    //seckill被反序列化
                    return seckill;
                }

            }finally {
                jedis.close();
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
 //缓存没有，就putSeckill
    public String putSeckill(Seckill seckill){
        //set Object(Seckill)->序列化->byte[]
        try{
            Jedis jedis=jedisPool.getResource();
            //jedis认证,不要发送认证请求，如果要发送认证请求，那么认证请求的密码一定要和redis服务器中配置文件redis.windows.conf里设置的密码要一致。
           // jedis.auth("ggb654321");
            try{
                String key="seckill:"+seckill.getSeckillId();
                byte[] bytes=ProtostuffIOUtil.toByteArray(seckill,schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //设置超时缓存
                int timeout=60 * 60;//秒为单位，即缓存1小时，缓存不会永久缓存
                String result=jedis.setex(key.getBytes(),timeout,bytes);
                return result;
            }finally {
                jedis.close();
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

}
