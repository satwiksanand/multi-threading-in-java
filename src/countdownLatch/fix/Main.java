package countdownLatch.fix;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int numberOfServices = 4;
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(numberOfServices);
        executorService.submit(new DependentExample(latch));
        executorService.submit(new DependentExample(latch));
        executorService.submit(new DependentExample(latch));
        executorService.submit(new DependentExample(latch));

        latch.await(3, TimeUnit.SECONDS);
        executorService.shutdownNow();
        System.out.println("Total time taken: " + (System.currentTimeMillis() - start));
    }
}
