package http;



import model.Quote;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {

    public static final int TCP_PORT = 8080;
    public static Map<String, List<Quote>> quotes = new HashMap<>();

    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(TCP_PORT);
            while (true) {
                Socket sock = ss.accept();
                new Thread(new ServerThread(sock)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
