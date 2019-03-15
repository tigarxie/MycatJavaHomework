package com.tigar.homework.lesson11;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author tigar
 * @date 2018/6/9.
 */
public class TelnetHandler implements Runnable {

    private SelectionKey selectionKey;
    private SocketChannel socketChannel;
    private Selector selector;
    private ByteBuffer readByteBuffer = ByteBuffer.allocateDirect(1024);
    private ByteBuffer writeByteBuffer = ByteBuffer.allocateDirect(1024);
    private int lastReadPostion = 0;
    private long lastWritePostion = 0;
    // 下载文件最大的发送缓冲空间
    final long MAX_SEND_BUFFER_LENGTH = 100;
    // 是否有文件正在下载
    boolean isDownloading = false;
    // 正在下的文件名
    String    downloadingFileName =null;

    public TelnetHandler(SocketChannel socketChannel, Selector selector) {
        this.socketChannel = socketChannel;
        this.selector = selector;
        try {
            socketChannel.configureBlocking(false);
            selectionKey = socketChannel.register(selector, 0);
            selectionKey.attach(this);
            selectionKey.interestOps(SelectionKey.OP_READ);
            writeByteBuffer.put(new String("welcome tigar house\r\n").getBytes());
            writeByteBuffer.flip();
            socketChannel.write(writeByteBuffer);
        } catch (Exception e) {
            selectionKey.cancel();
            try {
                socketChannel.close();
            } catch (IOException ex) {
            }
        }
    }

    @Override
    public void run() {
        if (selectionKey.isReadable()) {
            doRead();
        } else if (selectionKey.isWritable()) {
            doWrite();
        }
    }

    private void doRead() {
        String cmd = null;
        try {
            socketChannel.read(readByteBuffer);
            int endPostion = readByteBuffer.position();
            for (int i = lastReadPostion; i < endPostion; i++) {
                if (readByteBuffer.get(i) == 13) {
                    byte[] bytes = new byte[i - lastReadPostion];
                    readByteBuffer.position(lastReadPostion);
                    readByteBuffer.get(bytes);
                    lastReadPostion = i;
                    cmd = new String(bytes);
                    System.out.println("收到命令：" + cmd);
                    break;
                }
            }
            if (cmd != null) {
                // 准备写的数据
                processComand(cmd);
                selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_READ);
            }
            if (lastReadPostion > readByteBuffer.capacity() / 2) {
                // 回收
                readByteBuffer.limit(readByteBuffer.position());
                readByteBuffer.position(lastReadPostion);
                // 将 position 与 limit之间的数据复制到buffer的开始位置，
                // 复制后 position  = limit -position,limit = capacity
                readByteBuffer.compact();
                lastReadPostion = 0;
            }
        } catch (IOException e) {

        }
    }

    private void processComand(String cmd) {
        try {
            if (cmd.equals("download")) {
                doDownload();
            } else {
                // 暂时原路返回
                System.out.println("回写：" + cmd);
                writeByteBuffer.clear();
                writeByteBuffer.put(cmd.getBytes());
                doWrite();
            }
        } catch (Exception e) {
        }
    }

    private void doDownload() {
        try {
            downloadingFileName="c:/s3.txt";
            FileChannel fromChannel =
                    new RandomAccessFile(downloadingFileName, "r").getChannel();
            long fileLength = fromChannel.size();
            if (fileLength <= MAX_SEND_BUFFER_LENGTH) {
                fromChannel.transferTo(0, fileLength, socketChannel);
                selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_WRITE);
            } else {
                fromChannel.transferTo(0, MAX_SEND_BUFFER_LENGTH, socketChannel);
                selectionKey.interestOps(selectionKey.interestOps() & SelectionKey.OP_WRITE);
                // 标记上次写入的位置
                lastWritePostion = MAX_SEND_BUFFER_LENGTH;
                // 标记有文件在下载中
                isDownloading = true;
            }
            fromChannel.close();
        } catch (Exception e) {
        }
    }


    private void doWrite() {
        try {
            if(isDownloading){
                // 有文件
                FileChannel fromChannel =
                        new RandomAccessFile(downloadingFileName, "r").getChannel();
                long fileLength = fromChannel.size();
                if (lastWritePostion + MAX_SEND_BUFFER_LENGTH <= fileLength) {
                    // 还没下载完
                    fromChannel.transferTo(lastWritePostion, MAX_SEND_BUFFER_LENGTH, socketChannel);
                    selectionKey.interestOps(selectionKey.interestOps() & SelectionKey.OP_WRITE);
                    lastWritePostion += MAX_SEND_BUFFER_LENGTH;
                }
                else{
                    fromChannel.transferTo(lastWritePostion,
                            fileLength - lastWritePostion, socketChannel);
                    selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_WRITE);
                    isDownloading = true;
                }
                fromChannel.close();
            }
            else{
                int count = socketChannel.write(writeByteBuffer);
                System.out.println("写了数据数：" + count);
                if (writeByteBuffer.hasRemaining()) {
                    selectionKey.interestOps(selectionKey.interestOps() & SelectionKey.OP_WRITE);
                } else {
                    selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_WRITE);
                }
            }
        } catch (IOException e) {

        }
    }
}
