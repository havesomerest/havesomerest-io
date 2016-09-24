package hu.hevi.havesomerest.converter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class FilenameTest {

    private Filename underTest;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetNameWhenTheSameShouldEqual() throws Exception {
        // GIVEN
        String expected = "get200MyTest.json";

        underTest = new Filename("get200MyTest.json");

        // WHEN
        String actual = underTest.getName();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testGetNameWhenNotTheSameShouldNotEqual() throws Exception {
        // GIVEN
        String expected = "post200MyTest.json";

        underTest = new Filename("get200MyTest.json");

        // WHEN
        String actual = underTest.getName();

        // THEN
        assertNotSame(expected, actual);
    }

    @Test
    public void testGetMethodFromFilenameWhenGetShouldEqual() throws Exception {
        // GIVEN
        HttpMethod expected = HttpMethod.GET;

        underTest = new Filename("get200MyTest.json");

        // WHEN
        HttpMethod actual = underTest.getMethod();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testGetMethodFromFilenameWhenNotGetShouldNotEqual() throws Exception {
        // GIVEN
        HttpMethod expected = HttpMethod.GET;

        underTest = new Filename("post200MyTest.json");

        // WHEN
        HttpMethod actual = underTest.getMethod();

        // THEN
        assertNotSame(expected, actual);
    }

    @Test
    public void testGetMethodFromFilenameWhenPostShouldEqual() throws Exception {
        // GIVEN
        HttpMethod expected = HttpMethod.POST;

        underTest = new Filename("post200MyTest.json");

        // WHEN
        HttpMethod actual = underTest.getMethod();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testGetMethodFromFilenameWhenNotPostShouldNotEqual() throws Exception {
        // GIVEN
        HttpMethod expected = HttpMethod.POST;

        underTest = new Filename("get200MyTest.json");

        // WHEN
        HttpMethod actual = underTest.getMethod();

        // THEN
        assertNotSame(expected, actual);
    }

    @Test
    public void testGetMethodFromFilenameWhenPutShouldEqual() throws Exception {
        // GIVEN
        HttpMethod expected = HttpMethod.PUT;

        underTest = new Filename("put200MyTest.json");

        // WHEN
        HttpMethod actual = underTest.getMethod();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testGetMethodFromFilenameWhenNotPutShouldNotEqual() throws Exception {
        // GIVEN
        HttpMethod expected = HttpMethod.PUT;

        underTest = new Filename("post200MyTest.json");

        // WHEN
        HttpMethod actual = underTest.getMethod();

        // THEN
        assertNotSame(expected, actual);
    }

    @Test
    public void testGetMethodFromFilenameWhenPatchShouldEqual() throws Exception {
        // GIVEN
        HttpMethod expected = HttpMethod.PATCH;

        underTest = new Filename("patch200MyTest.json");

        // WHEN
        HttpMethod actual = underTest.getMethod();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testGetMethodFromFilenameWhenNotPatchShouldNotEqual() throws Exception {
        // GIVEN
        HttpMethod expected = HttpMethod.PATCH;

        underTest = new Filename("post200MyTest.json");

        // WHEN
        HttpMethod actual = underTest.getMethod();

        // THEN
        assertNotSame(expected, actual);
    }

    @Test
    public void testGetMethodFromFilenameWhenDeleteShouldEqual() throws Exception {
        // GIVEN
        HttpMethod expected = HttpMethod.DELETE;

        underTest = new Filename("delete200MyTest.json");

        // WHEN
        HttpMethod actual = underTest.getMethod();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testGetMethodFromFilenameWhenNotDeleteShouldNotEqual() throws Exception {
        // GIVEN
        HttpMethod expected = HttpMethod.DELETE;

        underTest = new Filename("post200MyTest.json");

        // WHEN
        HttpMethod actual = underTest.getMethod();

        // THEN
        assertNotSame(expected, actual);
    }

    @Test
    public void testGetStatusCodeWhenGet200ShouldEqual() throws Exception {
        // GIVEN
        String expected = "200";

        underTest = new Filename("get200MyTest.json");

        // WHEN
        String actual = underTest.getStatusCode();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatusCodeWhenNotGet200ShouldNotEqual() throws Exception {
        // GIVEN
        String expected = "200";

        underTest = new Filename("get300MyTest.json");

        // WHEN
        String actual = underTest.getStatusCode();

        // THEN
        assertNotSame(expected, actual);
    }

    @Test
    public void testGetStatusCodeWhenPost200ShouldEqual() throws Exception {
        // GIVEN
        String expected = "200";

        underTest = new Filename("post200MyTest.json");

        // WHEN
        String actual = underTest.getStatusCode();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatusCodeWhenNotPost200ShouldNotEqual() throws Exception {
        // GIVEN
        String expected = "200";

        underTest = new Filename("post300MyTest.json");

        // WHEN
        String actual = underTest.getStatusCode();

        // THEN
        assertNotSame(expected, actual);
    }

    @Test
    public void testGetStatusCodeWhenPut200ShouldEqual() throws Exception {
        // GIVEN
        String expected = "200";

        underTest = new Filename("put200MyTest.json");

        // WHEN
        String actual = underTest.getStatusCode();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatusCodeWhenNotPut200ShouldNotEqual() throws Exception {
        // GIVEN
        String expected = "200";

        underTest = new Filename("get300MyTest.json");

        // WHEN
        String actual = underTest.getStatusCode();

        // THEN
        assertNotSame(expected, actual);
    }

    @Test
    public void testGetStatusCodeWhenPatch200ShouldEqual() throws Exception {
        // GIVEN
        String expected = "200";

        underTest = new Filename("patch200MyTest.json");

        // WHEN
        String actual = underTest.getStatusCode();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatusCodeWhenNotPatch200ShouldNotEqual() throws Exception {
        // GIVEN
        String expected = "200";

        underTest = new Filename("get300MyTest.json");

        // WHEN
        String actual = underTest.getStatusCode();

        // THEN
        assertNotSame(expected, actual);
    }

    @Test
    public void testGetStatusCodeWhenDelete200ShouldEqual() throws Exception {
        // GIVEN
        String expected = "200";

        underTest = new Filename("delete200MyTest.json");

        // WHEN
        String actual = underTest.getStatusCode();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testGetStatusCodeWhenNotDelete200ShouldNotEqual() throws Exception {
        // GIVEN
        String expected = "200";

        underTest = new Filename("get300MyTest.json");

        // WHEN
        String actual = underTest.getStatusCode();

        // THEN
        assertNotSame(expected, actual);
    }
}