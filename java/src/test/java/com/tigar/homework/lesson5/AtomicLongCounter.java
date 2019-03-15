package com.tigar.homework.lesson5;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author tigar
 * @date 2018/4/14.
 */
public class AtomicLongCounter implements ICounter {
    private AtomicLong value = new AtomicLong(0);

    @Override
    public synchronized void incr() {
        value.getAndIncrement();
    }

    @Override
    public long getCurValue() {
        return value.longValue();
    }
}
