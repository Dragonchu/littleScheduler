package littlescheduler.app;

import lombok.AllArgsConstructor;
import lombok.Data;

//内存分区表
@Data
@AllArgsConstructor
public class PartitionTable {
    private int number;
    private int size;
    private int startAddress;
    private int state;
}
