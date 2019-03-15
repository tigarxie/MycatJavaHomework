package com.tigar.homework.lesson4;

import com.tigar.homework.common.Salary;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tigar on 2018/3/28.
 */
public class s3_2 {

    @Test
    public void ArrListTest() throws Exception {
        int count = 10000 * 20;
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(i);
        }
        endTime = System.currentTimeMillis();
        System.out.println("List初始化：" + (endTime - startTime) + "毫秒");

        startTime = System.currentTimeMillis();
        Integer[] arr = new Integer[count];
        for (int i = 0; i < count; i++) {
            arr[i] = i;
        }
        endTime = System.currentTimeMillis();
        System.out.println("数组初始化：" + (endTime - startTime) + "毫秒");
    }
}
