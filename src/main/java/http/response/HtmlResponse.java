package http.response;


import http.Server;
import http.ServerThread;

public class HtmlResponse extends Response{
    private final String html;
    private final String cookie;

    public HtmlResponse(String html, String cookie) {
        this.html = html;
        this.cookie = cookie;
    }

    @Override
    public String getResponseString() {
        String response = "";
        if(Server.quotes.containsKey(cookie))
            response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n" + "Set-Cookie:" + cookie + "\r\n\r\n";
        response += html;

        return response;
    }
}
