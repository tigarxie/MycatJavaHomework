package com.tigar.homework.lesson5;

/**
 * @author tigar
 * @date 2018/4/14.
 */
public class SynchronizeCounter implements ICounter {
    private long value = 0L;

    @Override
    public synchronized void incr() {
        value++;
    }

    @Override
    public long getCurValue() {
        return value;
    }
}
