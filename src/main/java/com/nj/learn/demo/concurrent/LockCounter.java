package com.nj.learn.demo.concurrent;

import com.nj.learn.demo.thread.DeamoThreadFactory;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class LockCounter {
    private int sum = 0;
    private Lock lock = new ReentrantLock();

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

    public static synchronized void run( LockCounter lockCounter){
        IntStream.range(0, 1000).forEach(i -> lockCounter.addAndGet());
        System.out.println(lockCounter.sum);
    }

    public static ThreadPoolExecutor initThreadPool(){
        int coreSize = Runtime.getRuntime().availableProcessors();
        int maxSize = Runtime.getRuntime().availableProcessors()*2;
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(5000);
        ThreadFactory threadFactory = new DeamoThreadFactory();
        return new ThreadPoolExecutor(coreSize, maxSize, 100, TimeUnit.SECONDS, linkedBlockingQueue, threadFactory);
    }

}
