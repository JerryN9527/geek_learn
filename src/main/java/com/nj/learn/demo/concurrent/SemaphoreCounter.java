package com.nj.learn.demo.concurrent;

import java.util.concurrent.Semaphore;

public class SemaphoreCounter {
    private int sum = 0;
    /**
     * 设置100个并发，开启公平锁
     */
    private Semaphore readSemaphore = new Semaphore(100,true);

    /**
     * 设置一个并发，相当于一个synchronized
     */
    private Semaphore writerSemaphore = new Semaphore(1);

    public int inrAndGet(){
        try {
            writerSemaphore.acquireUninterruptibly();
            return ++sum;
        }finally {
            //放锁
            writerSemaphore.release();
        }
    }

    public int getSum(){
        try {
            readSemaphore.acquireUninterruptibly();
            return sum;
        }finally {
            //放锁
            readSemaphore.release();
        }
    }

}
