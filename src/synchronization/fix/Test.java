package synchronization.fix;


public class Test {
    public static void main(String[] args){
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
        System.out.println(counter.getCounter());
    }
}
