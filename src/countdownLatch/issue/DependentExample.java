package countdownLatch.issue;

import java.util.concurrent.Callable;

public class DependentExample implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " is working right now.");
        Thread.sleep(2000);
        return "ok";
    }
}
