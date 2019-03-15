package com.tigar.homework.lesson5;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author tigar
 * @date 2018/4/14.
 */
public class LongAdderCounter implements ICounter {
    private LongAdder value = new LongAdder();

    @Override
    public void incr() {
        value.increment();
    }

    @Override
    public long getCurValue() {
        return value.longValue();
    }
}
