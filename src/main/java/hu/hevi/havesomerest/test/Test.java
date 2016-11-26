package hu.hevi.havesomerest.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Test {

    private String name;
    private String statusCode;
    private List<String> endpointParts;
    private Map<String, String> pathVariablesByName;
    private HttpMethod method;
    private String description;
    private HttpHeaders requestHeaders;
    private Map<String, String> requestParams;
    private String request;
    private String response;
    private HttpHeaders responseHeaders;

    public boolean hasRequest() {
        return request != null;
    }

    public boolean hasResponse() {
        return response != null;
    }



}
