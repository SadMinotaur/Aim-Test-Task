package testtask.threads;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class WriteFileThread extends Thread {

    private final String fileName;
    private final String[] content;
    private final CountDownLatch countDownLatch;

    public WriteFileThread(String fileName, String[] content, CountDownLatch countDownLatch) {
        this.fileName = fileName;
        this.content = content;
        this.countDownLatch = countDownLatch;
    }

    private void writeFile(String fileName, String[] content) throws IOException {
        FileWriter file = new FileWriter("output/" + fileName);
        try (BufferedWriter output = new BufferedWriter(file)) {
            output.write(String.join(";", content) + ";");
        }
    }

    @Override
    public void run() {
        try {
            writeFile(fileName, content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}
