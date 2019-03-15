package com.tigar.homework.lesson2;

import com.google.common.base.Stopwatch;
import com.tigar.homework.common.Salary;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


/**
 * Created by Administrator on 2017-12-08.
 */
public class s6 {
    @Test
    public void TestMain() throws Exception {
        String path = "c:/";
        String charset = "utf-8";

        // 写文件
        bioWrite(path + "Subject6_bio.txt", charset);
        nioWrite(path + "Subject6_nio.txt", charset);

        // 读文件
        bioRead(path + "Subject6_nio.txt", charset);
        nioRead(path + "Subject6_nio.txt", charset);


    }


    // bio文件读取
    private  void bioRead(String path, String charset) throws Exception
    {
        Stopwatch watch = Stopwatch.createUnstarted();
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
        for (int i = 0; i < 1000 * 10000; i++) {
            // 逐行读取，并转换
            watch.start();
            lineNumberReader.readLine();
            // String lineStr = lineNumberReader.readLine();
            watch.stop();
            // Salary ent = new Salary(lineStr);
        }

        System.out.println("文件大小为：" + file.length() / 1024 / 1000 + "M");
        System.out.println("读文件时间：" + watch.toString() + "");
    }

    // bio文件写
    private void bioWrite(String path, String charset) throws Exception
    {
        Stopwatch watch = Stopwatch.createUnstarted();
        FileWriter fileWriter = null;
        File file = new File(path);
        try {
            fileWriter = new FileWriter(file);
            // 不加大JMT内存了，原内存是128，所以分段写入
            for (int j = 0; j < 10; j++)
            {
                StringBuffer outputBuf = new StringBuffer();
                for (int i = 0; i < 10000 * 100; i++) {
                    // 初始化，并写入
                    Salary ent = new Salary();
                    String outputStr = ent.toString() + "\r\n";
                    outputBuf.append(outputStr);
                }
                String outputString = outputBuf.toString();
                watch.start();
                fileWriter.write(outputString);
                watch.stop();
            }
        }
        catch (Exception ex)
        {
        }
        finally {
            if(fileWriter != null)
                fileWriter.close();
        }
        System.out.println("文件大小为：" + file.length() / 1024 / 1000 + "M");
        System.out.println("写文件时间：" + watch.toString() + "");
    }

    // nio文件写
    private void nioWrite(String path, String charset) throws Exception
    {
        Stopwatch watch = Stopwatch.createUnstarted();
        FileChannel fc = null;
        File file = new File(path);
        try {
            // 为了以可读可写的方式打开文件，这里使用RandomAccessFile来创建文件。
            fc = new RandomAccessFile(path, "rw").getChannel();
            //注意，文件通道的可读可写要建立在文件流本身可读写的基础之上
            MappedByteBuffer out;
            //写内容
            int index = 0;
            // 不加大JMT内存了，原内存是128，所以分段写入
            for (int j = 0; j < 10; j++)
            {
                StringBuilder outputBuf = new StringBuilder();
                for (int i = 0; i < 100 * 10000; i++) {
                    // 初始化，并写入
                    Salary ent = new Salary();
                    String outputStr = ent.toString() + "\r\n";
                    outputBuf.append(outputStr);
                }
                String outputString = outputBuf.toString();
                out = fc.map(FileChannel.MapMode.READ_WRITE, index, outputString.length());
                index += outputString.length();
                watch.start();
                out.put(outputString.getBytes());
                watch.stop();
            }
        }
        catch (Exception ex)
        {
        }
        finally {
            if(fc != null)
                fc.close();
        }
        System.out.println("文件大小为：" + file.length() / 1024 / 1000 + "M");
        System.out.println("写文件时间：" + watch.toString() + "");
    }

