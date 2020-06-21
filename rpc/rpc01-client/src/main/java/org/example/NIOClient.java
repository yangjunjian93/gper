package org.example;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClient {

    static Selector selector;


    public static void main(String[] args) {
        try {
            selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            while (true){
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if(selectionKey.isConnectable()){
                        handlerConnection(selectionKey);
                    } else if (selectionKey.isReadable()){
                        handlerRead(selectionKey);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void handlerRead(SelectionKey selectionKey) throws Exception{

        SocketChannel socketChannel=(SocketChannel)selectionKey.channel();
        //TODO
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer);
        System.out.println("client receive msg:"+new String(byteBuffer.array()));

    }

    private static void handlerConnection(SelectionKey selectionKey) throws Exception{

        SocketChannel socketChannel=(SocketChannel)selectionKey.channel();
        if(socketChannel.isConnectionPending()){
            //
            socketChannel.finishConnect();
        }
        socketChannel.configureBlocking(false);
        socketChannel.write(ByteBuffer.wrap("Hello Server,I'm NIo Client".getBytes()));
        socketChannel.register(selector,SelectionKey.OP_READ); //

    }

}
