package http;

import app.RequestHandler;
import com.google.gson.Gson;
import http.response.Response;
import model.Quote;

import java.io.*;
import java.net.Socket;

public class ServerHelperThread implements Runnable{

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private static boolean first = true;

    public ServerHelperThread(Socket client) {
        this.client = client;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

        try {
            String message = in.readLine();
            if(first) {
                if (message.equals("GET /qod")) {
                    Request request = new Request(HttpMethod.GET, "/qod");
                    RequestHandler requestHandler = new RequestHandler();
                    Response response = requestHandler.handle(request);
                    String qod = response.getResponseString();
                    qod = qod.substring(qod.indexOf("\r\n\r\n")+4);
                    Gson gson = new Gson();
                    Quote qodQuote = new Quote(qod.substring(0, qod.indexOf(":")), qod.substring(qod.indexOf("\"")+1, qod.length()-1));
                    String qodJSON = gson.toJson(qodQuote);
                    out.println(qodJSON);
                    first = false;
                } else
                    out.println("Invalid request");
            }
            in.close();
            out.close();
            client.close();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
