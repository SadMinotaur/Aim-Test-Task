package testtask.threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class ReadFileThread extends Thread {

    private final String path;
    private CountDownLatch cyclicBarrier;

    public ReadFileThread(String path) {
        this.path = path;
    }

    public ReadFileThread(String path, CountDownLatch cyclicBarrier) {
        this.path = path;
        this.cyclicBarrier = cyclicBarrier;
    }

    private Map<Integer, String[]> readLines(String path) throws IOException {
        Map<Integer, String[]> lines = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            for (int i = 0; line != null; i++) {
                lines.putIfAbsent(i, line.split(";"));
                line = reader.readLine();
            }
        }
        return lines;
    }

    private Map<String, Set<String>> getPrintStrings(Map<Integer, String[]> values) {
        Map<String, Set<String>> retString = new HashMap<>();
        String[] firstLine = values.get(0);
        for (int i = 0; i < firstLine.length; i++) {
            Set<String> uniqueVs = new HashSet<>();
            for (int j = 1; j < values.size(); j++) {
                uniqueVs.add(values.get(j)[i]);
            }
            retString.putIfAbsent(firstLine[i], uniqueVs);
        }
        return retString;
    }

    private void writeFile(Map<String, Set<String>> values) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(values.size());
        for (String file : values.keySet()) {
            new WriteFileThread(file, values.get(file).toArray(new String[0]), countDownLatch).start();
        }
        countDownLatch.await();
        System.out.println("Done with " + path);
    }

    @Override
    public void run() {
        try {
            writeFile(getPrintStrings(readLines(path)));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (cyclicBarrier != null) {
                cyclicBarrier.countDown();
            }
        }
    }
}
