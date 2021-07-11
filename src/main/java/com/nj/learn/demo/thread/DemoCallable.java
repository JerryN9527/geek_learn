package com.nj.learn.demo.thread;

import java.util.Random;
import java.util.concurrent.*;

public class DemoCallable implements Callable {
    @Override
    public Object call() throws Exception {

        return null;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<Integer> a= ()->{

            int i = new Random().nextInt(5000);
            Thread.sleep(i);
            System.out.println(Thread.currentThread().getName()+"  : "+i);
            return i;

        };
        ExecutorService executorService = initThreadPoolExecutor();
        Future<Integer> future = executorService.submit(a);
        Integer o = 0;
        try {
            o = future.get();
//            o = future.get(10, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("超时");
        }
        System.out.println("主线程:"+Thread.currentThread().getName()+"---："+o);
    }

    private static ExecutorService initThreadPoolExecutor() {
        int coreSize = Runtime.getRuntime().availableProcessors();
        int maxSize = Runtime.getRuntime().availableProcessors()*2;
        LinkedBlockingQueue workQueue = new LinkedBlockingQueue(500);
        ThreadFactory threadFactory = new DeamoThreadFactory();
        return new ThreadPoolExecutor(coreSize,maxSize,1, TimeUnit.MINUTES,workQueue,threadFactory);
    }
}
