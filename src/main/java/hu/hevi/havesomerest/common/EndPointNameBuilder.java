package hu.hevi.havesomerest.common;

import hu.hevi.havesomerest.test.Test;
import org.springframework.stereotype.Component;

@Component
public class EndPointNameBuilder {

    public String build(Test test) {
        String endPoint = "";
        for (String part : test.getEndpointParts()) {
            endPoint = endPoint + "/" + part;
        }
        return endPoint;
    }
}
