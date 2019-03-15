package com.tigar.homework.lesson2;

import org.omg.CORBA.CharHolder;
import org.junit.Test;

import javax.swing.*;
import java.io.*;
import java.util.function.Function;

/**
 * Created by Administrator on 2017-12-03.
 */
public class s2 {

    @Test
    public void TestMain() throws UnsupportedEncodingException {

        int words = 10240;

        String path = "c:/s2.txt";
        String charset = "utf-8";

        File file = new File(path);

        // 小头模式
        byte[]  b1 = int2BigEndianByte(words);
        System.out.println("小头模式转换："  + b1[0] + " " + b1[1] + " " + b1[2] + " " + b1[3] + " 还原数据：" + bigEndianByte2Int(b1));


        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(b1);
            fos.close();

            FileInputStream fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int len = fis.read(buf, 0, buf.length);
            fis.close();

            System.out.println("文本还原数据："  + bigEndianByte2Int(buf));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 大头模式
        byte[]  b2 = int2SmallEndianByte(words);
        System.out.println("大头模式转换："  + b2[0] + " " + b2[1] + " " + b2[2] + " " + b2[3] + " 还原数据：" + smallEndianByte2Int(b2));


        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(b2);
            fos.close();

            FileInputStream fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int len = fis.read(buf, 0, buf.length);
            fis.close();

            System.out.println("文本还原数据："  + smallEndianByte2Int(buf));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 清理文件
        if(file.exists())
            file.delete();
    }

    // byte转换为char
    public  char[] byte2Char(byte[] input)
    {
        return  null;
    }

    // 小头模式转换
    public byte[] int2BigEndianByte(int input)
    {
        return  new byte[]{
                (byte)(input & 0xFF),
                (byte)((input >> 8) & 0xFF),
                (byte)((input >> 16) & 0xFF),
                (byte)((input >> 24) & 0xFF)
        };
    }
    public int bigEndianByte2Int(byte[] input)
    {
        return  ((input[3] & 0xFF) << 24) |
                ((input[2] & 0xFF) << 16) |
                ((input[1] & 0xFF) << 8) |
                (input[0] & 0xFF);
    }

    // 大头模式转换
    public byte[] int2SmallEndianByte(int input)
    {
        return  new byte[]{
                (byte)((input >> 24) & 0xFF),
                (byte)((input >> 16) & 0xFF),
                (byte)((input >> 8) & 0xFF),
                (byte)(input & 0xFF)
        };
    }
    public int smallEndianByte2Int(byte[] input)
    {
        return  ((input[0] & 0xFF) << 24) |
                ((input[1] & 0xFF) << 16) |
                ((input[2] & 0xFF) << 8) |
                (input[3] & 0xFF);
    }
}
