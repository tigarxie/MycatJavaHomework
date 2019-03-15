package com.tigar.homework.lesson11;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 特有树
 * <p>
 * 树中有列表主要是防止已经分配了1024，又来一个请求分配1024
 *
 * @author tigar
 * @date 2018/6/9.
 */
public class DirectByteBufferTreeMap extends TreeMap<Integer, List<ByteBuffer>> {

    /**
     * 添加值
     *
     * @param buffer
     */
    public void addByValue(ByteBuffer buffer) {
        if (containsKey(buffer.capacity())) {
            ceilingEntry(buffer.capacity()).getValue().add(buffer);
        } else {
            List<ByteBuffer> list = new ArrayList<>();
            list.add(buffer);
            put(buffer.capacity(), list);
        }
    }

    /**
     * 根据值移除
     *
     * @param buffer
     * @throws Exception
     */
    public void removeByValue(ByteBuffer buffer) throws Exception {
        if (containsKey(buffer.capacity()) && ceilingEntry(buffer.capacity()).getValue().remove(buffer)) {
            if (ceilingEntry(buffer.capacity()).getValue().size() <= 0) {
                remove(buffer.capacity());
            }
            return;
        }

        throw new Exception("ByteBuffer 不存在");
    }

    /**
     * 根据键获取
     *
     * @param cap
     * @return
     * @throws Exception
     */
    public ByteBuffer getByKey(int cap) throws Exception {
        Map.Entry<Integer, List<ByteBuffer>> freeEntry = tailMap(cap, true).firstEntry();
        if (freeEntry == null || freeEntry.getValue().size() <= 0) {
            return null;
        }
        ByteBuffer ent = freeEntry.getValue().get(0);
        return ent;
    }
}
