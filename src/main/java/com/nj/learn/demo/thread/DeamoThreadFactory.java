package com.nj.learn.demo.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DeamoThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        AtomicInteger atomicInteger = new AtomicInteger();
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.setName("zidingyiThread---"+ atomicInteger.getAndIncrement());
        return thread;
    }
}
