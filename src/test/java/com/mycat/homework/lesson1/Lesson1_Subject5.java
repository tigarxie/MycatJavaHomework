package com.mycat.homework.lesson1;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Administrator on 2017-11-24.
 */
public class Lesson1_Subject5 {

    @Test
    public void TestMain(){

        ByteStore bStroe = new ByteStore();
        int len = 1000;

        for (int i = 0; i < len; i++) {
            MyItem item = new MyItem();
            item.initValue();

            for (int j = 0; j < i - 1; j++) {
                MyItem temp = bStroe.getMyItem(j);
                if (temp.equal(item))
                    System.out.println("序号" + i + "有重复");
            }

            bStroe.putMyItem(i, item);
        }

        System.out.println("===============排序前===============");
        for (int i = 0; i < len; i++) {
            MyItem item = bStroe.getMyItem(i);
            System.out.println("type:" + String.valueOf(item.getType()) +
                    "  color:" + String.valueOf(item.getColor()) + "  price:"
                    + String.valueOf(item.getPrice()));
        }

        // 冒泡排序
        // 记录临时中间值
        MyItem temp;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (bStroe.getMyItem(i).compareTo(bStroe.getMyItem(j)) < 0) { // 交换两数的位置
                    temp = bStroe.getMyItem(i);
                    bStroe.putMyItem(i, bStroe.getMyItem(j));
                    bStroe.putMyItem(j, temp);
                }
            }
        }

        System.out.println("===============排序后===============");
        for (int i = 0; i < 10; i++) {
            MyItem item = bStroe.getMyItem(i);
            System.out.println("type:" + String.valueOf(item.getType()) +
                    "  color:" + String.valueOf(item.getColor()) + "  price:"
                    + String.valueOf(item.getPrice()));
        }
    }

    public class ByteStore {
        byte[] strore;

        public ByteStore() {
            strore = new byte[3 * 1000];
        }

        public void putMyItem(int index, MyItem item) {
            if (index < 0 || index >= 1000)
                return;

            int startIndex = index * 3;
            strore[startIndex++] = item.type;
            strore[startIndex++] = item.color;
            strore[startIndex] = item.price;
        }

        public MyItem getMyItem(int index) {
            int startIndex = index * 3;
            MyItem item = new MyItem();
            item.type = strore[startIndex++];
            item.color = strore[startIndex++];
            item.price = strore[startIndex];

            return item;
        }

    }

    public class MyItem {
        byte type;
        byte color;
        byte price;

        public void initValue() {
            // 随机数
            type = Hepler.getRandomByte();
            color = Hepler.getRandomByte();
            price = Hepler.getRandomByte();
        }

        public byte getType() {
            return type;
        }

        public int compareTo(MyItem other) {
            if (price == other.price)
                return 0;

            return price > other.price ? 1 : -1;
        }

        public boolean equal(MyItem other) {
            return type == other.type && color == other.color && price == other.price;
        }

        public void setType(byte type) {
            this.type = type;
        }

        public byte getColor() {
            return color;
        }

        public void setColor(byte color) {
            this.color = color;
        }

        public byte getPrice() {
            return price;
        }

        public void setPrice(byte price) {
            this.price = price;
        }

    }

    // 生成随机数的工具类
    private static class Hepler {
        static Random random = new Random();

        public static byte getRandomByte() {
            byte[] bytes = new byte[1];
            random.nextBytes(bytes);
            return bytes[0];
        }
    }
}
