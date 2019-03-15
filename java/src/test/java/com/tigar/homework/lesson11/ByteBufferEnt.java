package com.tigar.homework.lesson11;

import java.nio.ByteBuffer;

/**
 * ByteBuffer 元素
 *
 * @author tigar
 * @date 2018/6/9.
 */
public class ByteBufferEnt {
    /**
     * 空间
     */
    private int capacity;
    /**
     * 真实数据
     */
    private ByteBuffer data;
    /**
     * 是否被使用
     */
    private boolean used;

    public static ByteBufferEnt allocate(int cap) {
        ByteBufferEnt ent = new ByteBufferEnt();
        ent.setCapacity(cap);
        ent.setData(ByteBuffer.allocateDirect(cap));
        return ent;
    }

    public ByteBufferEnt() {
    }

    public ByteBufferEnt(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ByteBuffer getData() {
        return data;
    }

    public void setData(ByteBuffer data) {
        this.data = data;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
