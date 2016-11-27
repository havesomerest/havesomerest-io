package hu.hevi.havesomerest.converter;

import hu.hevi.havesomerest.io.FileType;
import hu.hevi.havesomerest.io.TestDirectory;
import hu.hevi.havesomerest.test.Test;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("jsonHeadersHelper")
    private HeadersHelper jsonHeadersHelper;
    @Autowired
    @Qualifier("xmlHeadersHelper")
    private HeadersHelper xmlHeadersHelper;

    public Map<Test, String> getTestsByFileContent(List<TestDirectory> testDirectories) {
        Map<Test, String> testByFileContent = new HashMap<>();
        testDirectories.stream()
                       .map(TestDirectory::getTestFiles)
                       .forEach(testCase -> {
                           testCase.forEach(testFile -> {
                               try {
                                   String fileContent = new String(Files.readAllBytes(testFile.getPath()));
                                   FileType fileType = testFile.getFileType();
                                   Test t = null;
                                   if (fileType.equals(FileType.JSON)) {
                                       JSONObject convertedObject = new JSONObject(fileContent);
                                       t = fromJsonObject(convertedObject);
                                   } else if (fileType.equals(FileType.XML)) {
                                       Document doc = Jsoup.parse(fileContent);
                                       t = fromXmlObject(doc);
                                   }


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

                                   testByFileContent.put(t, fileContent);

                               } catch (Exception e) {
                                   e.printStackTrace();
                               }
                           });
                       });
        return testByFileContent;
    }

    private Test fromXmlObject(Document doc) {

        Test.TestBuilder testBuilder = Test.builder();

        if (doc.select(REQUEST) != null) {
            Elements requestWrapper = doc.select(REQUEST);

            if (requestWrapper.select("requestBody") != null) {
                String body = requestWrapper.select("requestBody").html();
                testBuilder.request(body);
            }

            HttpHeaders httpHeaders = xmlHeadersHelper.getHeaders(requestWrapper);
            testBuilder.requestHeaders(httpHeaders);

            Map<String, String> parameters = getXmlParameters(requestWrapper);
            testBuilder.requestParams(parameters);

        }
        if (doc.select(RESPONSE) != null) {
            Elements response = doc.select(RESPONSE);
            Elements headers = response.select(HEADERS);
            if (response.select(BODY) != null) {
                testBuilder.response(response.select("requestBody").html());
            }

            HttpHeaders httpHeaders = xmlHeadersHelper.getHeaders(response);
            testBuilder.responseHeaders(httpHeaders);
        }
        if (doc.select(DESCRIPTION).size() != 0) {

            testBuilder.description(((String) doc.select(DESCRIPTION).toString())).build();
        }

        return testBuilder.pathVariablesByName(new HashMap<>()).build();
    }

    private Test fromJsonObject(JSONObject jsonObject) {
        Test.TestBuilder testBuilder = Test.builder();
        if (jsonObject.has(REQUEST)) {
            JSONObject requestWrapper = (JSONObject) jsonObject.get(REQUEST);
            if (requestWrapper.has(BODY)) {
                String body = ((JSONObject) requestWrapper.get(BODY)).toString();
                testBuilder.request(body);
            }

            HttpHeaders httpHeaders = jsonHeadersHelper.getHeaders(requestWrapper);
            testBuilder.requestHeaders(httpHeaders);

            Map<String, String> parameters = getJsonParameters(requestWrapper);
            testBuilder.requestParams(parameters);

        }
        if (jsonObject.has(RESPONSE)) {
            JSONObject response = (JSONObject) jsonObject.get(RESPONSE);
            JSONObject headers = (JSONObject) response.get(HEADERS);
            if (response.has(BODY)) {
                testBuilder.response(((JSONObject) response.get(BODY)).toString());
            }

            HttpHeaders httpHeaders = jsonHeadersHelper.getHeaders(response);
            testBuilder.responseHeaders(httpHeaders);
        }
        if (jsonObject.has(DESCRIPTION)) {

            testBuilder.description(((String) jsonObject.get(DESCRIPTION).toString())).build();
        }

        testBuilder.pathVariablesByName(new HashMap<>());

        return testBuilder.build();
    }

    private Map<String,String> getXmlParameters(Elements requestWrapper) {
        Map<String, String> parameters = new HashMap<>();
        Elements rawParameters = requestWrapper.select("parameters > *");
        for (Element key : rawParameters) {
            parameters.put(key.toString(), (String) rawParameters.select(key.nodeName()).toString());
        }
        return parameters;
    }

    private Map<String, String> getJsonParameters(JSONObject requestWrapper) {
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
