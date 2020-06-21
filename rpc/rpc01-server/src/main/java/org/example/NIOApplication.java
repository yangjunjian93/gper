package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOApplication {

    static Selector selector;


    public static void main(String[] args) {

        try {
            selector = Selector.open();

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(8080));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if(selectionKey.isAcceptable()){
                        handlerAccept(selectionKey);
                    }else if(selectionKey.isReadable()){
                        handlerRead(selectionKey);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handlerRead(SelectionKey selectionKey) {

        SocketChannel channel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        try {
            channel.read(byteBuffer);

            System.out.println(new String(byteBuffer.array()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 如果是连接事件，那么往外写
     * @param selectionKey
     */
    public static void handlerAccept(SelectionKey selectionKey){
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel channel = serverSocketChannel.accept();
            channel.configureBlocking(false);
            channel.write(ByteBuffer.wrap("这里是服务器发出的消息".getBytes()));
            channel.register(selector, SelectionKey.OP_READ );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
