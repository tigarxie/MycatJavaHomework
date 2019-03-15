package com.tigar.homework.lesson11;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author tigar
 * @date 2018/6/9.
 */
public class TelnetAcceptor implements Runnable {

    private SocketChannel socketChannel;
    private Selector selector;

    public TelnetAcceptor(SocketChannel socketChannel, Selector selector) {
        this.socketChannel = socketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        new TelnetHandler(socketChannel, selector);
    }
}
