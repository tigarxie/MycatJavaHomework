package com.tigar.homework.lesson11;

import java.util.Comparator;

/**
 * ByteBuffer 元素大小比较者
 * @author tigar
 * @date 2018/6/9.
 */
public class ByteBufferEntComparator implements Comparator<ByteBufferEnt> {
    @Override
    public int compare(ByteBufferEnt o1, ByteBufferEnt o2) {
        return o1.getCapacity() - o2.getCapacity();
    }
}
