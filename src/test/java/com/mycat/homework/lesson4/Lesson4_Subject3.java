package com.mycat.homework.lesson4;

import com.mycat.homework.Common.Salary;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.math.BigInteger;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.mycat.homework.*;

/**
 * Created by Administrator on 2018-01-12.
 */
public class Lesson4_Subject3 {

    @Test
    public void TestMain() throws Exception {
        int count = 10000 * 1000;
        long startTime, endTime;

        startTime = System.currentTimeMillis();
        Salary[] arr = new Salary[count];
        IntStream.range(0, count).forEach(x->{
            arr[x] = new Salary();
        });
        endTime = System.currentTimeMillis();
        System.out.println("数组初始化：" + (endTime - startTime) + "毫秒");
        System.out.println("数组抽查：" +  arr[4]);

        startTime = System.currentTimeMillis();
        List<Salary> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Salary ent = new Salary();
            list.add(ent);
        }
        endTime = System.currentTimeMillis();
        System.out.println("list基础方法初始化：" + (endTime - startTime) + "毫秒");

        List<Salary> list1 = new ArrayList<>();
        startTime = System.currentTimeMillis();
        IntStream.range(0, count).forEach(x->{
            Salary ent = new Salary();
            list1.add(ent);
        });
        endTime = System.currentTimeMillis();
        System.out.println("list函数式方法初始化：" + (endTime - startTime) + "毫秒");

        startTime = System.currentTimeMillis();
        list.stream().filter(m -> {
            int salarySum = m.getBaseSalary() * 12 + m.getBonus();
            if (salarySum < 100000)
                return false;

            m.setSalarySum(salarySum);
            return true;
        }).collect(Collectors.groupingBy(m -> m.getName().substring(0, 2))).entrySet()
                .parallelStream().map(m -> {
            SalaryStatistics statistics = new SalaryStatistics();
            statistics.setNamePrefix(m.getKey());
            statistics.setCount(m.getValue().size());
            statistics.setSum(m.getValue().parallelStream()
                    .map(l -> new BigInteger(String.valueOf(l.getSalarySum())))
                    .reduce(new BigInteger("0"), (sum, l1) -> sum.add(l1)));
            return statistics;
        }).sorted((a1, a2) -> a2.getSum().compareTo(a1.getSum()))
                .limit(10)
                .forEach(System.out::println);
        endTime = System.currentTimeMillis();
        System.out.println("parallelStream运行速度：" + (endTime - startTime) + "毫秒");

        startTime = System.currentTimeMillis();
        list.stream().filter(m -> {
            int salarySum = m.getBaseSalary() * 12 + m.getBonus();
            if (salarySum < 100000)
                return false;

            m.setSalarySum(salarySum);
            return true;
        }).collect(Collectors.groupingBy(m -> m.getName().substring(0, 2))).entrySet()
                .stream().map(m -> {
            SalaryStatistics statistics = new SalaryStatistics();
            statistics.setNamePrefix(m.getKey());
            statistics.setCount(m.getValue().size());
            statistics.setSum(m.getValue().stream()
                    .map(l -> new BigInteger(String.valueOf(l.getSalarySum())))
                    .reduce(new BigInteger("0"), (sum, l1) -> sum.add(l1)));
            return statistics;
        }).sorted((a1, a2) -> a2.getSum().compareTo(a1.getSum()))
                .limit(10)
                .forEach(System.out::println);
        endTime = System.currentTimeMillis();
        System.out.println("stream运行速度：" + (endTime - startTime) + "毫秒");


        startTime = System.currentTimeMillis();
        list.stream().collect(new MyCollector()).stream().sorted((a1, a2) -> a2.getSum().compareTo(a1.getSum()))
                .limit(10)
                .forEach(System.out::println);
        endTime = System.currentTimeMillis();
        System.out.println("自定义Collector运行速度：" + (endTime - startTime) + "毫秒");

        System.out.println("运行结束");
    }

    /**
     * 统计结果
     */
    public class SalaryStatistics {
        // 名字前缀
        private String namePrefix;
        // 总收入
        private BigInteger sum;
        // 人数
        private Integer count;


        public String getNamePrefix() {
            return namePrefix;
        }

        public void setNamePrefix(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        public BigInteger getSum() {
            return sum;
        }

        public void setSum(BigInteger sum) {
            this.sum = sum;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "{ namePrefix:" + namePrefix + " sum:" + sum + " count:" + count + "}";
        }
    }


    @Test
    public void CustomCollecter() {

    }

    public class MyCollector implements Collector<Salary, Map<String, SalaryStatistics>, List<SalaryStatistics>> {
        @Override
        public Supplier<Map<String, SalaryStatistics>> supplier() {
            return () -> new HashMap<>();
        }

        @Override
        public BiConsumer<Map<String, SalaryStatistics>, Salary> accumulator() {
            return (a, t) -> {
                // 获取前缀
                String namePrefix = t.getName().substring(0, 2);
                // 计算收入
                int salarySum = t.getBaseSalary() * 12 + t.getBonus();
                // 开始赋值
                if (a.containsKey(namePrefix)) {
                    SalaryStatistics tp = a.get(namePrefix);
                    // 累计人数
                    tp.setCount(tp.getCount() + 1);
                    // 累计收入
                    tp.setSum(tp.getSum().add(new BigInteger(String.valueOf(salarySum))));
                } else {
                    SalaryStatistics tp = new SalaryStatistics();
                    tp.setNamePrefix(namePrefix);
                    tp.setCount(1);
                    tp.setSum(new BigInteger(String.valueOf(salarySum)));
                    a.put(namePrefix, tp);
                }
            };
        }

        @Override
        public BinaryOperator<Map<String, SalaryStatistics>> combiner() {
            return (a1, a2) -> {
                a1.putAll(a2);
                return a1;
            };
        }

        @Override
        public Function<Map<String, SalaryStatistics>, List<SalaryStatistics>> finisher() {
            return (a) -> {
                List<SalaryStatistics> r = new ArrayList<>();
                a.values().forEach(m -> r.add(m));
                return r;
            };
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));
        }
    }
}
