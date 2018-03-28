package com.mycat.homework.lesson1;

import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Administrator on 2017-11-24.
 */
public class Lesson1_Subject3 {

    @Test
    public void TestMain(){
        byte[][] arr = new byte[10240][10240];

        long begintime, endtime;

        begintime = System.currentTimeMillis();
        // 行优先
        for (int x = 0; x < 10240; x++) {
            for (int y = 0; y < 10240; y++) {
                arr[x][y] = 1;
            }
        }
        endtime = System.currentTimeMillis();
        System.out.println(String.format("%d",endtime - begintime));

        // 列优先
        begintime = System.currentTimeMillis();
        for (int x = 0; x < 10240; x++) {
            for (int y = 0; y < 10240; y++) {
                arr[y][x] = 1;
            }
        }
        endtime = System.currentTimeMillis();
        System.out.println(String.format("%d", endtime - begintime));
    }
}
