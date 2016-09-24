package hu.hevi.havesomerest.converter;

import lombok.Value;
import org.springframework.http.HttpMethod;

@Value
public class Filename {

    private static final String GET = "get";
    private static final String POST = "post";
    private static final String PUT = "put";
    private static final String PATCH = "patch";
    private static final String DELETE = "delete";

    private String name;

    public HttpMethod getMethod() {
        HttpMethod httpMethod = HttpMethod.GET;
        if (name.toLowerCase().startsWith(GET)) {
            httpMethod = HttpMethod.GET;
        }
        if (name.toLowerCase().startsWith(POST)) {
            httpMethod = HttpMethod.POST;
        }
        if (name.toLowerCase().startsWith(PUT)) {
            httpMethod = HttpMethod.PUT;
        }
        if (name.toLowerCase().startsWith(PATCH)) {
            httpMethod = HttpMethod.PATCH;
        }
        if (name.toLowerCase().startsWith(DELETE)) {
            httpMethod = HttpMethod.DELETE;
        }
        return httpMethod;
    }

    public String getStatusCode() {
        String statusCode = "";
        if (name.toLowerCase().startsWith(GET)) {
            statusCode = name.substring(3, 6);
        }
        if (name.toLowerCase().startsWith(POST)) {
            statusCode = name.substring(4, 7);
        }
        if (name.toLowerCase().startsWith(PUT)) {
            statusCode = name.substring(3, 6);
        }
        if (name.toLowerCase().startsWith(PATCH)) {
            statusCode = name.substring(5, 8);
        }
        if (name.toLowerCase().startsWith(DELETE)) {
            statusCode = name.substring(6, 9);
        }
        return statusCode;
    }
}
