package org.example;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 注意，socket只会在new的时候建立一次连接！后续不再建立对应连接，所以不能多线程复用！
 */
public class ClientApplication2 {

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", 8080);
        while (true){

            executorService.execute(new SocketThread(socket));
            Thread.sleep(1000);

        }


    }

}
