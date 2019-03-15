package com.tigar.homework.lesson2;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017-12-03.
 */
public class s1 {

    @Test
    public void TestMain() throws UnsupportedEncodingException {

        String words = "中国";

        byte[] utf8Bytes = words.getBytes("UTF-8");
        System.out.println(utf8Bytes);

        byte[] gbkBytes = words.getBytes("gbk");
        System.out.println(gbkBytes);

        byte[] iosBytes = words.getBytes("iso-8859-1");
        System.out.println(iosBytes);

        byte[] gb2312Bytes = words.getBytes("gb2312");
        System.out.println(gb2312Bytes);

        // 以utf-8编码得到的byte[]无法用gbk的方式“还原”为原来的字符串
        // gbk编码后的字串跟utf8的不一样
        String errorTransWord = new String(utf8Bytes, "gbk");
        System.out.println(errorTransWord);

        // 正确转换，怎么来怎么去
        String correctTransWord = new String(utf8Bytes, "UTF-8");
        System.out.println(correctTransWord);
    }

}
