package hu.hevi.havesomerest.common;

import hu.hevi.havesomerest.test.Test;
import org.springframework.stereotype.Component;

@Component
public class EndPointNameBuilder {

    public String build(Test test) {
        String endPoint = "";
        for (String part : test.getEndpointParts()) {
            if (part.startsWith("_") && part.endsWith("_")) {
                endPoint = endPoint + "/" + test.getPathVariablesByName().get(part);
            } else {
                endPoint = endPoint + part;
            }
        }
        return endPoint;
    }
}
