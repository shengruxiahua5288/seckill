package dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import entity.SuccessKilled;




/** 
 * @author jiangjia
 * @version 2018年9月5日 下午5:45:05
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;
    @Test
    public void insertSuccessKilled() throws Exception {
        long id=1005L;
        long phone=15112534641L;
        int insertCount=successKilledDao.insertSuccesskilled(id,phone);
        System.out.println("insertCount="+insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id=1005L;
        long phone=15112534641L;
        SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(id,phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
        
        //查询出来的结果是:
        //SuccessKilled [seckillId=1005, userPhone=15112534641, state=0, createTime=Thu Sep 06 11:40:31 CST 2018, seckill=Seckill [seckillId=1005, name=99元秒杀iphone50, number=98, startTime=Sun Aug 05 16:02:57 CST 2018, endTime=Fri Nov 02 00:00:00 CST 2018, createTime=Thu Sep 06 11:40:31 CST 2018]]
        //Seckill [seckillId=1005, name=99元秒杀iphone50, number=98, startTime=Sun Aug 05 16:02:57 CST 2018, endTime=Fri Nov 02 00:00:00 CST 2018, createTime=Thu Sep 06 11:40:31 CST 2018]

    }

}
