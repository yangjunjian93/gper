package org.example;

import java.io.*;
import java.net.Socket;

public class ClientApplication {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("127.0.0.1", 8080);

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(System.currentTimeMillis() + "客户端1发送了消息过来！\n");
            bufferedWriter.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(bufferedReader.readLine());

            bufferedReader.close();
            bufferedWriter.close();



        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
