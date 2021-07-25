package com.nj.learn.concurrent;

import java.util.concurrent.*;

/**
 * 2.（必做）思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这
 * 个方法的返回值后，退出主线程? 写出你的方法，越多越好，提交到 GitHub。
 */
public class Homework {



    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(Thread.currentThread().getName()+"-->主线程开始运行：");
        //第一种  FutureTask+callable
        System.out.println("-----------------第1种：----------------");
        long start = System.currentTimeMillis();
        Callable callable =()-> {
                return worker();
        };
        FutureTask<String> future = new FutureTask<>(callable);
        Thread t = new Thread(future);
        t.start();
        String result = future.get();
        System.out.println(Thread.currentThread().getName()+"-->主线程获取返回值：--"+result);
        System.out.println(Thread.currentThread().getName()+"-->退出主线程,使用时间："+(System.currentTimeMillis()-start));

        //第二种  FutureTask+callable
        System.out.println("-----------------第2种：----------------");
        start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future2 = executorService.submit(callable);
        result = (String) future2.get();
        executorService.shutdown();
        System.out.println(Thread.currentThread().getName()+"-->主线程获取返回值：--"+result);
        System.out.println(Thread.currentThread().getName()+"-->退出主线程,使用时间："+(System.currentTimeMillis()-start));
        //第3种 伪通知
        System.out.println("-----------------第3种：----------------");
        flag=false;
        start = System.currentTimeMillis();
        new Thread(()->{
                worker();
        }).start();
        result = getResult();
        System.out.println(Thread.currentThread().getName()+"-->主线程获取返回值：--"+result);
        System.out.println(Thread.currentThread().getName()+"-->退出主线程,使用时间："+(System.currentTimeMillis()-start));
        //第4种  CompletableFuture
        System.out.println("-----------------第4种：----------------");
        start = System.currentTimeMillis();
        result = CompletableFuture.supplyAsync(()->worker()).get();
        System.out.println(Thread.currentThread().getName()+"-->主线程获取返回值：--"+result);
        System.out.println(Thread.currentThread().getName()+"-->退出主线程,使用时间："+(System.currentTimeMillis()-start));
        //第5种 notify 唤醒 wait
        System.out.println("-----------------第5种：----------------");
        start = System.currentTimeMillis();
        new Thread(()->{
            synchronized (object){
                Homework.result=worker();
                object.notify();
            }
        }).start();
        synchronized (object){
            object.wait();
        }
        System.out.println(Thread.currentThread().getName()+"-->主线程获取返回值：--"+Homework.result);
        System.out.println(Thread.currentThread().getName()+"-->退出主线程,使用时间："+(System.currentTimeMillis()-start));

    }

    private static Object object=new Object();
    private static String result;
    private static volatile boolean  flag;

    public static  String  getResult(){
        while (true){
                if (flag){
                    flag=false;
                    return result;
                }
        }
    }
    public synchronized static String worker()  {
        System.out.println(Thread.currentThread().getName()+"-->方法worker运行");
        //第3种
        result="帅气de返回值";
        flag=true;
        return "帅气de返回值";
    }
}