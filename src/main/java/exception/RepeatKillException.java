package exception;
/** 
 * @author jiangjia
 * @version 2018年9月6日 下午2:37:03
 * 重复秒杀异常（运行时异常）
 */
public class RepeatKillException extends SeckillException {

	public RepeatKillException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public RepeatKillException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
