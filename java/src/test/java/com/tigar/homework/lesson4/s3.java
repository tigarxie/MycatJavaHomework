package com.tigar.homework.lesson4;

import com.tigar.homework.common.MyCollector;
import com.tigar.homework.common.Salary;
import com.tigar.homework.common.SalaryStatistics;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2018-01-12.
 */
public class s3 {

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


    @Test
    public void CustomCollecter() {

    }

}
