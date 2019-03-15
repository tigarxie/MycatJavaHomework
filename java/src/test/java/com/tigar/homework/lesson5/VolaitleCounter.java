package com.tigar.homework.lesson5;

/**
 * @author tigar
 * @date 2018/4/14.
 */
public class VolaitleCounter implements ICounter {
    private volatile long value = 0L;

    @Override
    public void incr() {
        value++;
    }

    @Override
    public long getCurValue() {
        return value;
    }
}
