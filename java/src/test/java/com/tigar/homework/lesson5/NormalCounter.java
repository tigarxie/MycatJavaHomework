package com.tigar.homework.lesson5;

/**
 * @author tigar
 * @date 2018/4/14.
 */
public class NormalCounter implements ICounter {
    private long value = 0L;

    @Override
    public void incr() {
        value++;
    }

    @Override
    public long getCurValue() {
        return value;
    }
}
