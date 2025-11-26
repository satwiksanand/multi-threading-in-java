package deadlock;

public class DeadlockExample {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void method1() {
        synchronized (lock1) {
            System.out.println("Thread 1 acquired lock1");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2) {
                System.out.println("Thread 1 acquired lock2");
            }
        }
    }

    public void method2() {
        synchronized (lock2) {
            System.out.println("Thread 2 acquired lock2");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock1) {
                System.out.println("Thread 2 acquired lock1");
            }
        }
    }

    public static void main(String[] args) {
        DeadlockExample example = new DeadlockExample();
        //these threads are in a deadlock condition in method 1, thread 1 is waiting for lock2, which is acquired by lock2
        //and thread2 is waiting for lock1 which is in turn acquired by thread1, hence creating a deadlock situation.
        Thread thread1 = new Thread(example::method1);
        Thread thread2 = new Thread(example::method2);
        thread1.start();
        thread2.start();
    }
}