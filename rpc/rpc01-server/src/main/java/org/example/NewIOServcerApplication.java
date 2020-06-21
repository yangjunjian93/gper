package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServcerApplication {

    static Selector selector;

    public static void main(String[] args) throws IOException, InterruptedException {
        selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);// 设置为非阻塞
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8080));
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null){
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                socketChannel.read(byteBuffer);

                System.out.println(new String(byteBuffer.array()));

            } else {
                Thread.sleep(1000);
                System.out.println("连接未就绪");
            }

        }

    }

}
