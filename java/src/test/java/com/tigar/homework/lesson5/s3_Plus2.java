package com.tigar.homework.lesson5;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * @author tigar
 * @date 2018/4/14.
 */
public class s3_Plus2 {
    @Test
    public void TestMain() throws Exception {
        String[] source = new String[10];
        AtomicReferenceArray<String> list = new AtomicReferenceArray<String>(source);
        list.getAndSet(1, "d");
        list.compareAndSet(0, null, "b");
    }
}
