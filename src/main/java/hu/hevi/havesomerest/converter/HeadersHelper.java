package hu.hevi.havesomerest.converter;

import org.springframework.http.HttpHeaders;

public interface HeadersHelper {
    HttpHeaders getHeaders(Object requestWrapper);
}
