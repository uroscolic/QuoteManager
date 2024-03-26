package app;

import http.Request;
import http.Server;
import http.ServerHelper;
import http.response.HtmlResponse;
import http.response.Response;

import java.util.Random;

public class QODController extends Controller{

    private boolean firstTime = true;
    private static int index = 0;
    public QODController(Request request) {
        super(request);
    }
    @Override
    public Response doGet() {
        if(firstTime) {
            int size = ServerHelper.quotes.size();
            Random random = new Random();
            index = random.nextInt(size);
            firstTime = false;
        }
        String quote = ServerHelper.quotes.get(index).toString();


        return new HtmlResponse(quote);
    }

    @Override
    public Response doPost() {
        return null;
    }
}
