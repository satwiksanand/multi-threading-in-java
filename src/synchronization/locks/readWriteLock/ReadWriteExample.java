package synchronization.locks.readWriteLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteExample {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();
    private int count = 0;

    public int getCount(){
        readLock.lock();
        try{
            return count;
        }
        catch (Exception e){
            System.out.println(e);
        }
        finally {
            readLock.unlock();
        }
        return -1;
    }

    public void increment()  {
        writeLock.lock();
        try{
            count += 1;
            System.out.println(Thread.currentThread().getName() + "count value after increment: " + count);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            writeLock.unlock();
        }
    }
}
