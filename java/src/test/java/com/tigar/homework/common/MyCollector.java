package com.tigar.homework.common;

import java.math.BigInteger;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 自定义统计方法
 *
 * @author tigar
 * @date 2018/4/30.
 */

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