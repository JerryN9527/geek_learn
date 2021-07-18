package com.nj.learn.demo.concurrent.muban.conc0303.tool;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;

public class CyclicBarrierTask implements  Runnable {
    private CyclicBarrier cyclicBarrier;
    public CyclicBarrierTask(CyclicBarrier cyclicBarrier){
        this.cyclicBarrier = cyclicBarrier;
    }


    @Override
    public void run() {
        int i = new Random().nextInt(100);
        try {
            Thread.sleep(i);
            this.cyclicBarrier.await();
            System.out.println("开吃："+Thread.currentThread().getName());
            Thread.sleep(i);
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        while (true) {
            int num = 4;
            CyclicBarrier cyclicBarrier = new CyclicBarrier(num);
            List<CompletableFuture> list = new ArrayList<>(num);
            for (int i = 0; i < num; i++) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(new CyclicBarrierTask(cyclicBarrier));
                System.out.println("运行次数" + i + "次");
                list.add(future);
            }

            for (CompletableFuture f : list) {
                f.get();
            }

        }
    }
}
