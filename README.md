# MultiThreading

## What is an Operating System?

An operating system is a software program that manages computer hardware and software resources, providing common services for computer programs.

## What is a Core?

A core is a single processor unit in a multicore processor. Each core can execute instructions independently, allowing for parallel processing and improved performance.

## What is a Program?

A program is a set of instructions that are executed by a computer. Programs are written in a programming language and are compiled or interpreted by the operating system to create an executable file that can be run on the computer.

## What is a Process?

A process is an instance of a program in execution. Each process has its own memory space and resources, and can be executed independently by the operating system.

## What is Multi-tasking?

Multitasking refers to the ability of a computer to perform multiple tasks simultaneously. This can be achieved through the use of multiple processes or threads, each running independently and sharing resources as needed.

## What is a Thread?

A thread is a sequence of instructions that can be executed independently by the operating system. Each thread has its own program counter and stack, and can be executed concurrently with other threads in the same process.

## What is Multi-threading?

Multi-threading refers to the ability of a computer to perform multiple tasks simultaneously. This can be achieved through the use of multiple threads, each running independently and sharing resources as needed.

## How do we create a thread in java?

There are two ways to create a thread in java:

1. By extending the Thread class
2. By implementing the Runnable interface

### By extending the Thread class

we can extend the `Thread` class and override the `run` method.

```java
public class MyThread extends Thread {
    @Override
    public void run(){
        System.out.println("Thread is running");
    }
}
```

then we can create an instance of the thread and call the `start` method.

```java
public class Main {
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
    }
}
```

### By implementing the Runnable interface

we can implement the `Runnable` interface and override the `run` method.

```java
public class MyRunnable implements Runnable {
    @Override
    public void run(){
        System.out.println("Thread is running");
    }
}
```

then we can create an instance of the thread and call the `start` method.

```java
public class Main {
    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
```

## What are the states of a thread?

A thread can be in one of the following states:

1. New
2. Runnable
3. Blocked
4. Waiting
5. Timed Waiting
6. Terminated

### 1. New

A thread that has been created but not yet started. It is in this state when `new Thread()` is called but `start()` has not yet been invoked.

### 2. Runnable

A thread that is eligible to be run by the Java Virtual Machine (JVM) scheduler. It could be currently running or waiting for its turn to run.

### 3. Blocked

A thread that is temporarily inactive because it is waiting for a monitor lock to enter a synchronized block/method or re-enter after calling `wait()`.

### 4. Waiting

A thread that is indefinitely waiting for another thread to perform a particular action. For example, a thread might be waiting for another thread to call `notify()` or `notifyAll()`.

### 5. Timed Waiting

A thread that is waiting for another thread to perform an action for a specified waiting time. Examples include threads that have called `sleep()`, `wait(long timeout)`, `join(long timeout)`, or `Lock.tryLock(long timeout, TimeUnit unit)`.

### 6. Terminated

A thread that has completed its execution or has been abnormally terminated. Once a thread is in this state, it cannot be restarted.

## User Thread and Daemon Thread

Daemon Thread: a daemon thread is low-priority thread in java used to run background operations.

The defining characteristic of a daemon thread is its lifecycle dependency on the JVM: The Java Virtual Machine (JVM) will exit when the only threads running are daemon threads.

If all "User Threads" (non-daemon threads, like the main thread) complete their execution, the JVM terminates immediately, killing all currently running daemon threads without waiting for them to finish.

```java
public class MyThread extends Thread{
    @Override
    public void run(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("My thread is running...");
    }
}
```

```java
public class Main{
    public static void main(String[] args){
        Thread t1 = new MyThread();
        t1.setDaemon(true);
        //when setDaemon is true then jvm will treat t1 as a low priority-thread 
        // and the program will terminate even if t1 is not finished(1s delay), 
        // if it's set to false then the program will wait for t1 to finish.
        t1.start();
    }
}
```

## Synchronization

