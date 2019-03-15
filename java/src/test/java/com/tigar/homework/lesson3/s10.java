package com.tigar.homework.lesson3;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Administrator on 2017-12-23.
 */
public class s10 {

    @Test
    public void TestMain() throws UnsupportedEncodingException {

        System.out.println("运行结果");
        Map map = new IdentityHashMap();
        Integer a=5;   // Integer.valueOf(5);
        Integer b=5;   // Integer.valueOf(5);
        map.put(a,"100");
        map.put(b,"100");
        System.out.println(map.size());
        map.clear();
        Integer a1=Integer.MAX_VALUE-1;
        Integer b1=Integer.MAX_VALUE-1;
        map.put(a1,"100");
        map.put(b1,"100");
        System.out.println(map.size());

    }

}
