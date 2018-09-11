package enums;
/** 
 * @author jiangjia
 * @version 2018年9月6日 下午2:20:33
 * 使用枚举表诉常量数据字段
 */
public enum SeckillStatEnum {
	SUCCESS(1,"秒杀成功"),
	END(0,"秒杀结束"),
	REPEATE_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改");
	private int state;
	
	private String stateInfo;
	//定义枚举类型的形式对(整型，字符串)
	SeckillStatEnum(int state,String stateInfo){
		this.state=state;
		this.stateInfo=stateInfo;
	}
	public int getState(){
		return state;
	}
	public String getStateInfo() {
        return stateInfo;
    }
	
	 public static SeckillStatEnum stateOf(int index){
	        for(SeckillStatEnum state:values()){
	            if(state.getState()==index) {
	                return state;
	            }
	        }
	        return null;
	    }
}
