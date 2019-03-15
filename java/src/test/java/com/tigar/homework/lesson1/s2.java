package com.tigar.homework.lesson1;


import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Administrator on 2017-11-24.
 */
public class s2 {

    @Test
    public void TestMain(){
		int a=-1024;	// 11111111111111111111110000000000
		// >> 右移
		// 期望：11111111111111111111111000000000
		// 十进制：-512
		int b1 = a>>1;

		// >>> 无符号右移
		// 期望：01111111111111111111111000000000
		// 十进制：2147483136
		int b2 = a>>>1;

		System.out.println(b1);
		System.out.println(b2);
	}

}
