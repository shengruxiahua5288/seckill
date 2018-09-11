package dto;
/** 
 * @author jiangjia
 * @version 2018年9月6日 下午2:01:20
 * 暴露秒杀接口，就是数据传输层，暴露数据给用户
 */
public class Exposer {
	//是否暴露数据
	private boolean exposed;
	//是否md5加密
	private String md5;
	private long seckillId;
	private long now;
	private long start;
	private long end;
	public Exposer(boolean exposed, String md5, long seckillId) {

		this.exposed = exposed;
		this.md5 = md5;
		this.seckillId = seckillId;
	}
	public Exposer(boolean exposed, long seckillId, long now, long start,
			long end) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
		this.now = now;
		this.start = start;
		this.end = end;
	}
	public Exposer(boolean exposed, long seckillId) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
	}
	@Override
	public String toString() {
		return "Exposer [exposed=" + exposed + ", md5=" + md5 + ", seckillId="
				+ seckillId + ", now=" + now + ", start=" + start + ", end="
				+ end + "]";
	}
	public boolean isExposed() {
		return exposed;
	}
	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public long getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}
	public long getNow() {
		return now;
	}
	public void setNow(long now) {
		this.now = now;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	
	

}
