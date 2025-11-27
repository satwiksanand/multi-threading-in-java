package executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorExample {

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule a task to run once after a 3-second delay
        System.out.println("Scheduling task 1 (one-time) to run after 3 seconds...");
        scheduler.schedule(() -> {
            System.out.println("Task 1 executed: This runs once after a delay.");
        }, 3, TimeUnit.SECONDS);

        // Schedule a task to run repeatedly with a fixed delay
        // Initial delay of 2 seconds, then repeat every 5 seconds (delay between end of
        // one and start of next)
        System.out.println("Scheduling task 2 (fixed delay) to run after 2s, then every 5s...");
        scheduler.scheduleWithFixedDelay(() -> {
            System.out.println("Task 2 executed: Fixed delay task. Current time: " + System.currentTimeMillis());
            try {
                // Simulate some work
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, 2, 5, TimeUnit.SECONDS);

        // Schedule a task to run repeatedly at a fixed rate
        // Initial delay of 1 second, then repeat every 4 seconds (start time of each
        // execution)
        System.out.println("Scheduling task 3 (fixed rate) to run after 1s, then every 4s...");
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Task 3 executed: Fixed rate task. Current time: " + System.currentTimeMillis());
            try {
                // Simulate some work that might take longer than the rate
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, 1, 4, TimeUnit.SECONDS);

        // Allow tasks to run for some time before shutting down
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Shut down the scheduler
        System.out.println("Attempting to shut down the scheduler...");
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Scheduler did not terminate in time, forcing shutdown.");
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.err.println("Shutdown interrupted, forcing shutdown.");
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Scheduler shut down.");
    }
}
