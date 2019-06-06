package http.prim.com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author prim
 * @version 1.0.0
 * @desc 服务端的线程
 * @time 2018/8/6 - 上午10:21
 */
public class ServerThread implements Runnable {
    Socket client = null;

    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            //获取socket的输出流，用来向客户端发送数据
            PrintStream printStream = new PrintStream(client.getOutputStream());
            //获取socket的输入流，用来接收客户端发送的数据
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            boolean flag = true;

            while (flag) {
                //接收客户端发送过来的数据
                String readLine = bufferedReader.readLine();
                if (readLine == null || "".equals(readLine)) {
                    flag = false;
                } else {
                    if ("bye".equals(readLine)) {
                        flag = false;
                    } else {
                        printStream.println("服务端响应客户端:" + readLine);
                    }
                }
            }

            printStream.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
