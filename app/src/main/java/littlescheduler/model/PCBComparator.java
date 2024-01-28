package littlescheduler.model;


import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class PCBComparator implements Comparator<PCB> {
    public int compare(PCB left, PCB right) {
        PCB lPcb = left;
        PCB rpcb = right;
        return rpcb.getPriority() - lPcb.getPriority();
    }

    public static void priority_sort(Vector<PCB> list) {
        Comparator<PCB> ct = new PCBComparator();
        Collections.sort(list, ct);
    }
}
