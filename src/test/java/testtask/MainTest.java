package testtask;

import org.junit.jupiter.api.Test;
import testtask.threads.ReadFileThread;

public class MainTest {

    @Test
    public void test() throws InterruptedException {
        ReadFileThread th = new ReadFileThread(System.getProperty("user.dir") + "/testfiles/input1.csv");
        th.start();
        th.join();
    }
}
