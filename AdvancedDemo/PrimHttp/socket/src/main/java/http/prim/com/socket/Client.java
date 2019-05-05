package http.prim.com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author prim
 * @version 1.0.0
 * @desc Socket 客户端
 * @time 2018/8/6 - 上午10:20
 */
public class Client {
    public static void main(String[] args) {
        try {
            //客户端请求与本机在10009端口 建立连接
            Socket client = new Socket("127.0.0.1", 10009);
            client.setSoTimeout(10000);

            //获取键盘输入
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            //获取socket的输出流，用来发送数据到服务端
            PrintStream printStream = new PrintStream(client.getOutputStream());

            //获取socket的输入流，用来接受服务端发送的数据
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            boolean flag = true;

            while (flag) {
                System.out.println("客户端向服务端发送信息:");
                String in = input.readLine();
                //发送数据到服务端
                printStream.println(in);
                if (in.equals("bye")) {
                    flag = false;
                } else {
                    //从服务端接受数据 这里会有个时间限制 超过这个限制会报异常
                    String readLine = bufferedReader.readLine();
                    System.out.println(readLine);
                }
            }

            input.close();
            if (client != null){
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();//关闭socket 其关联的输入输出流也会关闭
        }
    }
}
