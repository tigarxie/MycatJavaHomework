package com.tigar.homework.lesson6;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author tigar
 * @date 2018/4/21.
 */
public class s4 {

    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    private static Lock readLock;
    private static Lock writeLock;
    private int count = 0;

    public static void init() {
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    @Test
    public void TestWait() throws Exception {
        count = 0;

//        List<Thread> thrs = IntStream.range(0, 10).map(m -> {
//            if (m < 4) {
//                // 四个写线程
//
//                IntStream.range(0, 10).forEach(i -> {
//                    // 读锁
//                    try {
//                        readLock.tryLock(10, TimeUnit.MINUTES);
//                        System.out.println("当前线程：" + m + " 读值：" + count);
//                    } catch (InterruptedException e) {
//
//                    } finally {
//                        readLock.unlock();
//                    }
//                });
//            } else {
//                // 六个读线程
//
//                IntStream.range(0, 10).forEach(i -> {
//                    // 写锁
//                    try {
//                        writeLock.tryLock(10, TimeUnit.MINUTES);
//                        count++;
//                        System.out.println("当前线程：" + m + " 写后值：" + count);
//                    } catch (InterruptedException e) {
//
//                    } finally {
//                        writeLock.unlock();
//                    }
//                });
//            }
//        });

    }

}
