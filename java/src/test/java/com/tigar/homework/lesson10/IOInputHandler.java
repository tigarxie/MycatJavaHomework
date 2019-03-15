package com.tigar.homework.lesson10;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tigar
 * @date 2018/5/26.
 */
public class IOInputHandler {
    private IOInputHandler() {
    }

    private static IOInputHandler handler = null;

    public static IOInputHandler getInstance() {
        if (handler == null) {
            synchronized (IOInputHandler.class) {
                if (handler == null) {
                    handler = new IOInputHandler();
                }
            }
        }
        return handler;
    }

    ExecutorService pool = Executors.newFixedThreadPool(5);

    public void Handle(Socket client, s1.IOInputListener ioInputListener) {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    LineNumberReader bufferedReader = new LineNumberReader(new InputStreamReader(client.getInputStream()));
                    System.out.println("当期线程专做io读，线程ID：" + Thread.currentThread().getId());
                    String temp;
                    while ((temp = bufferedReader.readLine()) != null) {
                        stringBuilder.append(temp + "\n");
                    }
                    System.out.println("请求内容：" + stringBuilder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ioInputListener.AfterGetInputStream(client, stringBuilder.toString());
            }
        });
    }
}