In certain scenarios, multiple threads may try to access a common resource and depending upon their relative timing the output of the program may differ
this condition of the output depending upon which thread get resource access first is called `race condition`.

to avoid this the easiest way is to apply some kind of lock on the resource such that at any particular time only one thread can access the resource,
there are two types of locks:

1. Implicit Lock
2. Explicit Lock

### What is Implicit Lock?

Implicit Lock is a lock that is automatically acquired and released by the JVM. It is used to synchronize access to a shared resource.

```java
public class MyThread extends Thread{
    @Override
    public void run(){
        synchronized (this){//this block is synchronized
            System.out.println("My thread is running...");
        }
    }
}
```

### What is Explicit Lock?

Explicit Lock is a lock that is manually acquired and released by the programmer. It is used to synchronize access to a shared resource. It offers more control over the lock.(look for synchronization.locks.ReentrantLockExample for more details)

```java
public class MyThread extends Thread{
    private final Lock lock = new ReentrantLock();
    @Override
    public void run(){
        lock.lock();
        try {
            System.out.println("My thread is running...");
        } finally {
            lock.unlock();
        }
    }
}
```

### What are the advantages of Explicit Lock over Implicit Lock?

- **Fairness:** Explicit locks (like `ReentrantLock`) can offer fair locking, meaning the longest-waiting thread gets the lock next or in the first come first serve basis. Implicit locks (synchronized blocks) do not guarantee fairness.

- **Non-blocking attempts:** Explicit locks allow for `tryLock()`, which attempts to acquire the lock without blocking. This is useful for avoiding deadlocks or implementing more sophisticated concurrency control.

- **Interruptible lock acquisition:** Explicit locks support `lockInterruptibly()`, allowing a thread waiting for a lock to be interrupted. `synchronized` blocks do not offer this.

- **Multiple conditions:** Explicit locks can have multiple `Condition` objects associated with them, enabling more fine-grained control over thread waiting and notification. `synchronized` blocks only have a single wait-set per object.

- **Time-out for lock acquisition:** Explicit locks allow you to specify a timeout when acquiring a lock using `tryLock(long timeout, TimeUnit unit)`, preventing indefinite waiting.

### What are Read-Write Locks?

Read-Write Locks are a type of lock that allows multiple threads to read a shared resource concurrently, but only one thread to write to the resource at a time.

there's a class called `ReentrantReadWriteLock` that implements `ReadWriteLock` interface.

```java
public class MyThread extends Thread{
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private int count = 0;

    @Override
    public void run(){
        lock.readLock().lock();//multiple threads can acquire the read lock simultaneously
        try {
            System.out.println("My thread is running...");
        } finally {
            lock.readLock().unlock();
        }
    }

    public void increment(){
        lock.writeLock().lock();//only one thread can acquire the write lock
        //read lock can only be acquired when no thread holds the write lock.
        //The Write Lock can only be granted if no other thread currently holds either a Read Lock OR a Write Lock.
        try {
            count++;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
```

## Deadlock

Deadlock is a situation where two or more threads are blocked waiting for each other to release a lock.

Deadlock typically happens when the below conditions are met:

1. Mutual Exclusion: Only one thread can access a resource at a time.
2. Hold and Wait: A thread is holding at least one resource and waiting for other resources that are being held by other threads.
3. No Preemption: Resources cannot be forcibly taken away from a thread.
4. Circular Wait: A set of threads are waiting for resources in a circular fashion.

## What is Executors framework?

Executors framework is a framework that provides a way to create and manage threads in a Java application.

## What is the difference between Runnable and Callable?

Runnable is an interface that is used to define a task that can be executed by a thread. Callable is an interface that is used to define a task that can be executed by a thread and returns a result.

Runnable does not return a result and does not throw a checked exception. Callable returns a result and throws a checked exception.

Runnable has a method called `run()` and Callable has a method called `call()`. Callable is a subinterface of Runnable.
