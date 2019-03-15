package com.tigar.homework.lesson11;

import java.nio.ByteBuffer;

/**
 * DirectByteBuffer 缓存池
 *
 * @author tigar
 * @date 2018/6/9.
 */
public class DirectByteBufferPool {
    // 最大可分配空间
    private final int MAX_CAPACITY = 1024 * 1024;
    // 当前分配的空间
    private int limitCapacity = 0;
    // 数据库仓库
    private DirectByteBufferRepertory repertory;

    /**
     * 初始化
     */
    public DirectByteBufferPool() {
        limitCapacity = MAX_CAPACITY;
        repertory = new DirectByteBufferRepertory(limitCapacity);
    }

    /**
     * 初始化
     *
     * @param capacity 最大可分配空间
     */
    public DirectByteBufferPool(int capacity) {
        if (capacity < MAX_CAPACITY) {
            limitCapacity = capacity;
        }
        else {
            limitCapacity = MAX_CAPACITY;
        }
        repertory = new DirectByteBufferRepertory(limitCapacity);
    }

    /**
     * 分配空间
     *
     * @param cap
     * @return
     * @throws Exception
     */
    public ByteBuffer allocate(int cap) throws Exception {
        if (cap > limitCapacity) {
            throw new Exception("需分配的空间超过最大空间");
        }
        ByteBuffer byteBuffer = repertory.getByteBuffer(cap);
        if (byteBuffer == null) {
            throw new Exception("分配失败");
        }
        return byteBuffer;
    }

    /**
     * 获取剩余空间
     *
     * @return
     */
    public int getRemain() {
        return repertory.getRemainCap();
    }

    /**
     * 归还空间
     *
     * @param byteBuffer
     */
    public void free(ByteBuffer byteBuffer) throws Exception {
        repertory.retrieve(byteBuffer);
    }

}
