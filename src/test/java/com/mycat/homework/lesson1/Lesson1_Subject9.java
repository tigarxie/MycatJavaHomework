package com.mycat.homework.lesson1;

import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Administrator on 2017-11-24.
 */
public class Lesson1_Subject9 {

    @Test
    public void TestMain() {
        int a = 1000;
        byte[] b = intToByteArray(a);
        System.out.println(b);
        int a1 = byteArrayToInt(b);
        System.out.println(a1);
    }

    public static int byteArrayToInt(byte[] ba) {
        return (ba[0] & 0xFF) << 24 |
                (ba[1] & 0xFF) << 16 |
                (ba[2] & 0xFF) << 8 |
                (ba[3] & 0xFF);
    }

    public static byte[] intToByteArray(int a) {

        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }
}
