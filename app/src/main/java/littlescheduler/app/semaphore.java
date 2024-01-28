package littlescheduler.app;
//信号量
public class semaphore {
	public String getPrecursor() {
		return precursor;
	}
	public void setPrecursor(String precursor) {
		this.precursor = precursor;
	}
	public String getSubsequent() {
		return subsequent;
	}
	public void setSubsequent(String subsequent) {
		this.subsequent = subsequent;
	}
	public int getIsPredone() {
		return isPredone;
	}
	public void setIsPredone(int isPredone) {
		this.isPredone = isPredone;
	}
	private String precursor;
	private String subsequent;
	public int isPredone;

}
