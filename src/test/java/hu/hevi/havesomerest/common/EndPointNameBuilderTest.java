package hu.hevi.havesomerest.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EndPointNameBuilderTest {

    private EndPointNameBuilder underTest;

    @Before
    public void setUp() {
        underTest = new EndPointNameBuilder();
    }

    @Test
    public void testBuildWithEmptyArrayShouldReturnEmptyString() {
        // GIVEN
        String expected = "";
        List<String> endpointParts = new ArrayList<>();
        // WHEN
        String actual = underTest.build(endpointParts);

        // THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testBuildShouldReturnEndpoint() {
        // GIVEN
        String expected = "/end/point/parts";
        List<String> endpointParts = Arrays.asList("end", "point", "parts");
        // WHEN
        String actual = underTest.build(endpointParts);

        // THEN
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void testBuildWithNullParamShouldThrowNullPointerException() {
        // GIVEN
        // WHEN
        String actual = underTest.build(null);

        // THEN
    }
}