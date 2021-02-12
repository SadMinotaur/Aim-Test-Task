package testtask;


import org.junit.Test;
import testtask.threads.ReadFileThread;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void test1() throws InterruptedException {
        new ReadFileThread(System.getProperty("user.dir") + "/testfiles/input1.csv").start();
        Thread.sleep(2000);
        String[] files = new File(System.getProperty("user.dir") + "/output").list();
        List<String> list = Arrays.asList(files);
        assertTrue(list.contains("id"));
        assertTrue(list.contains("version"));
        assertTrue(list.contains("path"));
    }

    @Test
    public void test2() throws InterruptedException {
        new ReadFileThread(System.getProperty("user.dir") + "/testfiles/input2.csv").start();
        Thread.sleep(2000);
        String[] files = new File(System.getProperty("user.dir") + "/output").list();
        List<String> list = Arrays.asList(files);
        assertTrue(list.contains("id"));
        assertTrue(list.contains("name"));
        assertTrue(list.contains("sex"));
    }
}
