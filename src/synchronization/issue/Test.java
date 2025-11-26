package synchronization.issue;

public class Test {
    public static void main(String[] args) {
        // we have a common resource counter and two threads are
        // simultaneously trying to access it, which leads to race condition and thus the counter value is not correctly updated
        Counter counter = new Counter();
        Thread t1 = new MyThread(counter);
        Thread t2 = new MyThread(counter);
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        }
        catch (Exception e){
            System.out.println(e);
        }
        System.out.println(counter.getCount());
    }
}