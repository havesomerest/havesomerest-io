package hu.hevi.havesomerest.converter;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class HeadersHelper {

    private static final String HEADERS = "headers";

    private Logger log = LoggerFactory.getLogger(HeadersHelper.class);

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

    void setLog(Logger log) {
        this.log = log;
    }
}
