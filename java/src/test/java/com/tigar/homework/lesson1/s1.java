package com.tigar.homework.lesson1;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Administrator on 2017-11-24.
 */
public class s1 {

    @Test
    public void TestMain(){

        byte ba = 127;		// 0111 1111
        byte bb = (byte) (ba << 2);		// 预期是 1111 1100  即为 -4
        System.out.println(bb);		// 实际得到-4
    }
}

