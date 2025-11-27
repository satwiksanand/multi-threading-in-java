package countdownLatch.fix;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class DependentExample implements Callable<String> {
    private final CountDownLatch latch;

    public DependentExample(CountDownLatch latch){
        this.latch = latch;
    }
    @Override
    public String call() throws Exception{
        try{
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " completed it's work!");
        }
        finally {
            latch.countDown();
        }
        return "ok";
    }
}
