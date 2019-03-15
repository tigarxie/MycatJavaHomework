package com.tigar.homework.lesson4;

import com.tigar.homework.common.Salary;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by tigar on 2018/3/28.
 */
public class s3_1 {

    @Test
    public void ArrListTest() throws Exception {
        int count = 10000 * 20;
        long startTime, endTime;


        startTime = System.currentTimeMillis();
        List<Salary> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Salary ent = new Salary();
            list.add(ent);
        }
        endTime = System.currentTimeMillis();
        System.out.println("list基础方法初始化：" + (endTime - startTime) + "毫秒");

        startTime = System.currentTimeMillis();
        Salary[] arr = new Salary[count];
        for (int i = 0; i < count; i++) {
            arr[i] = new Salary();
        }
        endTime = System.currentTimeMillis();
        System.out.println("数组初始化：" + (endTime - startTime) + "毫秒");

        startTime = System.currentTimeMillis();
        List<Salary> list2 = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Salary ent = new Salary();
            list2.add(i, ent);
        }
        endTime = System.currentTimeMillis();
        System.out.println("list定义长度基础方法初始化：" + (endTime - startTime) + "毫秒");

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
        System.out.println("流运行速度：" + (endTime - startTime) + "毫秒");

        startTime = System.currentTimeMillis();
        Arrays.stream(arr).filter(m -> {
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
        System.out.println("数组行速度：" + (endTime - startTime) + "毫秒");
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

}
