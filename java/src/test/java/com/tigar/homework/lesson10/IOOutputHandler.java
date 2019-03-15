package com.tigar.homework.lesson10;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tigar
 * @date 2018/5/26.
 */

public class IOOutputHandler {
    private IOOutputHandler() {
    }

    private static IOOutputHandler handler = null;

    public static IOOutputHandler getInstance() {
        if (handler == null) {
            synchronized (IOOutputHandler.class) {
                if (handler == null) {
                    handler = new IOOutputHandler();
                }
            }
        }
        return handler;
    }

    ExecutorService pool = Executors.newFixedThreadPool(5);

    public void Handle(Socket client, String content, s1.IOOutputListener ioOutputListener) {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("当期线程专做io写，线程ID：" + Thread.currentThread().getId());
                    System.out.println("返回体：" + content);
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                    bufferedWriter.write(content);
                    bufferedWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ioOutputListener.AfterGetOutStream(client);
            }
        });
    }
}