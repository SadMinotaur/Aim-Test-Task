package testtask.threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ReadFileThread extends Thread {

    private final String path;
    private CyclicBarrier cyclicBarrier;

    public ReadFileThread(String path) {
        this.path = path;
    }

    public ReadFileThread(String path, CyclicBarrier cyclicBarrier) {
        this.path = path;
        this.cyclicBarrier = cyclicBarrier;
    }

    private Map<Integer, String[]> readLines(String path) throws BrokenBarrierException, InterruptedException, IOException {
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

    private void writeFile(Map<String, Set<String>> values) {
    }

    @Override
    public void run() {
        try {
            writeFile(getPrintStrings(readLines(path)));
            cyclicBarrier.await();
        } catch (BrokenBarrierException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
