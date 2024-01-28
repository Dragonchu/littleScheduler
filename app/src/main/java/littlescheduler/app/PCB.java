package littlescheduler.app;


public class PCB {
	private String name;
	private int time;
	private int priority;
	private String state;
	private int processor = 0;
	public int getMul() {
		return mul;
	}
	public void setMul(int mul) {
		this.mul = mul;
	}

	private int mul = 1;
	public int getMemorysize() {
		return memorysize;
	}
	public void setMemorysize(int memorysize) {
		this.memorysize = memorysize;
	}
	public int getMemory_start_address() {
		return memory_start_address;
	}
	public void setMemory_start_address(int memory_start_address) {
		this.memory_start_address = memory_start_address;
	}

	private int memorysize;
	private int memory_start_address = -1;
	//get set 方法
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	//进程运行
	public void processrun() {
		this.priority -=1;
		this.time -=1;
	}
	public String getmessage() {
		return this.name+" "+"优先级"+Integer.toString(this.priority)+" "+"所需时间"+Integer.toString(this.time)+" "+"大小"+Integer.toString(this.memorysize)+"MB"+" "+"起始地址"+Integer.toString(this.memory_start_address);
	}
	
	public String messageforrunning() {
		if (this.mul == 1) {
			return this.name+" "+"优先级"+Integer.toString(this.priority)+" "+"所需时间"+Integer.toString(this.time)+" "+"大小"+Integer.toString(this.memorysize)+"MB"+" "+"起始地址"+Integer.toString(this.memory_start_address)+"处理机1";
		}
		else {
			return this.name+" "+"优先级"+Integer.toString(this.priority)+" "+"所需时间"+Integer.toString(this.time)+" "+"大小"+Integer.toString(this.memorysize)+"MB"+" "+"起始地址"+Integer.toString(this.memory_start_address)+"处理机2";
		}
	}
}
