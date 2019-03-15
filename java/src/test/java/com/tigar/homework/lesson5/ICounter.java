package com.tigar.homework.lesson5;

/**
 * @author tigar
 * @date 2018/4/14.
 */
public interface ICounter {

    /**
     * 自增
     */
    void incr();

    /**
     * 得到最后结果
     *
     * @return
     */
    long getCurValue();
}