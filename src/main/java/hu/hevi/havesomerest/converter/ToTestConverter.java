package hu.hevi.havesomerest.converter;

import hu.hevi.havesomerest.io.TestDirectory;
import hu.hevi.havesomerest.io.TestFile;
import hu.hevi.havesomerest.test.Test;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ToTestConverter {

    public static final String HEADERS = "headers";
    public static final String BODY = "body";
    public static final String DESCRIPTION = "description";
    public static final String PARAMETERS = "parameters";
    public static final String GET = "get";
    public static final String POST = "post";
    public static final String PUT = "put";
    public static final String PATCH = "patch";
    public static final String DELETE = "delete";
    public static final String JSON = "json";
    private static final String RESPONSE = "response";
    private static final String REQUEST = "request";
    private static final String JSON_SUFFIX = ".json";

    @Autowired
    private HeadersHelper headersHelper;

    public Map<Test, JSONObject> convert(Map<Path, Optional<TestDirectory>> filesByDirectory) {

        List<TestDirectory> testDirectories = new ArrayList<>();
        filesByDirectory.entrySet().forEach((en) -> {
            en.getValue().ifPresent(testDirectory -> {
                testDirectories.add(testDirectory);
            });
        });

        Map<Test, JSONObject> testsByFileContent = getTests(testDirectories);

        return testsByFileContent;
    }

    private String getStatusCodeFromFilename(String filename) {
        String statusCode = "";
        if (filename.toLowerCase().startsWith(GET)) {
            statusCode = filename.substring(3, 6);
        }
        if (filename.toLowerCase().startsWith(POST)) {
            statusCode = filename.substring(4, 7);
        }
        if (filename.toLowerCase().startsWith(PUT)) {
            statusCode = filename.substring(3, 6);
        }
        if (filename.toLowerCase().startsWith(PATCH)) {
            statusCode = filename.substring(5, 8);
        }
        if (filename.toLowerCase().startsWith(DELETE)) {
            statusCode = filename.substring(6, 9);
        }
        return statusCode;
    }

    private HttpMethod getMethodFromFilename(String filename) {
        HttpMethod httpMethod = HttpMethod.GET;
        if (filename.toLowerCase().startsWith(GET)) {
            httpMethod = HttpMethod.GET;
        }
        if (filename.toLowerCase().startsWith(POST)) {
            httpMethod = HttpMethod.POST;
        }
        if (filename.toLowerCase().startsWith(PUT)) {
            httpMethod = HttpMethod.PUT;
        }
        if (filename.toLowerCase().startsWith(PATCH)) {
            httpMethod = HttpMethod.PATCH;
        }
        if (filename.toLowerCase().startsWith(DELETE)) {
            httpMethod = HttpMethod.DELETE;
        }
        return httpMethod;
    }

    Map<Test, JSONObject> getTests(List<TestDirectory> testDirectories) {
        Map<Test, JSONObject> testByFileContent = new HashMap<>();
        testDirectories.stream()
                       .map(TestDirectory::getTestFiles)
                       .forEach(testCase -> {
                           testCase.forEach(testFile -> {
                               try {
                                   String fileContent = new String(Files.readAllBytes(testFile.getPath()));
                                   if (isJson(testFile)) {
                                       JSONObject convertedObject = new JSONObject(fileContent);

                                       if (isTestFile(testFile)) {
                                           Test t = getTest(convertedObject);
                                           String filename = testFile.getFileName();
                                           t.setName(filename);

                                           String[] splittedPath = testFile.getPath().toString().split("/");
                                           List<String> endpoints = getEndpoint(splittedPath);
                                           t.setEndpointParts(endpoints);

                                           List<String> pathVariablesInFileName = getPathVariablesFromFilename(testFile);
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

                                           String statusCode = getStatusCodeFromFilename(filename);
                                           HttpMethod httpMethod = getMethodFromFilename(filename);

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

    private List<String> getPathVariablesFromFilename(TestFile testFile) {
        String[] filenameSplittedByUnderscore = testFile.getFileName().split("_");
        List<String> filenameSplittedList = new LinkedList<>();
        if (filenameSplittedByUnderscore.length > 1) {
            filenameSplittedList = new LinkedList<>(Arrays.asList(filenameSplittedByUnderscore));
            if (hasAtLeastTwoElements(filenameSplittedList)) {
                filenameSplittedList.remove(0);
                filenameSplittedList.remove(filenameSplittedList.size() - 1);
            }
        }
        return filenameSplittedList;
    }

    private boolean hasAtLeastTwoElements(List<String> filenameSplittedList) {
        return filenameSplittedList.size() > 1;
    }

    private Test getTest(JSONObject jsonObject) {
        Test.TestBuilder testBuilder = Test.builder();
        if (jsonObject.has(REQUEST)) {
            JSONObject requestWrapper = (JSONObject) jsonObject.get(REQUEST);
            JSONObject body = (JSONObject) requestWrapper.get(BODY);

            HttpHeaders httpHeaders = headersHelper.getHeaders(requestWrapper);
            testBuilder.requestHeaders(httpHeaders);

            Map<String, String> parameters = getParameters(requestWrapper);
            testBuilder.requestParams(parameters);

            testBuilder.request(body);
        }
        if (jsonObject.has(RESPONSE)) {
            JSONObject response = (JSONObject) jsonObject.get(RESPONSE);
            JSONObject headers = (JSONObject) response.get(HEADERS);
            testBuilder.response((JSONObject) response.get(BODY));

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

    private boolean isTestFile(TestFile f) {
        String asd = f.getFileName();
        String[] split = asd.split("[.]");
        boolean isTestFile = false;
        if (split.length == 2 && JSON.equals(split[1])) {
            isTestFile = true;
        }
        return isTestFile;
    }

    private boolean isJson(TestFile f) {
        return f.getFileName().endsWith(JSON_SUFFIX);
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


