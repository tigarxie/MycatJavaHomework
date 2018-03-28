package com.mycat.homework.Common;

import java.util.Random;

/**
 * Created by Administrator on 2017-12-16.
 */

// 生成随机数的工具类
public class RandomHepler {
    static Random random = new Random();

    public static String getRandomString(int length) { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomName() {
        // name长度为5，
        return getRandomString(5);
    }

    public static int getRandomSalary() {
        // baseSalary范围是5-100万，
        return random.nextInt(20 * 10000) * 5;
    }

    public static int getRandomBonus() {
        // bonus为（0-10万）
        return random.nextInt(10 * 10000);
    }
}