package com.tigar.homework.lesson4;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2018-01-07.
 */
public class s2 {

    @Test
    public void TestMain() throws UnsupportedEncodingException {

        System.out.println("题目要求测试：");
        Stream.of("d2", "a2", "b1", "b3", "c").filter(s -> {
            System.out.println("filter: " + s);
            return true;
        }).forEach(s -> System.out.println("forEach: " + s));


        System.out.println("对比测试1：");
        Stream.of("d2", "a2", "b1", "b3", "c").filter(s -> {
            System.out.println("filter: " + s);
            return true;
        });

        System.out.println("对比测试2：");
        Stream.of("d2", "a2", "b1", "b3", "c").forEach(s -> System.out.println("forEach: " + s));
    }

}
