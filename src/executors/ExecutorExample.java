package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        System.out.println("Submitting tasks...");

        var future = executorService.submit(() -> {
            try {
                Thread.sleep(1000);
                return "Task Completed";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Task Interrupted";
            }
        });

        System.out.println("Is shutdown before shutdown call? " + executorService.isShutdown());

        try {
            String result = future.get();
            System.out.println("Result from Future: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        System.out.println("Is shutdown after shutdown call? " + executorService.isShutdown());

        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
