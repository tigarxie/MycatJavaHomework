package com.tigar.homework.lesson6;

/**
 * @author tigar
 * @date 2018/4/21.
 */
public class Counter2 {

    private int count = 0;

    public synchronized void incr() {
        count++;
    }

    @Override
    public String toString() {
        return String.valueOf(count);
    }
}
