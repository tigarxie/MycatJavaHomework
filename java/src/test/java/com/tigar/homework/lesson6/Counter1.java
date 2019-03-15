package com.tigar.homework.lesson6;

/**
 * @author tigar
 * @date 2018/4/21.
 */
public class Counter1 {

    private static int count = 1;

    public static synchronized void incr() {
        count++;
    }

    @Override
    public String toString() {
        return String.valueOf(count);
    }
}
