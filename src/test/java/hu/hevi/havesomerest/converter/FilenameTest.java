package hu.hevi.havesomerest.converter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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
        assertFalse(expected.equals(actual));
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
        assertFalse(expected.equals(actual));
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
        assertFalse(expected.equals(actual));
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
        assertFalse(expected.equals(actual));
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
        assertFalse(expected.equals(actual));
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
        assertFalse(expected.equals(actual));
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
        assertFalse(expected.equals(actual));
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
        assertFalse(expected.equals(actual));
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
        assertFalse(expected.equals(actual));
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
        assertFalse(expected.equals(actual));
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
        assertFalse(expected.equals(actual));
    }

    @Test
    public void testGetPathVariablesShouldEqual() throws Exception {
        // GIVEN
        List<String> expected = Arrays.asList("pathVar1", "pathVar2");

        underTest = new Filename("get200_pathVar1_pathVar2_MyTest.json");

        // WHEN
        List<String> actual = underTest.getPathVariables();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testGetPathVariablesWhenLessIsPresentShouldNotEqual() throws Exception {
        // GIVEN
        List<String> expected = Arrays.asList("pathVar1", "pathVar2");

        underTest = new Filename("get200_pathVar1MyTest.json");

        // WHEN
        List<String> actual = underTest.getPathVariables();

        // THEN
        assertFalse(expected.equals(actual));
    }

    @Test
    public void testGetPathVariablesWhenMoreIsPresentShouldNotEqual() throws Exception {
        // GIVEN
        List<String> expected = Arrays.asList("pathVar1", "pathVar2");

        underTest = new Filename("get200_pathVar1_pathVar2_pathVar3_MyTest.json");

        // WHEN
        List<String> actual = underTest.getPathVariables();

        // THEN
        assertFalse(expected.equals(actual));
    }

    @Test
    public void testGetPathVariablesWhenTheyDifferentShouldNotEqual() throws Exception {
        // GIVEN
        List<String> expected = Arrays.asList("notThis1", "notThis2");

        underTest = new Filename("get200_pathVar1_pathVar2_MyTest.json");

        // WHEN
        List<String> actual = underTest.getPathVariables();

        // THEN
        assertFalse(expected.equals(actual));
    }
}