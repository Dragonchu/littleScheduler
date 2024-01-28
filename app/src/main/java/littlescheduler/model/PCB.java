package littlescheduler.model;

import lombok.Data;

@Data
public class PCB {
    private String name;
    private int time;
    private int priority;
    private String state;
    private int mul = 1;
    private int memorySize;
    private int memoryStartAddress = -1;

    //进程运行
    public void processRun() {
        this.priority -= 1;
        this.time -= 1;
    }

    public String getMessage() {
        return this.name + " " + "优先级" + this.priority + " " + "所需时间" + this.time + " " + "大小" + this.memorySize + "MB" + " " + "起始地址" + this.memoryStartAddress;
    }

    public String messageForRunning() {
        if (this.mul == 1) {
            return this.name + " " + "优先级" + this.priority + " " + "所需时间" + this.time + " " + "大小" + this.memorySize + "MB" + " " + "起始地址" + this.memoryStartAddress + "处理机1";
        } else {
            return this.name + " " + "优先级" + this.priority + " " + "所需时间" + this.time + " " + "大小" + this.memorySize + "MB" + " " + "起始地址" + this.memoryStartAddress + "处理机2";
        }
    }
}