    // nio文件读取，整块读取
    private  void nioRead(String path, String charset) throws Exception
    {
        Stopwatch watch = Stopwatch.createUnstarted();
        File file = new File(path);
        try {
            watch.start();
            RandomAccessFile inRandomAccessFile= new RandomAccessFile(path, "r");
            FileChannel inFileChannel= inRandomAccessFile.getChannel();
            MappedByteBuffer inMappedByteBuffer=
                    inFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inFileChannel.size());
            inRandomAccessFile.close();
            inFileChannel.close();
            byte[] tempByte = new byte[50];
            int index = -1;
            byte bt = inMappedByteBuffer.get();
            while(bt != -1) {
                //判断是否出现了换行符，注意这要区分LF-\n,CR-\r,CRLF-\r\n,这里判断\n
                if(bt == 10)
                {
                    String temp = new String(tempByte, 0, index);
//                    System.out.println(temp);
//                    System.out.println("------------------------");
                    index = -1;
                }
                else
                {
                    index++;
                    tempByte[index] = bt;
                }
                bt = inMappedByteBuffer.get();
            }
            watch.stop();
        }
        catch (Exception ex)
        {
        }
        finally {
        }
        System.out.println("文件大小为：" + file.length() / 1024 / 1000 + "M");
        System.out.println("读文件时间：" + watch.toString() + "");
    }

    // nio文件读取，逐个字节读取
    private  void nioReadByOne(String path, String charset) throws Exception
    {
        Stopwatch watch = Stopwatch.createUnstarted();
        FileChannel fc = null;
        File file = new File(path);
        try {
            fc = new RandomAccessFile(path, "r").getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1);
            byte[] tempByte = new byte[50];
            int index = -1;
            watch.start();
            while(fc.read(byteBuffer) != -1) {
                byteBuffer.flip();
                byte bt = byteBuffer.get();
                //判断是否出现了换行符，注意这要区分LF-\n,CR-\r,CRLF-\r\n,这里判断\n
                if(bt == 10)
                {
                    String temp = new String(tempByte, 0, index);
//                    System.out.println(temp);
//                    System.out.println("------------------------");
                    index = -1;
                }
                else
                {
                    index++;
                    tempByte[index] = bt;
                }
                byteBuffer.clear();
            }
            watch.stop();
        }
        catch (Exception ex)
        {

        }
        finally {
            if(fc != null)
                fc.close();
        }

        System.out.println("文件大小为：" + file.length() / 1024 / 1000 + "M");
        System.out.println("读文件时间：" + watch.toString() + "");
    }

    // nio文件读取，会不断寻址
    private  void nioReadHasSeek(String path, String charset) throws Exception
    {
        Stopwatch watch = Stopwatch.createUnstarted();
        FileChannel fc = null;
        File file = new File(path);
        try {
            fc = new RandomAccessFile(path, "r").getChannel();
            // 计算最大长度
            Salary ent = new Salary();
            ent.setBaseSalary(10000 * 100); // 最大值100W
            ent.setBonus(10000 * 10);   // 最大值10万
            int maxLength = (ent.toString() + "\r\n").length();
            ByteBuffer byteBuffer = ByteBuffer.allocate(maxLength);
            long index = 0;
            watch.start();
            int len = fc.read(byteBuffer, index);
            watch.stop();
            while(len != -1) {
                watch.start();
                byte[] bs = new byte[byteBuffer.position()];
                byteBuffer.flip();
                byteBuffer.get(bs);
                byteBuffer.clear();
                int endIndex = 0;
                //判断是否出现了换行符，注意这要区分LF-\n,CR-\r,CRLF-\r\n,这里判断\n
                for (int i = 0; i < bs.length; i++) {
                    if (bs[i] == 10) {
                        endIndex = i;
                    }
                }
//                System.out.println(new String(bs, 0, endIndex));
//                System.out.println("------------------------");
                if(endIndex > 0)
                {
                    index += endIndex + 1;
                }
                else
                {
                    throw new Exception("异常");
                }
                len = fc.read(byteBuffer, index);
                watch.stop();
            }
        }
        catch (Exception ex)
        {

        }
        finally {
            if(fc != null)
                fc.close();
        }
        System.out.println("文件大小为：" + file.length() / 1024 / 1000 + "M");
        System.out.println("读文件时间：" + watch.toString() + "");
    }
}
