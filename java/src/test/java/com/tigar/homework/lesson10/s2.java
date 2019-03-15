package com.tigar.homework.lesson10;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.stream.IntStream;

import com.tigar.homework.common.RandomHepler;

/**
 * @author tigar
 * @date 2018/5/26.
 */
public class s2 {

    @Test
    public void t1() throws Exception {
        String filePath = "d:/niotest.txt";
        String fileCopyPath = "d:/niotest_copy.txt";
        String fileCopyPath1 = "d:/niotest_copy1.txt";
        long startTime, endTime;
        // 生成大文件
        FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        startTime = System.currentTimeMillis();
        IntStream.range(0, 100000000).forEach(f -> {
            try {
                bufferedWriter.write(RandomHepler.getRandomName());
            } catch (IOException e) {
            }
        });
        bufferedWriter.flush();
        endTime = System.currentTimeMillis();
        System.out.println("生成大文件：" + (endTime - startTime) + "毫秒");

        FileInputStream bioFis = new FileInputStream(filePath);
        FileOutputStream bioFos = new FileOutputStream(fileCopyPath);
        byte[] bioB = new byte[1024];
        startTime = System.currentTimeMillis();
        while (true) {
            int len = bioFis.read(bioB);
            if (len <= 0) {
                break;
            }
            bioFos.write(bioB, 0, len);
        }
        endTime = System.currentTimeMillis();
        System.out.println("bio复制大文件：" + (endTime - startTime) + "毫秒");

        FileInputStream nioFis = new FileInputStream(filePath);
        FileOutputStream nioFos = new FileOutputStream(fileCopyPath1);
        FileChannel nioFic = nioFis.getChannel();
        FileChannel nioFoc = nioFos.getChannel();
        ByteBuffer nioBB = ByteBuffer.allocateDirect(1024);
        //ByteBuffer nioBB = ByteBuffer.allocate(1024);
        startTime = System.currentTimeMillis();
        while (true) {
            // 清空缓存区
            nioBB.clear();
            int len = nioFic.read(nioBB);
            if (len <= 0) {
                break;
            }
            // 指针移到缓冲区开头
            nioBB.flip();
            nioFoc.write(nioBB);
        }
        endTime = System.currentTimeMillis();
        System.out.println("nio复制大文件：" + (endTime - startTime) + "毫秒");
    }

    @Test
    public void t2() throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        System.out.println("position："+byteBuffer.position());
        byteBuffer.putInt(12);
        System.out.println("position："+byteBuffer.position());
        byteBuffer.clear();
        System.out.println("position："+byteBuffer.position());
        byteBuffer.putInt(1332);
        byteBuffer.flip();
        System.out.println("刚存入的值："+byteBuffer.getInt());
        try {
            System.out.println("刚存入的值，看会不会报错或变："+byteBuffer.getInt());
        } catch (Exception e) {
            System.out.println("报错了");
        }
        byteBuffer.clear();
        byteBuffer.putInt(331332);
        byteBuffer.mark();
        byteBuffer.putInt(1332);
        byteBuffer.putInt(444);
        System.out.println("刚存入的值："+byteBuffer.getInt());
        byteBuffer.reset();
        System.out.println("刚存入的值："+byteBuffer.getInt());
        byteBuffer.flip();
        System.out.println("刚存入的值："+byteBuffer.getInt());
    }
}
