package littlescheduler.app;

import java.util.PriorityQueue;
import littlescheduler.model.PCB;
import littlescheduler.model.PCBComparator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PCBTest {
    @Test
    public void compareTest() {
        PriorityQueue<PCB> readyList = new PriorityQueue<>(new PCBComparator());
        PCB pcbFirst = new PCB();
        pcbFirst.setPriority(10);
        PCB pcbSecond = new PCB();
        pcbSecond.setPriority(5);
        PCB pcbThird = new PCB();
        pcbThird.setPriority(8);

        readyList.offer(pcbFirst);
        readyList.offer(pcbSecond);
        readyList.offer(pcbThird);

        Assertions.assertAll(
            () -> {
                Assertions.assertEquals(readyList.poll(), pcbFirst);
                Assertions.assertEquals(readyList.poll(), pcbThird);
                Assertions.assertEquals(readyList.poll(), pcbSecond);
            }
        );
    }
}
