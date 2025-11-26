package synchronization.fix;

public class Counter {
    private int counter = 0;

    public synchronized void increment(){
        counter += 1;
    }

    public int getCounter(){
        return counter;
    }
}
