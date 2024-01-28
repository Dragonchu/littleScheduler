package littlescheduler.app;


import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class MyComparator implements Comparator<PCB> {
	public int compare(PCB left,PCB right) {
		PCB lPcb =(PCB)left;
		PCB rpcb =(PCB)right;
		return rpcb.getPriority()-lPcb.getPriority();
	}
	public static void priority_sort(Vector<PCB> list) {
		Comparator<PCB> ct = new MyComparator();
		Collections.sort(list,ct);
	}
}
