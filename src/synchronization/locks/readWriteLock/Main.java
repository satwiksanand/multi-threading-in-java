package synchronization.locks.readWriteLock;

public class Main {
    public static void main(String[] args) {
        ReadWriteExample readWriteExample = new ReadWriteExample();
        Runnable readTask = () -> {
            System.out.println(Thread.currentThread().getName() + " count value: " + readWriteExample.getCount());
        };

        Runnable writeTask = () -> {
            for(int i = 0; i < 5; ++i){
                readWriteExample.increment();
            }
        };

        Thread thread1 = new Thread(readTask);
        Thread thread2 = new Thread(readTask);
        Thread thread3 = new Thread(writeTask);
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
