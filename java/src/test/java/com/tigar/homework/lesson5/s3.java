package com.tigar.homework.lesson5;

import org.junit.Test;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author tigar
 * @date 2018/4/14.
 */
public class s3 {

    @Test
    public void TestMain() throws Exception {
        List<Class> classes = Arrays.asList(NormalCounter.class, VolaitleCounter.class,
                SynchronizeCounter.class, AtomicLongCounter.class, LongAdderCounter.class);
        classes.forEach(classz -> {
            try {
                final ExecutorService threadPool = Executors.newFixedThreadPool(10);
                long startTime, endTime;
                ICounter instance = (ICounter) classz.newInstance();
                // 开始计时
                startTime = System.currentTimeMillis();
                // 初始化线程
                IntStream.range(0, 10).forEach(index -> {
                            threadPool.execute(new Runnable() {
                                @Override
                                public void run() {
                                    IntStream.range(0, 1000000).forEach(
                                            thr -> {
                                                instance.incr();
                                            }
                                    );
                                }
                            });
                        }
                );
                threadPool.shutdown();
                threadPool.awaitTermination(1, TimeUnit.HOURS);
                // 结束计时
                endTime = System.currentTimeMillis();
                System.out.println(String.format("%s，运行结果：%d，时间：%d毫秒",
                        instance.getClass().getName(), instance.getCurValue(), (endTime - startTime)));
            } catch (Exception ex) {
            } finally {

            }
        });

//        for (Class classz : classes) {
//            try {
//                long startTime, endTime;
//                ICounter instance = (ICounter) classz.newInstance();
//                // 开始计时
//                startTime = System.currentTimeMillis();
//                // 初始化线程
//                IntStream.range(0, 10).forEach(index -> {
//                            threadPool.execute(new Runnable() {
//                                @Override
//                                public void run() {
//                                    IntStream.range(0, 1000000).forEach(
//                                            thr -> {
//                                                instance.incr();
//                                            }
//                                    );
//                                }
//                            });
//                        }
//                );
//                threadPool.shutdown();
//                threadPool.awaitTermination(1, TimeUnit.HOURS);
//                // 结束计时
//                endTime = System.currentTimeMillis();
//                System.out.println(String.format("%s，运行结果：%d，时间：%d毫秒", instance.getClass().getName(), instance.getCurValue(), (endTime - startTime)));
//            }
//            finally {
//
//            }
//        }


    }

}
