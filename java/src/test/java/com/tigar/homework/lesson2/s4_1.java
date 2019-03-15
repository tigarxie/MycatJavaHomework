package com.tigar.homework.lesson2;

import com.tigar.homework.common.Salary;
import com.tigar.homework.common.SalaryNew;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2018-03-15.
 */
public class s4_1 {

    @Test
    public void TestWrite() {
        String filePath = "c:/rtest.txt";
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        long startTime, endTime;
        int objectCount = 1000 * 1000;

        try {
            fileOutputStream = new FileOutputStream(filePath);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            startTime = System.currentTimeMillis();
            for (int i = 0; i < objectCount; i++) {
                SalaryNew salary = new SalaryNew();
                salary.init();
                objectOutputStream.writeObject(salary);
            }
            endTime = System.currentTimeMillis();
            System.out.println("写文件时间：" + (endTime - startTime) + "毫秒");

//            RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw");
//            FileChannel inFileChannel = randomAccessFile.getChannel();

        } catch (Exception ex) {

        } finally {
            try {
                if (objectOutputStream != null)
                    objectOutputStream.close();

                if (bufferedOutputStream != null)
                    bufferedOutputStream.close();

                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (Exception exc) {
            }
        }
    }

    @Test
    public void TestNioWrite() {
        String filePath = "c:/rtest.txt";
        FileChannel fcout = null;
        long startTime, endTime;
        int objectCount = 1000 * 1000;

        try {
            fcout = new FileOutputStream(filePath).getChannel();
            Charset cn = Charset.forName("utf-8");
            startTime = System.currentTimeMillis();
            for (int i = 0; i < objectCount; i++) {
                Salary ent = new Salary();
                fcout.write(cn.encode(ent.toString() + "\r\n"));
            }
            endTime = System.currentTimeMillis();
            System.out.println("写文件时间：" + (endTime - startTime) + "毫秒");

//            RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw");
//            FileChannel inFileChannel = randomAccessFile.getChannel();

        } catch (Exception ex) {

        } finally {
            try {
                if (fcout != null)
                    fcout.close();

            } catch (Exception exc) {
            }
        }
    }

    @Test
    public void TestRead() {
        String filePath = "c:/rtest.txt";
        RandomAccessFile inRandomAccessFile = null;
        FileChannel inFileChannel = null;
        MappedByteBuffer inMappedByteBuffer = null;
        long startTime, endTime;
        int objectCount = 1000 * 1000;

        try {
            inRandomAccessFile = new RandomAccessFile(filePath, "rw");
            inFileChannel = inRandomAccessFile.getChannel();
            inMappedByteBuffer = inFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inFileChannel.size());
            Charset cn = Charset.forName("utf-8");

            startTime = System.currentTimeMillis();
            for (int i = 0; i < objectCount; i++) {
                // 初始化，并写入
                Salary ent = new Salary();
                inMappedByteBuffer.put(cn.encode(ent.toString() + "\r\n"));
                inMappedByteBuffer.flip();
            }
            endTime = System.currentTimeMillis();
            System.out.println("写文件时间：" + (endTime - startTime) + "毫秒");

        } catch (Exception ex) {
        } finally {
            try {
                if (inRandomAccessFile != null)
                    inRandomAccessFile.close();

                if (inFileChannel != null)
                    inFileChannel.close();
            } catch (Exception exc) {

            }
        }
    }

    // 收入统计实体
    public class SalaryStatistics {
        // 名字前缀
        private String namePrefix;
        // 收入
        private BigInteger salary;
        // 人数
        private BigInteger count;

        public String getNamePrefix() {
            return namePrefix;
        }

        public void setNamePrefix(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        public BigInteger getSalary() {
            return salary;
        }

        public void setSalary(BigInteger salary) {
            this.salary = salary;
        }

        public BigInteger getCount() {
            return count;
        }

        public void setCount(BigInteger count) {
            this.count = count;
        }
    }

    // 收入统计实体
    public class SalaryStatisticsNew {
        // 名字前缀
        private String namePrefix;
        /* ----------------- 收入总和可能会超过long的最大值，所以必须拆分成两个int存储 -------------------- */
        // 收入高位
        private int salaryHightPosition = 0;
        // 收入总和的个位
        private int salaryLowPosition = 0;
        // 收入高位低位分割点
        private int splitPosition = 10000;      // 100000000  10000
        // 人数
        private int count = 0;

