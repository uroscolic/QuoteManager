package http;

import app.RequestHandler;
import com.google.gson.Gson;
import http.response.Response;
import model.Quote;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerThread implements Runnable{

    private Socket client;
    private Socket helper;
    private BufferedReader in;
    private BufferedReader helperIn;
    private PrintWriter out;
    private static boolean first = true;
    private static String qod;

    public ServerThread(Socket client) {
        this.client = client;
        try {
            helper = new Socket("localhost", 8081);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
            helperIn = new BufferedReader(new InputStreamReader(helper.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void run() {
        try {
            String requestLine = in.readLine();
            StringTokenizer stringTokenizer = new StringTokenizer(requestLine);

            String method = stringTokenizer.nextToken();
            String path = stringTokenizer.nextToken();

            System.out.println("\nHTTP ZAHTEV KLIJENTA:\n");
            do {
                System.out.println(requestLine);
                requestLine = in.readLine();
            } while (!requestLine.trim().equals(""));

            if (method.equals(HttpMethod.POST.toString())) {

                char[] buffer = new char[400];
                in.read(buffer);
                String s = new String(buffer);
                //author=aa&quote=dddd
                String[] parts = s.split("&");
                String author = parts[0].split("=")[1].replace("+", " ");
                String quote = parts[1].split("=")[1].replace("+", " ");
                Server.quotes.add(new Quote(author, quote));

            }

            RequestHandler requestHandler = new RequestHandler();
            if(first)
                try (BufferedReader helperReader = new BufferedReader(new InputStreamReader(helper.getInputStream()));
                     PrintWriter helperWriter = new PrintWriter(new OutputStreamWriter(helper.getOutputStream()), true)) {

                    helperWriter.println("GET /qod");
                    String qodResponseJSON = helperReader.readLine();
                    Gson gson = new Gson();
                    qod = gson.fromJson(qodResponseJSON, Quote.class).toString();
                    first = false;
                }

            Request request = new Request(HttpMethod.valueOf(method), path);

            Response response = requestHandler.handle(request);

            System.out.println("\nHTTP odgovor:\n");
            System.out.println(response.getResponseString());

            String finalResponse = response.getResponseString();
            if(!qod.equals("Invalid request"))
                finalResponse = finalResponse.replace("<br><br><label>Quotes:",
                        "<br><label><b><i>Quote " +
                        "of the day: </b></label><br><br>" + qod + "</i><br><br><label><b>Quotes:</b>");
            out.println(finalResponse);
            in.close();
            out.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                helper.close();
                helperIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
