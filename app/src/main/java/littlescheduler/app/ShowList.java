package littlescheduler.app;

import java.util.ListIterator;
import java.util.Vector;
import javax.swing.JList;
import littlescheduler.model.PCB;

public class ShowList {
    public static void show(JList<String> show_on_JList, Vector<PCB> show_list) {
        Vector<String> ready_process = new Vector<>();
        ListIterator<PCB> lIterator = show_list.listIterator();
        while (lIterator.hasNext()) {
            ready_process.add(lIterator.next().getMessage());
        }
        show_on_JList.setListData(ready_process);
    }

}
