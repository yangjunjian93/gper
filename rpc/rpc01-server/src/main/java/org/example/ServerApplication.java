package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO模型，同步阻塞IO，在socket服务启动后监听8080端口，没有接收到请求时会阻塞等待，接收到请求后结束
 * 一次只能处理一个请求，如果有两个的话处理不了另外一个
 */
public class ServerApplication {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            // 加循环是为了持续监听，否则收到一个之后就不再监听了
            while (true) {
                Socket socket = serverSocket.accept();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(bufferedReader.readLine());

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write(System.currentTimeMillis() + "服务端发送了消息回来！\n");
                bufferedWriter.flush();
                bufferedReader.close();
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
