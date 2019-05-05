package http.prim.com.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author prim
 * @version 1.0.0
 * @desc Socket 服务端
 * @time 2018/8/6 - 上午10:21
 */
public class Server {
    public static void main(String[] args) {
        try {

            ServerSocket serverSocket = new ServerSocket(10009);
            Socket client = null;
            boolean flag = true;
            while (flag) {
                //等待客户端连接
                client = serverSocket.accept();
                System.out.println("客户端与服务端连接成功");
                //为每个客户端连接开启一个线程
                new Thread(new ServerThread(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
