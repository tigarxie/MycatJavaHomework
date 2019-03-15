package com.tigar.homework.lesson11;

import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author tigar
 * @date 2018/6/9.
 */
public class s2 {

    @Test
    public void t1() throws Exception {
        long startTime, endTime;

        FileChannel fromChannel = new RandomAccessFile("c:/s4.txt", "rw").getChannel();
        FileChannel targetChannel = new RandomAccessFile("c:/s4_copy.txt", "rw").getChannel();

        startTime = System.currentTimeMillis();
        fromChannel.transferTo(0, fromChannel.size(), targetChannel);
        endTime = System.currentTimeMillis();
        System.out.println("传输时间：" + (endTime - startTime) + "毫秒");
    }

    @Test
    public void t2() throws Exception {
        new Thread(new TelnetReactor(19000)).run();
    }
}
