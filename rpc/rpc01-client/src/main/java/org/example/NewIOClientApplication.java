package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOClientApplication {

    public static void main(String[] args) throws IOException, InterruptedException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("这是客户端111来啦！\n".getBytes());
        Thread.sleep(10000);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);

    }

}
