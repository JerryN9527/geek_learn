package com.redis.redistest.zuoye.distributionLock;

/**
 * 分布式锁
 */
public interface DistributionLock {

    /**
     * 获取锁 每 10毫秒获取一次
     */
    void acquire();

    /**
     * 获取锁
     */
    boolean tryAcquire();

    /**
     * 释放锁
     */
    boolean release();
}

