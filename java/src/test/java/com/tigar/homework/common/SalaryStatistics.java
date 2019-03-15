package com.tigar.homework.common;

import java.math.BigInteger;

/**
 * 统计结果
 * @author tigar
 * @date 2018/4/30.
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
