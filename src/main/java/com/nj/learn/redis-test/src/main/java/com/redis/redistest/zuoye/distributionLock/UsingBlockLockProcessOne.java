package com.redis.redistest.zuoye.distributionLock;

import com.redis.redistest.lockcounter.DistributionLockDemo;
import com.redis.redistest.lockcounter.DistributionLockFactory;

public class UsingBlockLockProcessOne {
    public static void main(String[] args) {
//        DistributionLock lock = DistributionLockFactory.getLock(DistributionLockFactory.DLOCK_JEDIS,
//                "distributeLock", 30000);
        DistributionLockDemo lock = com.redis.redistest.lockcounter.DistributionLockFactory.getLock(DistributionLockFactory.DLOCK_LETTUCE,
                "distributeLock", 30000);
        Long start = System.currentTimeMillis();
        System.out.println("process one begin to do something ...., and now is "+ start);
        lock.acquire();
        try {  //do something but spent many time
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("process one end, do this job use "+ (System.currentTimeMillis() - start) + " ms" );
        } finally {
            lock.release();
        }
    }
}