        public SalaryStatisticsNew(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        // 增加收入的方法
        public void AddSalaryStatistics(int addSalary) {
            salaryLowPosition += addSalary;
            while (salaryLowPosition > splitPosition) {
                salaryHightPosition++;
                salaryLowPosition -= splitPosition;
            }
            count++;
        }

        public String getNamePrefix() {
            return namePrefix;
        }

        public int getSalaryHightPosition() {
            return salaryHightPosition;
        }

        public int getSalaryLowPosition() {
            return salaryLowPosition;
        }

        public int getCount() {
            return count;
        }
    }

    @Test
    public void BigTest() {
        String filePath = "c:/rtest.txt";
        RandomAccessFile inRandomAccessFile = null;
        FileChannel inFileChannel = null;
        MappedByteBuffer inMappedByteBuffer = null;
        long startTime, endTime;
        int objectCount = 1000 * 10000;

        try {
            Salary[] list = new Salary[objectCount];

            startTime = System.currentTimeMillis();
            for (int i = 0; i < objectCount; i++) {
                // 初始化，并写入
                Salary ent = new Salary();
                list[i] = ent;
            }
            System.out.println("开始计时");
            startTime = System.currentTimeMillis();
            Arrays.stream(list).collect(Collectors.groupingBy(m -> m.getName().substring(0, 2))).entrySet()
                    .stream().map(m -> {
                // 生成实体
                SalaryStatisticsNew statistic = new SalaryStatisticsNew(m.getKey());
                m.getValue().parallelStream().
                        forEach(s -> statistic.AddSalaryStatistics(s.getBaseSalary() * 13 + s.getBonus()));
                return statistic;
            }).sorted((a1, a2) -> {
                        if (a1.getSalaryHightPosition() > a2.getSalaryHightPosition())
                            return -1;
                        if (a1.getSalaryHightPosition() < a2.getSalaryHightPosition())
                            return 1;
                        if (a1.getSalaryLowPosition() > a2.getSalaryLowPosition())
                            return -1;
                        if (a1.getSalaryLowPosition() < a2.getSalaryLowPosition())
                            return 1;
                        return 0;
                    }
            ).limit(20)
            .forEach(k -> System.out.println(k.getNamePrefix() + " "
                    + k.getSalaryHightPosition() + "万" + k.getSalaryLowPosition() + " " + k.getCount()));
            endTime = System.currentTimeMillis();
            System.out.println("计算时间：" + (endTime - startTime) + "毫秒");

        } catch (Exception ex) {
        } finally {
            try {
                if (inRandomAccessFile != null)
                    inRandomAccessFile.close();

                if (inFileChannel != null)
                    inFileChannel.close();
            } catch (Exception exc) {

            }
        }
    }

    @Test
    public void CustomBigTest() {
        String filePath = "c:/rtest.txt";
        RandomAccessFile inRandomAccessFile = null;
        FileChannel inFileChannel = null;
        MappedByteBuffer inMappedByteBuffer = null;
        long startTime, endTime;
        int objectCount = 1000 * 10000;

        try {
            Salary[] list = new Salary[objectCount];

            startTime = System.currentTimeMillis();
            for (int i = 0; i < objectCount; i++) {
                // 初始化，并写入
                Salary ent = new Salary();
                list[i] = ent;
            }
            System.out.println("开始计时");
            startTime = System.currentTimeMillis();
            Arrays.stream(list).collect(Collectors.groupingBy(m -> m.getName().substring(0, 2))).entrySet().stream().map(m -> {
                // 计算工资总额
                BigInteger salarySum = m.getValue().parallelStream().map(s -> new BigInteger(String.valueOf(s.getBaseSalary() * 13 + s.getBonus())))
                        .reduce(new BigInteger("0"), (sum, p) -> sum.add(p));
                // 生成实体
                SalaryStatistics statistic = new SalaryStatistics();
                statistic.setNamePrefix(m.getKey());
                statistic.setCount(new BigInteger(String.valueOf(m.getValue().size())));
                statistic.setSalary(salarySum);
                return statistic;
            }).sorted((a1, a2) -> a2.getSalary().compareTo(a1.getSalary())).limit(20)
                    .forEach(k -> System.out.println(k.getNamePrefix() + " " + k.getSalary() + " " + k.getCount()));
            endTime = System.currentTimeMillis();
            System.out.println("计算时间：" + (endTime - startTime) + "毫秒");

        } catch (Exception ex) {
        } finally {
            try {
                if (inRandomAccessFile != null)
                    inRandomAccessFile.close();

                if (inFileChannel != null)
                    inFileChannel.close();
            } catch (Exception exc) {

            }
        }
    }
}
