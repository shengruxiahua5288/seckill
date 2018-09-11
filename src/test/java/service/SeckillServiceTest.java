package service;

import dto.Exposer;
import dto.SeckillExcecution;
import entity.Seckill;
import enums.SeckillStatEnum;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
//测试类由junite4执行
@RunWith(SpringJUnit4ClassRunner.class)
//上下文配置告诉spring容器到这个路径下先找到这个两个配置文件并执行
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})

public class SeckillServiceTest {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill>list=seckillService.getSeckillList();
        for(int i=0;i<list.size();i++)
        {
        	 logger.info("getSeckillList()测试方法的结果集list:",list.get(i).toString());
        }
       
    }

    @Test
    public void getById() throws Exception {
        long id=1005L;
        Seckill seckill=seckillService.getById(id);
        logger.info("getById()方法的结果集seckill:",seckill.toString());
    }

//    @Test
//    public void testSeckillLogic() throws Exception {
//    	//集成秒杀测试先暴露url，然后获取暴露的md5传递到秒杀接口进行秒杀,测试代码逻辑要完整，并且注意可重复执行。集成测试业务层一定要覆盖全面并且逻辑清晰
//    	//就是各种情况的分支要覆盖全面
//        long id=1000l;
//        Exposer exposer=seckillService.exportSeckillUrl(id);
//    	long phone=12345678901L;
//    	String MD5 = null;
//    	
//        if(exposer.isExposed())
//        {
//        	//这里是秒杀开启了
//        	logger.info("exposer={}",exposer);
//        
//	         MD5=exposer.getMd5();
//	         try
//	         {
//	         SeckillExcecution execution=seckillService.executeSeckill(id,phone,MD5);
//	         logger.info("result={}",execution);
//	         }catch(RepeatKillException e)
//	         {
//	         	 logger.error("result={}",e.getMessage());
//	         }catch(SeckillCloseException e)
//	         {
//	         	 logger.error("result={}",e.getMessage());
//	         }
//        }else
//        {
//           //这里是秒杀未开启
//        	logger.warn("expose={}",exposer);
//        }
//       
//        
//    }

//    @Test
//    public void executeSeckill() throws Exception {
//        long id=1005l;
//        long phone=15112534641L;
//        //aee2cbdf18e61d1de455fc87244fd8ce
//        String md5="aee2cbdf18e61d1de455fc87244fd8ce";
//        try
//        {
//        SeckillExcecution execution=seckillService.executeSeckill(id,phone,md5);
//        logger.info("result={}",execution);
//        }catch(RepeatKillException e)
//        {
//        	 logger.error("result={}",e.getMessage());
//        }catch(SeckillCloseException e)
//        {
//        	 logger.error("result={}",e.getMessage());
//        }
//    }
    
   @Test
   public void excuteSeckillProcedure()throws Exception
   {
	   long id=1005L;
       Exposer exposer=seckillService.exportSeckillUrl(id);
       long phone=15112534641L;
   	   String MD5 = null;
   	
       if(exposer.isExposed())
       {
       	//这里是秒杀开启了
       	logger.info("exposer={}",exposer);
	    MD5=exposer.getMd5();
       }
	   else
	   {
		    Date killtime=new Date();
			try
		       {
				SeckillExcecution secke=seckillService.excuteSckillProcedure(id,phone,MD5);
				logger.info("excuteSeckillProcedure()方法的结果集secke:",secke.toString());
		       }catch(Exception e)
		       {
		       	 logger.error(e.getMessage(),e);
		       }
		}
       
	   
   }

}