package littlescheduler.app;

import lombok.Data;

//信号量
@Data
public class Semaphore {
    private String precursor;
    private String subsequent;
    public int predone;

}
