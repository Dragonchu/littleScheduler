package scheduler;

public class partionmessage {
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	private int number;
	private int startaddress;
	public int getStartaddress() {
		return startaddress;
	}
	public void setStartaddress(int startaddress) {
		this.startaddress = startaddress;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	private int size;
	private int state;
	public partionmessage(int number,int startaddress,int size,int state) {
		this.number = number;
		this.startaddress = startaddress;
		this.size = size;
		this.state = state;
	}
}
