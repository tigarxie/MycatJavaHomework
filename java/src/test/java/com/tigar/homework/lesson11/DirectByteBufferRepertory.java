package com.tigar.homework.lesson11;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * DirectByteBuffer 缓存池 数据库仓库
 * <p>
 * 真正放数据结构的地方
 *
 * @author tigar
 * @date 2018/6/9.
 */
public class DirectByteBufferRepertory {

    // 最大可分配空间
    int totalCap = 0;
    // 已分配空间
    int useCap = 0;
    // 读写锁
    static Object lock = new Object();
    // 空闲集合
    DirectByteBufferTreeMap freeByteBufferSet = new DirectByteBufferTreeMap();
    // 正在使用集合
    DirectByteBufferTreeMap usedByteBufferSet = new DirectByteBufferTreeMap();

    /**
     * 初始化
     *
     * @param capacity
     */
    public DirectByteBufferRepertory(int capacity) {
        totalCap = capacity;
    }

    /**
     * 获取剩余空间
     *
     * @return
     */
    public int getRemainCap() {
        synchronized (lock) {
            return totalCap - useCap;
        }
    }

    /**
     * 从已有空间里面，拿出一个最小可适配的空间
     *
     * @param cap
     * @return
     */
    public ByteBuffer getByteBuffer(int cap) throws Exception {
        synchronized (lock) {
            if (totalCap - useCap - cap <= 0)
                return null;
            // 从空闲区获取
            ByteBuffer buffer = freeByteBufferSet.getByKey(cap);
            if (buffer == null) {
                buffer = ByteBuffer.allocateDirect(cap);
                usedByteBufferSet.addByValue(buffer);
            } else {
                // 从空闲空间树减少
                freeByteBufferSet.removeByValue(buffer);
                // 从已用空间树增加
                usedByteBufferSet.addByValue(buffer);
            }
            // 已用空间增加
            useCap += buffer.capacity();
            return buffer;
        }
    }
    /**
     * 回收空间
     *
     * @return
     */
    public void retrieve(ByteBuffer byteBuffer) throws Exception {
        synchronized (lock) {
            // 从已用空间树减少
            usedByteBufferSet.removeByValue(byteBuffer);
            // 从空闲空间树增加
            freeByteBufferSet.addByValue(byteBuffer);
            useCap -= byteBuffer.capacity();
        }
    }
}
