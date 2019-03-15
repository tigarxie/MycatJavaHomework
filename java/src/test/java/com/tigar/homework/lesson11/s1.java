package com.tigar.homework.lesson11;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author tigar
 * @date 2018/6/9.
 */
public class s1 {

    @Test
    public void t1() throws Exception {
        TreeSet<T1> treeSet = new TreeSet<T1>(new T1Comp());
        treeSet.add(new T1(1, "2"));
        treeSet.add(new T1(2, "2"));
        treeSet.add(new T1(1, "2"));
        treeSet.add(new T1(1, "2"));

    }

    public class T1Comp implements Comparator<T1> {
        @Override
        public int compare(T1 o1, T1 o2) {
            return o1.getId() - o2.getId();
        }
    }

    public class T1 {
        private Integer id;
        private String Name;

        public T1(Integer id, String name) {
            this.id = id;
            Name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }


    @Test
    public void t2() throws Exception {

        DirectByteBufferPool pool = new DirectByteBufferPool(50 * 3);
        System.out.println("程序开始，剩余空间：" + pool.getRemain());
        ByteBuffer buffer1 = pool.allocate(60);
        System.out.println("分配了50，剩余空间：" + pool.getRemain());
        ByteBuffer buffer2 = pool.allocate(80);
        System.out.println("分配了80，剩余空间：" + pool.getRemain());
        pool.free(buffer1);
        pool.free(buffer2);
        System.out.println("回收了60 + 80，剩余空间：" + pool.getRemain());
        buffer1 = pool.allocate(10);
        buffer2 = pool.allocate(60);
        System.out.println("再分配了10 + 60，剩余空间：" + pool.getRemain());
        try {
            ByteBuffer buffer3 = pool.allocate(40);
            System.out.println("再分配了40，剩余空间：" + pool.getRemain());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
