package littlescheduler.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


//wdnmd这一坨屎山
public class ScheduleFrame extends JFrame {
    private static final long serialVersionUID = 2760825274877778046L;
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    private static final int TIME_SLICE = 30;
    private static final int CHANNEL = 2;
    private static int tempcheck = 1;
    private Vector<Semaphore> synVector = new Vector<>();

    Vector<PCB> ready_list = new Vector<PCB>();
    Vector<PCB> running_list = new Vector<PCB>();
    Vector<PCB> running_list2 = new Vector<PCB>();
    Vector<PCB> wait_list = new Vector<PCB>();
    Vector<PCB> suspend_list = new Vector<PCB>();
    Vector<PartitionTable> partiontables_list = new Vector<>();
    Vector<String> memory_list = new Vector<>();
    boolean reset = false;

    public ScheduleFrame() {
        initialpartiontable();
        //设置窗体的基本属性
        this.setTitle("调度控制台");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        //屏幕居中
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setLocation((screenWidth - DEFAULT_WIDTH) / 2, (screenHeight - DEFAULT_HEIGHT) / 2);

        //设置绝对布局
        JPanel contentJPanel = new JPanel();
        contentJPanel.setLayout(null);

        //输入进程名的文本框
        JLabel nameJLabel = new JLabel("进程名");
        JTextField nameArea = new JTextField();
        contentJPanel.add(nameJLabel);
        contentJPanel.add(nameArea);
        nameJLabel.setBounds(5, 15, 50, 15);
        nameArea.setBounds(50, 13, 100, 20);

        //输入要求运行时间的文本框
        JLabel timeJLabel = new JLabel("运行时间");
        JTextField timeArea = new JTextField();
        contentJPanel.add(timeJLabel);
        contentJPanel.add(timeArea);
        timeJLabel.setBounds(2, 40, 50, 15);
        timeArea.setBounds(50, 38, 100, 20);

        //输入优先权的文本框
        JLabel priorityJLabel = new JLabel("优先权");
        JTextField priorityJTextField = new JTextField();
        contentJPanel.add(priorityJLabel);
        contentJPanel.add(priorityJTextField);
        priorityJLabel.setBounds(5, 65, 50, 15);
        priorityJTextField.setBounds(50, 63, 100, 20);

        //输入进程要求的主存大小
        JLabel memoryJLabel = new JLabel("要求主存大小");
        JTextField memoryTextField = new JTextField();
        contentJPanel.add(memoryJLabel);
        contentJPanel.add(memoryTextField);
        memoryJLabel.setBounds(2, 350, 100, 30);
        memoryTextField.setBounds(2, 380, 100, 25);
        //是否开启多处理
        JCheckBox multBox = new JCheckBox("开启多处理");
        contentJPanel.add(multBox);
        multBox.setBounds(2, 420, 100, 20);

        //进程属性（暂时留坑）

        //


        //显示就绪队列
        JLabel readylistJLabel = new JLabel("就绪队列：");
        contentJPanel.add(readylistJLabel);
        readylistJLabel.setBounds(300, 10, 100, 30);
        JList<String> readyJList = new JList<>();
        JScrollPane readylistPane = new JScrollPane(readyJList);
        contentJPanel.add(readylistPane);
        readylistPane.setBounds(300, 50, 260, 100);

        //显示后备队列
        JLabel waitlistJLabel = new JLabel("后备队列：");
        contentJPanel.add(waitlistJLabel);
        waitlistJLabel.setBounds(300, 250, 100, 30);
        JList<String> waitJList = new JList<>();
        JScrollPane waitlistPane = new JScrollPane(waitJList);
        contentJPanel.add(waitlistPane);
        waitlistPane.setBounds(300, 290, 260, 100);

        //显示内存使用情况
        JList<String> memoryJlist = new JList<>();
        JScrollPane memorylistPane = new JScrollPane(memoryJlist);
        contentJPanel.add(memorylistPane);
        memorylistPane.setBounds(600, 20, 100, 500);
        memoryJlist.setListData(memory_list);
        memoryColor(memoryJlist);

        //前驱选择列表
        JComboBox<String> existComboBox = new JComboBox<String>();
        contentJPanel.add(existComboBox);
        existComboBox.setBounds(2, 280, 100, 30);

        //reset
        JButton resetButton = new JButton("重置");
        contentJPanel.add(resetButton);
        resetButton.setBounds(2, 500, 100, 30);
        resetButton.addActionListener(e -> reset = true);

        //新建进程按钮
        JButton buildButton = new JButton("添加进程");
        contentJPanel.add(buildButton);
        buildButton.setBounds(2, 85, 100, 30);
        buildButton.addActionListener(e -> {
            addIntoReady(newpcb(nameArea, priorityJTextField, timeArea, memoryTextField));
            //把ready队列显示出来
            ShowList.show(readyJList, ready_list);
            //把wait队列显示出来
            ShowList.show(waitJList, wait_list);
            //在前驱列表中添加进程
            existComboBox.addItem(nameArea.getText());
        });

        //显示运行队列
        JLabel runninglistJLabel = new JLabel("运行队列：");
        contentJPanel.add(runninglistJLabel);
        runninglistJLabel.setBounds(300, 180, 100, 30);
        JList<String> runningJList = new JList<String>();
        JScrollPane runninglistpane = new JScrollPane(runningJList);
        contentJPanel.add(runninglistpane);
        runninglistpane.setBounds(270, 210, 300, 50);

        //显示运行队列2
        JLabel runninglistJLabel2 = new JLabel("运行队列2：");
        contentJPanel.add(runninglistJLabel);
        runninglistJLabel.setBounds(300, 180, 100, 30);
        JList<String> runningJList2 = new JList<String>();
        JScrollPane runninglistpane2 = new JScrollPane(runningJList2);
        contentJPanel.add(runninglistpane2);
        runninglistpane2.setBounds(2, 450, 280, 50);

        //显示挂起队列
        JLabel suspendlistlabel = new JLabel("挂起队列：");
        contentJPanel.add(suspendlistlabel);
        suspendlistlabel.setBounds(300, 400, 100, 30);
        JList<String> suspendJList = new JList<String>();
        JScrollPane suspendlistJScrollPane = new JScrollPane(suspendJList);
        contentJPanel.add(suspendlistJScrollPane);
        suspendlistJScrollPane.setBounds(300, 430, 260, 100);

        //解挂列表
        JComboBox<String> suspendComboBox = new JComboBox<>();
        contentJPanel.add(suspendComboBox);
        suspendComboBox.setBounds(2, 200, 100, 30);


        //添加同步进程按钮
        JButton addsynJButton = new JButton("添加前驱");
        contentJPanel.add(addsynJButton);
        addsynJButton.setBounds(2, 320, 100, 30);
        addsynJButton.addActionListener(e -> {
            Semaphore tempSemaphore = new Semaphore();
            tempSemaphore.setPredone(0);
            tempSemaphore.setPrecursor(existComboBox.getSelectedItem().toString());
            tempSemaphore.setSubsequent(nameArea.getText());
            System.out.println("添加信号量:" + tempSemaphore.getPrecursor() + " " + tempSemaphore.getSubsequent() + " " + tempSemaphore.getPredone());
            synVector.add(tempSemaphore);
        });

        //挂起按钮
        JButton suspendButton = new JButton("挂起");
        contentJPanel.add(suspendButton);
        suspendButton.setBounds(2, 160, 100, 30);
        suspendButton.addActionListener(new ActionListener() {

            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                if (!running_list.isEmpty()) {
                    suspend_list.add(running_list.firstElement());
                    suspendComboBox.addItem(running_list.firstElement().getName());
                    running_list.remove(0);
                    if (!ready_list.isEmpty()) {
                        movReadyToRun(memoryJlist);
                    }
                    refreshAllList(runningJList, readyJList, waitJList, suspendJList);
                } else {
                    JOptionPane.showMessageDialog(null, "运行队列已为空", null, JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        //解挂按钮
        JButton dissuspendButton = new JButton("解挂");
        contentJPanel.add(dissuspendButton);
        dissuspendButton.setBounds(2, 240, 100, 30);
        dissuspendButton.addActionListener(e -> {
            System.out.println("开始解挂");
            for (int i = 0; i < suspend_list.size(); i++) {
                if (suspend_list.get(i).getName() == suspendComboBox.getSelectedItem().toString()) {
                    System.out.println("找到");
                    if (ready_list.size() < CHANNEL) {
                        ready_list.add(suspend_list.get(i));
                        MyComparator.priority_sort(ready_list);
                    } else {
                        wait_list.add(suspend_list.get(i));
                    }
                    suspend_list.remove(i);
                }
            }
            suspendComboBox.removeItem(suspendComboBox.getSelectedItem());
            refreshAllList(runningJList, readyJList, waitJList, suspendJList);
        });

        //进程运行按钮
        JButton runningButton = new JButton("运行");
        contentJPanel.add(runningButton);
        runningButton.setBounds(2, 120, 100, 30);
        runningButton.addActionListener(e -> new Thread() {
            public synchronized void run() {
                int timeleft = TIME_SLICE;
                while (true) {
                    if (reset == true) {
                        break;
                    }
                    if (multBox.isSelected() && running_list2.size() < 1) {
                        if (!ready_list.isEmpty()) {
                            running_list2.add(ready_list.firstElement());
                            ready_list.remove(0);
                        }

                        ShowList.show(runningJList2, running_list2);
                    }
                    if (!running_list2.isEmpty()) {
                        checksynsub();
                        if (tempcheck == 1) {
                            //程序在运行中，该程序不为最后一个程序
                            running_list2.firstElement().processRun();
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        if (running_list2.firstElement().getTime() == 0) {
                            setsynpredone(running_list);
                            backMemory(memoryJlist, running_list2);
                            saveblockprocess();
                            existComboBox.removeItem(running_list2.firstElement().getName());
                            deletesynsub(running_list2);
                            running_list2.remove(0);
                        }
                        ShowList.show(runningJList2, running_list2);
                        tempcheck = 1;
                    }


                    if (!running_list.isEmpty()) {
                        if (!ready_list.isEmpty() && running_list.firstElement().getTime() > 0 && timeleft > 0 && running_list.firstElement().getPriority() >= ready_list.firstElement().getPriority()) {
                            checksynsub();
                            if (tempcheck == 1) {
                                //程序在运行中，该程序不为最后一个程序
                                running_list.firstElement().processRun();

                                timeleft--;
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (!running_list.isEmpty()) {
                                    printpcb(running_list.firstElement());
                                    refreshAllList(runningJList, readyJList, waitJList, suspendJList);
                                }
                            } else {
                                suspend_list.add(running_list.firstElement());
                                running_list.remove(0);
                            }
                            tempcheck = 1;
                        }

                        //运行进程被抢占
                        else if (!ready_list.isEmpty() && running_list.firstElement().getPriority() < ready_list.firstElement().getPriority() && running_list.firstElement().getTime() > 0) {
                            //后备队列没有进程，running进程加入ready队列
                            addIntoReady(running_list.firstElement());
                            //把running进程移除
                            running_list.remove(0);
                            //加入优先权最大的进程
                            movReadyToRun(memoryJlist);
                            timeleft = TIME_SLICE;
                            //刷新所有队列
                            refreshAllList(runningJList, readyJList, waitJList, suspendJList);

                        } else if (running_list.firstElement().getTime() <= 0 || timeleft <= 0) {
                            if (running_list.firstElement().getTime() <= 0) {
                                //进程执行完毕且该进程不是最后一个进程
                                setsynpredone(running_list);
                                backMemory(memoryJlist, running_list);
                                saveblockprocess();
                                existComboBox.removeItem(running_list.firstElement().getName());
                                deletesynsub(running_list);
                            }
                            running_list.remove(0);
                            if (!ready_list.isEmpty()) {
                                movReadyToRun(memoryJlist);
                            }
                            timeleft = TIME_SLICE;
                            //刷新所有队列
                            refreshAllList(runningJList, readyJList, waitJList, suspendJList);
                        } else if (ready_list.isEmpty()) {
                            while (!running_list.isEmpty() && running_list.firstElement().getTime() > 0) {
                                //最后一个程序不停地运行到结束
                                running_list.firstElement().processRun();
                                timeleft--;
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (!running_list.isEmpty()) {
                                    printpcb(running_list.firstElement());
                                    refreshAllList(runningJList, readyJList, waitJList, suspendJList);
                                }

                            }
                            //进程执行完毕且该进程是最后一个进程

                            setsynpredone(running_list);
                            deletesynsub(running_list);
                            if (!running_list.isEmpty()) {
                                existComboBox.removeItem(running_list.firstElement().getName());
                            }

                            backMemory(memoryJlist, running_list);
                            if (!running_list.isEmpty()) {
                                running_list.remove(0);
                                System.out.println("最后一个进程运行结束");
                            }
                            saveblockprocess();
                            refreshAllList(runningJList, readyJList, waitJList, suspendJList);
                        }

                    } else {
                        if (ready_list.isEmpty()) {

                        } else {
                            movReadyToRun(memoryJlist);
                            refreshAllList(runningJList, readyJList, waitJList, suspendJList);
                        }
                    }
                }
                reset = false;
            }
        }.start());


        //将面板添加到frame
        this.add(contentJPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public PCB newpcb(JTextField nameArea, JTextField priorityJTextField, JTextField timeArea, JTextField memoryJTextField) {
        PCB pcb = new PCB();
        pcb.setName(nameArea.getText());
        pcb.setPriority(Integer.parseInt(priorityJTextField.getText()));
        pcb.setState("就绪");
        pcb.setTime(Integer.parseInt(timeArea.getText()));
        pcb.setMemorySize(Integer.parseInt(memoryJTextField.getText()));
        System.out.println("新建一个PCB:" + "name:" + pcb.getName() + "priority:" + pcb.getPriority() + "getState:" + pcb.getState() + "time:" + pcb.getTime() + "memory:" + pcb.getMemorySize());
        return pcb;
    }

    public void printpcb(PCB pcb) {
        System.out.println("name:" + pcb.getName() + "priority:" + pcb.getPriority() + "getState:" + pcb.getState() + "time:" + pcb.getTime());
    }

    public void movReadyToRun(JList<String> memoryJlist) {
        int temp = 0;
        if (!ready_list.isEmpty() && ready_list.firstElement().getMemoryStartAddress() < 0) {
            for (int i = 0; i < partiontables_list.size(); i++) {
                if ((partiontables_list.get(i).getState() == 1) && partiontables_list.get(i).getSize() > ready_list.firstElement().getMemorySize()) {
                    ready_list.firstElement().setMemoryStartAddress(partiontables_list.get(i).getStartAddress());
                    //剩下的可用分区
                    partiontables_list.add(i + 1, new PartitionTable(partiontables_list.get(i).getNumber(), partiontables_list.get(i).getSize() - ready_list.firstElement().getMemorySize(), partiontables_list.get(i).getStartAddress() + ready_list.firstElement().getMemorySize(), 1));
                    //被分配的分区
                    partiontables_list.get(i).setSize(ready_list.firstElement().getMemorySize());
                    partiontables_list.get(i).setStartAddress(partiontables_list.get(i).getStartAddress());
                    partiontables_list.get(i).setState(0);
                    temp = 1;
                    break;
                }
            }
        }

        if (temp == 1 || ready_list.firstElement().getMemoryStartAddress() >= 0) {
            //把ready队列队首的元素移动到running队列
            running_list.add(ready_list.firstElement());
            ready_list.remove(0);

            if (!wait_list.isEmpty()) {
                //把wait队列中的队首元素移动到ready队列
                ready_list.add(wait_list.firstElement());
                wait_list.remove(0);
                //对ready队列按优先级排序
                MyComparator.priority_sort(ready_list);
            }
        } else {
            JOptionPane.showMessageDialog(null, "无可用主存给进程" + ready_list.firstElement().getName(), null, JOptionPane.ERROR_MESSAGE);
            ;
            suspend_list.add(ready_list.firstElement());
            ready_list.remove(0);
        }
        memoryColor(memoryJlist);
    }

    public void refreshAllList(JList<String> runningJList, JList<String> readyJList, JList<String> waitJList, JList<String> suspendJList) {
        //刷新所有队列
        ShowList.show(runningJList, running_list);
        ShowList.show(readyJList, ready_list);
        ShowList.show(waitJList, wait_list);
        ShowList.show(suspendJList, suspend_list);
    }

    public void addIntoReady(PCB pcb) {
        if (ready_list.size() < CHANNEL) {
            //向ready队列中添加新建进程
            ready_list.add(pcb);
            //对ready队列排序
            MyComparator.priority_sort(ready_list);
        } else {
            //将进程加入后备队列
            wait_list.add(pcb);

        }
    }

    //检查将要运行的进程的所有前驱是否执行完毕，是则tempcheck被置为1，不是则tempcheck被置为0
    public void checksynsub() {
        for (int i = 0; i < synVector.size(); i++) {
            if (synVector.get(i).getSubsequent().equals(running_list.firstElement().getName())) {
                tempcheck = tempcheck * synVector.get(i).getPredone();
            }
        }
    }

    //删除以刚运行完的程序为后继结点的信号量
    public void deletesynsub(Vector<PCB> runninglist) {
        for (int i = 0; i < synVector.size(); i++) {
            if (synVector.get(i).getSubsequent().equals(runninglist.firstElement().getName())) {
                synVector.remove(i);
            }
        }
    }

    //将所有以刚刚执行完的进程为前驱的信号量的布尔值置为1
    public void setsynpredone(Vector<PCB> runningList) {
        //查找同步队列
        for (int i = 0; i < synVector.size(); i++) {
            if (!runningList.isEmpty() && synVector.get(i).getPrecursor().equals(runningList.firstElement().getName())) {
                synVector.get(i).setPredone(1);
            }
        }
    }

    //将所有前驱已经执行完毕的被阻塞的进程解救出来
    public void saveblockprocess() {
        for (int i = 0; i < suspend_list.size(); i++) {
            int temp = 1;
            int j = 0;
            for (j = 0; j < synVector.size(); j++) {
                if (suspend_list.get(i).getName().equals(synVector.get(j).getSubsequent())) {
                    temp = temp * synVector.get(j).getPredone();
                }
            }
            if (temp == 1) {
                ready_list.add(suspend_list.get(i));
                suspend_list.remove(i);
            }
        }
    }

    public void initialpartiontable() {
        partiontables_list.add(new PartitionTable(1, 20, 10, 1));
        partiontables_list.add(new PartitionTable(2, 10, 31, 1));
        partiontables_list.add(new PartitionTable(3, 22, 42, 1));
        for (int i = 0; i < 65; i++) {
            memory_list.add(Integer.toString(i));
        }
    }


    public void memoryColor(JList<String> memoryJlist) {
        memoryJlist.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof String) {
                    int temp = Integer.parseInt((String) value);
                    setBackground(Color.red);
                    for (int i = 0; i < partiontables_list.size(); i++) {
                        int startaddress = partiontables_list.get(i).getStartAddress();
                        if (startaddress <= temp && temp <= startaddress + partiontables_list.get(i).getSize() && partiontables_list.get(i).getState() == 1) {
                            setBackground(Color.green);
                        } else if (startaddress <= temp && temp <= startaddress + partiontables_list.get(i).getSize() && partiontables_list.get(i).getState() == 0) {
                            setBackground(Color.RED);
                        }
                    }
                }
                return c;
            }
        });
    }

    public void backMemory(JList memoryJlist, Vector<PCB> runningList) {
        for (int i = 0; i < partiontables_list.size(); i++) {
            if (partiontables_list.get(i).getStartAddress() == runningList.firstElement().getMemoryStartAddress()) {
                partiontables_list.get(i).setState(1);
            }
        }
        for (int i = 0; i < partiontables_list.size(); i++) {
            for (int j = 0; j < partiontables_list.size(); j++) {
                PartitionTable itable = partiontables_list.get(i);
                PartitionTable jtable = partiontables_list.get(j);
                if (jtable.getStartAddress() - itable.getStartAddress() == itable.getSize() && itable.getNumber() == jtable.getNumber() && itable.getState() == 1 && jtable.getState() == 1) {
                    partiontables_list.get(i).setSize(itable.getSize() + jtable.getSize());
                    partiontables_list.remove(j);
                }
            }
        }
        memoryColor(memoryJlist);
    }
}
