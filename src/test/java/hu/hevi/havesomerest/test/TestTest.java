package hu.hevi.havesomerest.test;

import hu.hevi.havesomerest.io.FileType;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TestTest {

    public static final String REQUEST = "request";
    public static final String RESPONSE = "response";

    private Test underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new Test();
        MockitoAnnotations.initMocks(this);
    }

    @org.junit.Test
    public void testHasRequestWhenHasShouldReturnTrue() throws Exception {
        // GIVEN
        boolean expected = true;

        underTest.setRequest(REQUEST);

        // WHEN
        boolean actual = underTest.hasRequest();

        // THEN
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testHasRequestWhenDoesntHaveShouldReturnFalse() throws Exception {
        // GIVEN
        boolean expected = false;

        underTest.setRequest(null);

        // WHEN
        boolean actual = underTest.hasRequest();

        // THEN
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testHasResponseWhenHasShouldReturnTrue() throws Exception {
        // GIVEN
        boolean expected = true;

        underTest.setResponse("response");

        // WHEN
        boolean actual = underTest.hasResponse();

        // THEN
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testHasResponseWhenDoesntHaveShouldReturnFalse() throws Exception {
        // GIVEN
        boolean expected = false;

        underTest.setResponse(null);

        // WHEN
        boolean actual = underTest.hasResponse();

        // THEN
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testBuilder() throws Exception {
        // GIVEN
        Test expected = new Test("name", "statusCode", Arrays.asList("end", "point", "parts"),
                                 new HashMap<>(), HttpMethod.POST, "description",
                                 new HttpHeaders(), new HashMap<>(), REQUEST,
                                 "response", new HttpHeaders(), FileType.JSON);

        // WHEN
        Test actual = underTest.toBuilder()
                               .name("name")
                               .statusCode("statusCode")
                               .endpointParts(Arrays.asList("end", "point", "parts"))
                               .pathVariablesByName(new HashMap<>())
                               .method(HttpMethod.POST)
                               .description("description")
                               .requestHeaders(new HttpHeaders())
                               .requestParams(new HashMap<>())
                               .request(REQUEST)
                               .response(RESPONSE)
                               .responseHeaders(new HttpHeaders())
                               .fileType(FileType.JSON)
                               .build();

        // THEN
        assertEquals(expected, actual);
    }


}