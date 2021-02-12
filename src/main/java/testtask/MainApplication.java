package testtask;

import testtask.threads.ReadFileThread;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class MainApplication {

    public static void main(final String[] args) {
        boolean exit = false;
        Set<String> filenames = new HashSet<>();
        System.out.println("Enter global path to files and then enter \"done\"");
        System.out.println("Enter \"exit\" to exit");
        while (!exit) {
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            switch (input) {
                case "done": {
                    try {
                        CountDownLatch countDownLatch = new CountDownLatch(filenames.size());
                        filenames.forEach(name -> new ReadFileThread(name, countDownLatch).start());
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        filenames.clear();
                    }
                    break;
                }
                case "exit": {
                    exit = true;
                    break;
                }
                default: {
                    filenames.add(input);
                    break;
                }
            }
        }
    }
}
