package com.tigar.homework.lesson11;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/**
 * TelnetReactor
 *
 * @author tigar
 * @date 2018/6/9.
 */
public class TelnetReactor implements Runnable {

    private int port;

    public TelnetReactor(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            SocketAddress socketAddress = new InetSocketAddress(port);
            serverSocketChannel.bind(socketAddress);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                Set<SelectionKey> selectedKeys = null;
                try {
                    if (selector.select() <= 0) {
                        continue;
                    }
                    selectedKeys = selector.selectedKeys();
                } catch (IOException e) {
                    continue;
                }
                for (SelectionKey selectedKey : selectedKeys) {
                    if (selectedKey.isAcceptable()) {
                       new TelnetAcceptor(serverSocketChannel.accept(), selector).run();
                    } else {
                        ((TelnetHandler) selectedKey.attachment()).run();
                    }
                }
                selectedKeys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
