package hu.hevi.havesomerest.converter;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class HeadersHelperTest {

    public static final String HEADERS = "headers";
    public static final String KEY = "myKey";
    public static final String VALUE = "myValue";

    @Mock
    private JSONObject requestWrapper;
    @Mock
    private JSONObject rawHeaders;
    @Mock
    private Logger log;

    private JsonHeadersHelper underTest;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        underTest = new JsonHeadersHelper();
        underTest.setLog(log);
    }

    @Test
    public void testGetHeadersWithNoHeadersShouldLogWarning() throws Exception {
        // GIVEN
        // WHEN
        underTest.getHeaders(requestWrapper);

        // THEN
        verify(log).warn("Test file does not contain headers section");
    }

    @Test
    public void testGetHeadersWithEmptyHeadersShouldReturnEmptyHttpHeaders() throws Exception {
        // GIVEN
        HttpHeaders expected = new HttpHeaders();

        when(requestWrapper.get(HEADERS)).thenReturn(rawHeaders);
        when(rawHeaders.keySet()).thenReturn(new HashSet<String>());

        // WHEN
        HttpHeaders actual = underTest.getHeaders(requestWrapper);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testGetHeadersWithOneHeaderShouldReturnHttpHeaderWithProperKey() throws Exception {
        // GIVEN
        HttpHeaders expected = new HttpHeaders();
        expected.add(KEY, VALUE);

        when(requestWrapper.get(HEADERS)).thenReturn(rawHeaders);
        when(rawHeaders.keySet()).thenReturn(new HashSet<String>());

        // WHEN
        HttpHeaders actual = underTest.getHeaders(requestWrapper);

        // THEN
        assertTrue(expected.containsKey(KEY));
    }

    @Test
    public void testGetHeadersWithOneHeaderShouldReturnHttpHeaderWithProperValue() throws Exception {
        // GIVEN
        HttpHeaders expected = new HttpHeaders();
        expected.add(KEY, VALUE);

        Set<String> rawHeaderKeySet = new HashSet<String>(Arrays.asList(KEY));

        when(requestWrapper.get(HEADERS)).thenReturn(rawHeaders);
        when(rawHeaders.keySet()).thenReturn(rawHeaderKeySet);
        when(rawHeaders.get(KEY)).thenReturn(VALUE);

        // WHEN
        HttpHeaders actual = underTest.getHeaders(requestWrapper);

        // THEN
        assertEquals(expected.get(KEY), actual.get(KEY));
    }

    @Test
    public void testGetHeadersWithOneHeaderShouldReturnHttpHeaderWithSize1() throws Exception {
        // GIVEN
        HttpHeaders expected = new HttpHeaders();
        expected.add(KEY, VALUE);

        when(requestWrapper.get(HEADERS)).thenReturn(rawHeaders);
        when(rawHeaders.keySet()).thenReturn(new HashSet<String>());

        // WHEN
        HttpHeaders actual = underTest.getHeaders(requestWrapper);

        // THEN
        assertEquals(expected.size(), 1);
    }
}