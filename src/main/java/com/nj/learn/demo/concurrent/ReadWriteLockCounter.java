package com.nj.learn.demo.concurrent;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用读写锁R
 */
public class ReadWriteLockCounter {
    private int sum =0;
    /**
     * 可重入锁 + 读写锁 + 公平锁
     */
    private ReadWriteLock lock = new ReentrantReadWriteLock(true);

    public int incrAndGet(){
        try {
            //使用写锁；独占锁；排他锁
            lock.writeLock().lock();
            return ++sum;
        }finally {
            lock.writeLock().unlock();
        }
    }

    public int getSum(){
        try {
            //使用读锁；共享锁；保证可见性
            lock.readLock().lock();
            return ++sum;
        }finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {

    }
}
