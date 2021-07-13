package com.nj.learn.demo.concurrent;

import com.nj.learn.demo.thread.DeamoThreadFactory;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class LockCounter {
    private int sum = 0;
    /**
     * 可重入锁 + 公平锁
     *  什么是可重入锁： 第二次该对象去调用这个同步代码块 不会被阻塞，叫可重入锁
     *  什么是公平锁：  排队考前的线程优先获取锁，非公平锁都是同样的机会
     */
    private static Lock lock = new ReentrantLock(true);

    private  int addAndGet(){
        return ++sum;
    }


    public static void main(String[] args) throws InterruptedException {
        LockCounter lockCounter = new LockCounter();
        int coreSize = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(coreSize);
        forkJoinPool.execute(()->{
            run(lockCounter);
        });
        forkJoinPool.execute(()->{
            run(lockCounter);
        });
        //等待所有任务完成
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
    }

    public static  void run( LockCounter lockCounter){

        try{
            lock.tryLock(1,TimeUnit.MINUTES);
            IntStream.range(0, 1000).forEach(i -> lockCounter.addAndGet());
            System.out.println(lockCounter.sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
           lock.unlock();
        }
    }

    public static ThreadPoolExecutor initThreadPool(){
        int coreSize = Runtime.getRuntime().availableProcessors();
        int maxSize = Runtime.getRuntime().availableProcessors()*2;
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(5000);
        ThreadFactory threadFactory = new DeamoThreadFactory();
        return new ThreadPoolExecutor(coreSize, maxSize, 100, TimeUnit.SECONDS, linkedBlockingQueue, threadFactory);
    }

}
