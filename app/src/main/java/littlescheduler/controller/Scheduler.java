package littlescheduler.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import littlescheduler.model.PCB;
import littlescheduler.model.PCBComparator;

public class Scheduler {
    private static final int CHANNEL = 2;
    private final PriorityQueue<PCB> readyList = new PriorityQueue<>(new PCBComparator());
    private final List<PCB> waitList = new ArrayList<>();

    public void addPcb(PCB pcb) {
        if (readyList.size() < CHANNEL) {
            //向ready队列中添加新建进程
            readyList.offer(pcb);
        } else {
            //将进程加入后备队列
            waitList.add(pcb);
        }
    }
}
