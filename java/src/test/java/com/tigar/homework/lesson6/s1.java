package com.tigar.homework.lesson6;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author tigar
 * @date 2018/4/21.
 */
public class s1 {

    @Test
    public void CounterTest() throws Exception {
        int tryCount = 10;
        {
            List<Counter1> list = new ArrayList<>();
            final ExecutorService threadPool = Executors.newFixedThreadPool(tryCount);
            IntStream.range(0, tryCount).forEach(m->{
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        Counter1 instance = new Counter1();
                        list.add( instance);
                        IntStream.range(0, 1000000).forEach(
                                thr -> {
                                    instance.incr();
                                }
                        );
                    }
                });
            });
            threadPool.shutdown();
            threadPool.awaitTermination(1, TimeUnit.HOURS);
            list.stream().forEach(System.out::println);
        }
        System.out.println("==========================");
        {
            List<Counter2> list = new ArrayList<>();
            final ExecutorService threadPool = Executors.newFixedThreadPool(tryCount);
            IntStream.range(0, tryCount).forEach(m->{
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        Counter2 instance = new Counter2();
                        list.add( instance);
                        IntStream.range(0, 1000000).forEach(
                                thr -> {
                                    instance.incr();
                                }
                        );
                    }
                });
            });
            threadPool.shutdown();
            threadPool.awaitTermination(1, TimeUnit.HOURS);
            list.stream().forEach(System.out::println);
        }
    }
};
