package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NewIOClientApplication2 {

    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("这是客户端222来啦！\n".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);

    }

}
