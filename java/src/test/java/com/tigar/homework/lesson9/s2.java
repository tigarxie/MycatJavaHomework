package com.tigar.homework.lesson9;

import com.google.common.base.Strings;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.*;

/**
 * @author tigar
 * @date 2018/5/19.
 */
public class s2 {

    @Test
    public void t1() {
        // 线程池命名
        ThreadFactory mailThreadFactory = new ThreadFactoryBuilder().setNameFormat("mailThreadFactory").build();
        ExecutorService httpThreadPool =
                new ThreadPoolExecutor(1, 5, 0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingDeque<>(1024), mailThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(19002);
            while (true) {
                try {
                    Socket client = socket.accept();
                    httpThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String responseContent = RequestHandler(client.getInputStream());
                                System.out.println("返回内容：" + responseContent);
                                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                                bufferedWriter.write(responseContent);
                                bufferedWriter.flush();
                                client.close();
                            } catch (IOException e) {
                            }
                        }
                    });
                } catch (IOException e) {
                }
            }
        } catch (Exception e) {
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据请求返回结果
     *
     * @param inputStream
     * @return
     */
    public static String RequestHandler(InputStream inputStream) {
        String requestUrl = null;
        try {
            BufferedReader bufferedWriter = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedWriter.readLine();
            String[] block = line.split("\\s+");
            requestUrl = block[1];
            System.out.println("请求网址：" + requestUrl);
        } catch (IOException e) {
        }
        String msg = "";
        if (Strings.isNullOrEmpty(requestUrl)) {
            msg = "error";
        }
        // 检测帐号密码
        if (checkUserAndPass(requestUrl)) {
            msg = "Welcome my big hero aaaa";
        } else {
            msg = "Sorry,i dot not konw you";
        }
        // 处理不是本期重点，所以简单统一处理
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\r\n");
        sb.append(String.format("Content-Length:%d\r\n", msg.length()));
        sb.append("\r\n");
        sb.append(msg);
        return sb.toString();
    }

    /**
     * 检测帐号密码
     *
     * @param url
     * @return
     */
    public static boolean checkUserAndPass(String url) {
        // 正常应该分别获取不同的参数，但不是重点，先略过
        return url.indexOf("hellow.msp?user=aaaa&password=123456") >= 0;
    }


    @Test
    public void t2() {
        try {
            final AsynchronousServerSocketChannel socket = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(19002));
            socket.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                final ByteBuffer buffer = ByteBuffer.allocate(1024);

                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    try {
                        buffer.clear();
                        result.read(buffer);
                        System.out.println("请求内容:" + new String(buffer.array()));
                        buffer.flip();
                        result.write(buffer);
                    } catch (Exception e) {
                    } finally {
                        // 循环处理
                        socket.accept(null, this);
                        try {
                            result.close();
                        } catch (Exception e) {

                        }
                    }
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    // 暂时什么都不做
                }
            });
        } catch (Exception e) {
        }
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String RequestHandler() {
        // 处理不是本期重点，所以简单统一处理
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\r\n");
        sb.append("Content-Length:2s\r\n");
        sb.append("\r\n");
        sb.append("hi");
        return sb.toString();
    }

    @Test
    public void t3() {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(19002);
            while (true) {
                Socket client = socket.accept();
                InputStream inputStream = client.getInputStream();
                LineNumberReader bufferedReader = new LineNumberReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String requestContent;
                while ((requestContent = bufferedReader.readLine()) != null) {
                    sb.append(requestContent);
                }
                requestContent = sb.toString();
                System.out.println(requestContent);
                String responseContent = RequestHandler(requestContent);
                System.out.println(responseContent);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                bufferedWriter.write(responseContent);
                bufferedWriter.flush();
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String RequestHandler(String requestContent) {
        // 处理不是本期重点，所以简单统一处理
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\r\n");
        sb.append("Content-Length:2s\r\n");
        sb.append("\r\n");
        sb.append("hi");
        return sb.toString();
    }

}
