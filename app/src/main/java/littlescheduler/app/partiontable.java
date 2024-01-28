package littlescheduler.app;
//内存分区表
public class partiontable {
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getStartaddress() {
		return startaddress;
	}
	public void setStartaddress(int startaddress) {
		this.startaddress = startaddress;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public partiontable(int number,int size,int startaddress,int state) {
		this.number = number;
		this.size = size;
		this.startaddress = startaddress;
		this.state = state;
	}
	private int number;
	private int size;
	private int startaddress;
	private int state;
	
}
