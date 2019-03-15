package com.tigar.homework.lesson10;

import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.EventListener;
import java.util.Iterator;
import java.util.concurrent.*;

/**
 * @author tigar
 * @date 2018/5/26.
 */
public class s1 {

    @Test
    public void t1() throws Exception {
        IOHelper ioHelper = new IOHelper();
        // 简化监听事件
        InputStreamListener inputStreamListeners = ioHelper;
        OutputStreamListener outputStreamListener = ioHelper;

        ServerSocket socket = new ServerSocket(8300);
        while (true) {
            Socket client = socket.accept();
            String requestContent = inputStreamListeners.getInputStream(client.getInputStream());
            System.out.println("请求内容：" + requestContent);
            String responseContent = requestHandler(requestContent);
            outputStreamListener.setOutputStream(client.getOutputStream(), responseContent);
            client.close();
        }
    }

    private String requestHandler(String requestContent) {
        // 处理不是本期重点，所以简单统一处理
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\r\n");
        sb.append("Content-Length:2\r\n");
        sb.append("\r\n");
        sb.append("hi");
        return sb.toString();
    }

    /**
     * 监听当有需要读IO时候
     */
    public interface InputStreamListener extends EventListener {
        String getInputStream(InputStream stream);
    }

    /**
     * 监听当有需要写IO时候
     */
    public interface OutputStreamListener extends EventListener {
        void setOutputStream(OutputStream stream, String content);
    }


    public class IOHelper implements InputStreamListener, OutputStreamListener {
        ExecutorService inputPool = Executors.newFixedThreadPool(5);
        ExecutorService outPool = Executors.newFixedThreadPool(5);

        @Override
        public String getInputStream(InputStream stream) {
//            Future<String> res = inputPool.submit(new Callable<String>() {
//                @Override
//                public String call() throws Exception {
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String temp;
//                    while ((temp = bufferedReader.readLine()) != null) {
//                        stringBuilder.append(temp);
//                    }
//                    return stringBuilder.toString();
//                }
//            });
//            try {
//                return res.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
            return null;
        }

        @Override
        public void setOutputStream(OutputStream stream, String content) {
            outPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(stream));
                        bufferedWriter.write(content);
                        bufferedWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                boolean isFinish = outPool.awaitTermination(1, TimeUnit.SECONDS);
                while (!isFinish) {
                    outPool.awaitTermination(1, TimeUnit.SECONDS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void t2() throws Exception {
        ServerSocket socket = new ServerSocket(8300);
        while (true) {
            Socket client = socket.accept();
            IOInputHandler.getInstance().Handle(client, new IOInputListenerImpl() {
            });
        }
    }

    /**
     * IO读处理
     */
    public interface IOInputListener extends EventListener {
        void AfterGetInputStream(Socket client, String requestContent);
    }

    public class IOInputListenerImpl implements IOInputListener {
        @Override
        public void AfterGetInputStream(Socket client, String requestContent) {
            System.out.println("当期准备作输出，线程ID：" + Thread.currentThread().getId());
            IOOutputHandler.getInstance().Handle(client, requestHandler(requestContent), new IOOutputListenerImpl());
        }
    }


    /**
     * IO写处理
     */
    public interface IOOutputListener extends EventListener {
        void AfterGetOutStream(Socket client);
    }

    public class IOOutputListenerImpl implements IOOutputListener {
        @Override
        public void AfterGetOutStream(Socket client) {
            System.out.println("当期准备作结束握手，线程ID：" + Thread.currentThread().getId());
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void t3() throws Exception {
        ExecutorService readPool = Executors.newFixedThreadPool(2);
        ExecutorService writePool = Executors.newFixedThreadPool(2);

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(8121);
        serverSocketChannel.bind(address);
        System.out.println("监听开始，主机：" + address.getHostName() + " 端口：" + address.getPort());
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            int requestCount = selector.select();
            if (requestCount == 0) {
                continue;
            }
            System.out.println("未完成处理请求：" + requestCount);
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey selectionKey = iter.next();
                if (selectionKey.isAcceptable()) {
                    System.out.println("服务器启动");
                    SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    System.out.println("收到IO读事件");
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    readPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
                                int len = socketChannel.read(byteBuffer);
//                                if (len <= 0) {
//                                    socketChannel.close();
//                                }
                                System.out.println(String.format("客户端请求：\n%s", getString(byteBuffer)));
                                selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else if (selectionKey.isWritable()) {
                    System.out.println("收到IO写事件");
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    writePool.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("HTTP/1.1 200 OK\r\n");
                                stringBuilder.append("Content-Length:2\r\n");
                                stringBuilder.append("\r\n");
                                stringBuilder.append("hi");
                                byteBuffer.put(stringBuilder.toString().getBytes());
                                byteBuffer.flip();
                                socketChannel.write(byteBuffer);
                                System.out.println(String.format("服务端请求：\n%s", stringBuilder.toString()));
                                if (byteBuffer.hasRemaining()) {
                                    selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
                                } else {
                                    selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_WRITE);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                iter.remove();
            }
        }
    }

    /**
     * ByteBuffer 转换 String
     *
     * @param buffer
     * @return
     */
    public static String getString(ByteBuffer buffer) {
        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;
        try {
            charset = Charset.forName("UTF-8");
            decoder = charset.newDecoder();
            // charBuffer = decoder.decode(buffer);//用这个的话，只能输出来一次结果，第二次显示为空
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
