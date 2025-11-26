package synchronization.issue;

public class Counter {
    private int count = 0;

    public void increment(){
        count += 1;
    }

    public int getCount(){
        return count;
    }
}
