package hu.hevi.havesomerest.common;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EndPointNameBuilder {

    public String build(List<String> endpointParts) {
        String endPoint = "";
        for (String part : endpointParts) {
            endPoint = endPoint + "/" + part;
        }
        return endPoint;
    }
}
