package hu.hevi.havesomerest.converter;

import org.springframework.http.HttpHeaders;

/**
 * Created by hevi on 2016. 11. 26..
 */
public interface HeadersHelper {
    HttpHeaders getHeaders(Object requestWrapper);
}
