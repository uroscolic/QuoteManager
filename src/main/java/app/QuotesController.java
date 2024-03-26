package app;

import http.Request;
import http.Server;
import http.response.HtmlResponse;
import http.response.RedirectResponse;
import http.response.Response;
import model.Quote;

public class QuotesController extends Controller{

    public QuotesController(Request request) {
            super(request);
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
                "<label>Quotes:</label><br>");
        for(Quote quote : Server.quotes){
            htmlBody.append("<p>").append(quote).append("</p>");
        }

        String content = "<html><head><title>Odgovor servera</title></head>\n";
        content += "<body>" + htmlBody + "</body></html>";

        return new HtmlResponse(content);
    }

    @Override
    public Response doPost() {
        return new RedirectResponse("/quotes");
    }
}
