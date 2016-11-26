package hu.hevi.havesomerest.converter;

import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class XmlHeadersHelper implements HeadersHelper {

    private static final String HEADERS = "headers";

    private Logger log = LoggerFactory.getLogger(XmlHeadersHelper.class);

    @Override
    public HttpHeaders getHeaders(final Object requestWrapper) {
        Elements requestWrapperConverted = (Elements) requestWrapper;
        HttpHeaders httpHeaders = new HttpHeaders();
        Elements rawHeaders = (Elements) requestWrapperConverted.select("test > request > headers");
        try {
            rawHeaders.forEach(key -> {
                httpHeaders.add(key.toString(), (String) rawHeaders.select(key.toString()).toString());
            });
        } catch (NullPointerException e) {
            log.warn("Test file does not contain headers section");
        }
        return httpHeaders;
    }
}
