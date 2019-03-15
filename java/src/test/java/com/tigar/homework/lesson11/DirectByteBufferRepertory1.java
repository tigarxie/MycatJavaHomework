package com.tigar.homework.lesson11;

import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * DirectByteBuffer 缓存池 数据库仓库
 *
 * @author tigar
 * @date 2018/6/9.
 */
public class DirectByteBufferRepertory1 {

    // 最大可分配空间
    int totalCap = 0;
    // 已分配空间
    int useCap = 0;
    // 读写锁
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    // ByteBuffer 集合
    TreeSet<ByteBufferEnt> byteBufferSet = new TreeSet<>(new ByteBufferEntComparator());


    /**
     * 初始化
     *
     * @param capacity
     */
    public DirectByteBufferRepertory1(int capacity) {
        totalCap = capacity;
    }

    /**
     * 从已有空间里面，拿出一个最小可适配的空间
     *
     * @param cap
     * @return
     */
    public ByteBuffer getByteBuffer(int cap) throws Exception {
        lock.readLock().lock();
        try {
            Optional<ByteBufferEnt> optional = byteBufferSet.tailSet(new ByteBufferEnt(cap), true)
                    .stream().filter(f -> !f.isUsed()).findFirst();
            if (optional.isPresent()) {
                return optional.get().getData();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 增加空间
     *
     * @param cap
     * @throws Exception
     */
    public ByteBuffer addAndGetByteBuffer(int cap) throws Exception {
        lock.writeLock().lock();
        try {
            if (totalCap - useCap - cap <= 0)
                return null;
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(cap);
            ByteBufferEnt ent = new ByteBufferEnt();
            ent.setData(byteBuffer);
            ent.setCapacity(cap);
            ent.setUsed(true);
            byteBufferSet.add(ent);
            // 使用空间增加
            useCap += cap;
            return byteBuffer;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 获取剩余空间
     *
     * @return
     */
    public int getRemainCap() {
        return totalCap - useCap;
    }

    /**
     * 回收空间
     *
     * @return
     */
    public void retrieve(ByteBuffer byteBuffer) throws Exception {
        lock.writeLock().lock();
        try {

//            if (totalCap - useCap - cap <= 0)
//                return null;
//            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(cap);
//            ByteBufferEnt ent = new ByteBufferEnt();
//            ent.setData(byteBuffer);
//            ent.setCapacity(cap);
//            ent.setUsed(true);
//            byteBufferSet.add(ent);
//            return byteBuffer;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
