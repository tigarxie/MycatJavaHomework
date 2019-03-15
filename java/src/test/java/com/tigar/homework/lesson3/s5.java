package com.tigar.homework.lesson3;

import org.junit.Test;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.*;
import java.util.List;

/**
 * Created by Administrator on 2017-12-24.
 */
public class s5 {


    @Test
    public void TestMain() throws UnsupportedEncodingException {

        String[] a = new String[]{ "a", "b", "c"} ;
        List aList = Arrays.asList(a);
        aList.set(1, "d");
        System.out.println("运行结果：");
        for(String a1 : a)
        {
            System.out.println(a1);
        }
    }

}
