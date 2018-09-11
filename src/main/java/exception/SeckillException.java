package exception;

import entity.SuccessKilled;
import enums.SeckillStatEnum;

/** 
 * @author jiangjia
 * @version 2018年9月6日 下午2:33:12
 * 抛出运行器异常，事物才会回滚，要搞清楚哪些异常抛出被捕获了，事物会回滚，其它异常可以继承这个基本异常进行抛出，虽然异常继承是差不多，但是用继承来
 * 区分不同种类的异常，这样让客户一看就知道是什么异常
 */
public class SeckillException extends RuntimeException {

	public SeckillException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
   //参数arg0相当于丢出异常的信息
	public SeckillException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
}
