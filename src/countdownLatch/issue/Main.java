package countdownLatch.issue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main{
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        long start = System.currentTimeMillis();
        Future<String> future1 = executorService.submit(new DependentExample());
        Future<String> future2 = executorService.submit(new DependentExample());
        Future<String> future3 = executorService.submit(new DependentExample());

        future1.get();
        future2.get();
        future3.get();

        System.out.println("Total time taken is " + (System.currentTimeMillis() - start));
        executorService.shutdown();
    }
}
