package hu.hevi.havesomerest.converter;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HeadersHelper {

    public static final String HEADERS = "headers";

    public HttpHeaders getHeaders(JSONObject requestWrapper) {
        HttpHeaders httpHeaders = new HttpHeaders();
        JSONObject rawHeaders = (JSONObject) requestWrapper.get(HEADERS);
        try {
            rawHeaders.keySet().forEach(key -> {
                httpHeaders.add(key, (String) rawHeaders.get(key));
            });
        } catch (NullPointerException e) {
            log.warn("Test file does not contain headers section");
        }
        return httpHeaders;
    }
}
