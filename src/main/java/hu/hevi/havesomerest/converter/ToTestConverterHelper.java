package hu.hevi.havesomerest.converter;

import hu.hevi.havesomerest.io.TestDirectory;
import hu.hevi.havesomerest.test.Test;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ToTestConverterHelper {

    private Logger log = LoggerFactory.getLogger(ToTestConverterHelper.class);

    private static final String HEADERS = "headers";
    private static final String BODY = "body";
    private static final String DESCRIPTION = "description";
    private static final String PARAMETERS = "parameters";
    private static final String RESPONSE = "response";
    private static final String REQUEST = "request";

    @Autowired
    private HeadersHelper headersHelper;

    public Map<Test, JSONObject> getTests(List<TestDirectory> testDirectories) {
        Map<Test, JSONObject> testByFileContent = new HashMap<>();
        testDirectories.stream()
                       .map(TestDirectory::getTestFiles)
                       .forEach(testCase -> {
                           testCase.forEach(testFile -> {
                               try {
                                   String fileContent = new String(Files.readAllBytes(testFile.getPath()));
                                   if (testFile.isJson()) {
                                       JSONObject convertedObject = new JSONObject(fileContent);

                                       if (testFile.isTestFile()) {
                                           Test t = getTest(convertedObject);
                                           Filename filename = testFile.getFileName();
                                           t.setName(filename.getName());

                                           String[] splittedPath = testFile.getPath().toString().split("/");
                                           List<String> endpoints = getEndpoint(splittedPath);
                                           t.setEndpointParts(endpoints);

                                           List<String> pathVariablesInFileName = filename.getPathVariables();
                                           log.debug("pathVariables in filename: " + pathVariablesInFileName.toString());

                                           if (pathVariablesInFileName.size() > 0) {
                                               List<String> pathVariablesInEndpoint = endpoints.stream().filter(endpoint -> {
                                                   return endpoint.startsWith("_") && endpoint.endsWith("_");
                                               }).collect(Collectors.toList());

                                               for (int i = 0; i < pathVariablesInFileName.size(); i++) {
                                                   try {
                                                       t.getPathVariablesByName().put(pathVariablesInEndpoint.get(i), pathVariablesInFileName.get(i));
                                                   } catch (IndexOutOfBoundsException e) {
                                                       log.warn("No Pathvariable in: " + t.toString());
                                                   }
                                               }
                                           }

                                           String statusCode = filename.getStatusCode();
                                           HttpMethod httpMethod = filename.getMethod();

                                           t.setStatusCode(statusCode);
                                           t.setMethod(httpMethod);

                                           testByFileContent.put(t, convertedObject);
                                       }
                                   }
                               } catch (Exception e) {
                                   e.printStackTrace();
                               }
                           });
                       });
        return testByFileContent;
    }

    private Test getTest(JSONObject jsonObject) {
        Test.TestBuilder testBuilder = Test.builder();
        if (jsonObject.has(REQUEST)) {
            JSONObject requestWrapper = (JSONObject) jsonObject.get(REQUEST);
            if (requestWrapper.has(BODY)) {
                JSONObject body = (JSONObject) requestWrapper.get(BODY);
                testBuilder.request(body);
            }

            HttpHeaders httpHeaders = headersHelper.getHeaders(requestWrapper);
            testBuilder.requestHeaders(httpHeaders);

            Map<String, String> parameters = getParameters(requestWrapper);
            testBuilder.requestParams(parameters);

        }
        if (jsonObject.has(RESPONSE)) {
            JSONObject response = (JSONObject) jsonObject.get(RESPONSE);
            JSONObject headers = (JSONObject) response.get(HEADERS);
            if (response.has(BODY)) {
                testBuilder.response((JSONObject) response.get(BODY));
            }

            HttpHeaders httpHeaders = headersHelper.getHeaders(response);
            testBuilder.responseHeaders(httpHeaders);
        }
        if (jsonObject.has(DESCRIPTION)) {

            testBuilder.description(((String) jsonObject.get(DESCRIPTION).toString())).build();
        }
        return testBuilder.pathVariablesByName(new HashMap<>()).build();
    }

    private Map<String, String> getParameters(JSONObject requestWrapper) {
        Map<String, String> parameters = new HashMap<>();
        JSONObject rawParameters = (JSONObject) requestWrapper.get(PARAMETERS);
        rawParameters.keySet().forEach(key -> {
            parameters.put(key, (String) rawParameters.get(key));
        });
        return parameters;
    }

    private List<String> getEndpoint(String[] splittedPath) {
        List<String> endPointParts = new LinkedList<>();
        boolean found = false;
        for (int i = 0; i < splittedPath.length; i++) {
            if (!found && "test".equals(splittedPath[i]) && "rest".equals(splittedPath[i + 1])) {
                found = true;
                i = i + 1;
            } else if (found && i < splittedPath.length - 1) {
                endPointParts.add(splittedPath[i]);
            }
        }

        String joinedEndpointParts = endPointParts.stream()
                                                  .collect(Collectors.joining("/"));
        return endPointParts;
    }
}
