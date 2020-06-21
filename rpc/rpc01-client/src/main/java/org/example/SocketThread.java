package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketThread implements Runnable {

    private Socket socket;

    public SocketThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(Thread.currentThread().getName() + "客户端发送了消息过来！\n");
            bufferedWriter.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(bufferedReader.readLine());

            bufferedReader.close();
            bufferedWriter.close();
        } catch (Exception e) {

        }

    }


}
