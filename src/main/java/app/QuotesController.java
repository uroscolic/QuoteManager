package app;

import http.Request;
import http.Server;
import http.response.HtmlResponse;
import http.response.RedirectResponse;
import http.response.Response;
import model.Quote;

import java.util.List;
import java.util.Map;

public class QuotesController extends Controller{

    String cookie;
    public QuotesController(Request request, String cookie) {
            super(request);
            this.cookie = cookie;
        }

    @Override
    public Response doGet() {
        StringBuilder htmlBody = new StringBuilder("" +
                "<form method=\"POST\" action=\"/save-quote\">" +
                "<label>Author: </label><input name=\"author\" type=\"author\"><br><br>" +
                "<label>Quote: </label><input name=\"quote\" type=\"quote\"><br><br>" +
                "<button>Save quote</button>" +
                "</form>" +
                "<br><br>" +
                "<label>Quotes:</label><br><br>");
        for(Quote quote : Server.quotes.get(cookie)){
            htmlBody.append(quote.getAuthor()).append(": ").append(quote.getQuote());
            htmlBody.append("<br>");
        }

        String content = "<html><head><title>Odgovor servera</title></head>\n";
        content += "<body>" + htmlBody + "</body></html>";

        return new HtmlResponse(content, cookie);
    }

    @Override
    public Response doPost() {
        return new RedirectResponse("/quotes");
    }
}
