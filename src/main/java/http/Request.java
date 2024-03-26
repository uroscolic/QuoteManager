package http;

import lombok.Getter;

@Getter
public class Request {

    private final HttpMethod httpMethod;

    private final String path;

    public Request(HttpMethod httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }

}
