package http;

import model.Quote;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerHelper {
    public static final int TCP_PORT = 8081;
    public static List<Quote> quotes = new ArrayList<>();

    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(TCP_PORT);
            addQuotes();
            while (true) {
                Socket sock = ss.accept();
                new Thread(new ServerHelperThread(sock)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void addQuotes()
    {
        quotes.add(new Quote("Nikola Tesla", "The present is theirs; the future, for which I really worked, is mine."));
        quotes.add(new Quote("Albert Einstein", "Imagination is more important than knowledge."));
        quotes.add(new Quote("Isaac Newton", "If I have seen further it is by standing on the shoulders of Giants."));
        quotes.add(new Quote("Marie Curie", "Nothing in life is to be feared, it is only to be understood."));
        quotes.add(new Quote("Galileo Galilei", "E pur si muove."));
        quotes.add(new Quote("Charles Darwin", "It is not the strongest of the species that survive, nor the most intelligent, but the one most responsive to change."));
    }

}
