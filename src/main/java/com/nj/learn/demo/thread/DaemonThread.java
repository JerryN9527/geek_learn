package com.nj.learn.demo.thread;

public class DaemonThread {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread t = Thread.currentThread();
            System.out.println("获取当前线程名称："+ t.getName());
        };

        Thread thread = new Thread(task);
        thread.setName("test-thread-1");
        //开启守护线程
        //jvm会检查当前进程里是否全部是守护线程，如果是，则会终止掉所有线程
//        thread.setDaemon(true);
        thread.start();
        //让主线程执行慢点
        Thread.sleep(5001);

    }

}
