package hu.hevi.havesomerest.io;

import hu.hevi.havesomerest.converter.Filename;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;

import java.nio.file.Path;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TestFileTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Path path;

    private TestFile underTest;

    @Before
    public void setUp() {
        initMocks(this);
        underTest = new TestFile(path);
    }

    @Test
    public void getFileName() throws Exception {
        // GIVEN
        Filename expected = new Filename("get200MyTest.json");

        when(path.getFileName().toString()).thenReturn("get200MyTest.json");

        // WHEN
        Filename actual = underTest.getFileName();

        // THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsGetWhenGetShouldReturnTrue() throws Exception {
        // GIVEN
        boolean expected = true;

        when(path.getFileName().toString()).thenReturn("get200MyTest.json");

        // WHEN
        boolean actual = underTest.isGet();

        // THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsGetWhenNotGetShouldReturnFalse() throws Exception {
        // GIVEN
        boolean expected = false;

        when(path.getFileName().toString()).thenReturn("post200MyTest.json");

        // WHEN
        boolean actual = underTest.isGet();

        // THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsPostWhenGetShouldReturnTrue() throws Exception {
        // GIVEN
        boolean expected = true;

        when(path.getFileName().toString()).thenReturn("post200MyTest.json");

        // WHEN
        boolean actual = underTest.isPost();

        // THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsPostWhenNotGetShouldReturnFalse() throws Exception {
        // GIVEN
        boolean expected = false;

        when(path.getFileName().toString()).thenReturn("get200MyTest.json");

        // WHEN
        boolean actual = underTest.isPost();

        // THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsPutWhenGetShouldReturnTrue() throws Exception {
        // GIVEN
        boolean expected = true;

        when(path.getFileName().toString()).thenReturn("put200MyTest.json");

        // WHEN
        boolean actual = underTest.isPut();

        // THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsPutWhenNotGetShouldReturnFalse() throws Exception {
        // GIVEN
        boolean expected = false;

        when(path.getFileName().toString()).thenReturn("post200MyTest.json");

        // WHEN
        boolean actual = underTest.isPut();

        // THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsDeleteWhenGetShouldReturnTrue() throws Exception {
        // GIVEN
        boolean expected = true;

        when(path.getFileName().toString()).thenReturn("delete200MyTest.json");

        // WHEN
        boolean actual = underTest.isDelete();

        // THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsDeleteWhenNotGetShouldReturnFalse() throws Exception {
        // GIVEN
        boolean expected = false;

        when(path.getFileName().toString()).thenReturn("post200MyTest.json");

        // WHEN
        boolean actual = underTest.isDelete();

        // THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetStatusWhenFilenameIsGetShouldReturn200() throws Exception {
        // GIVEN
        String expected = "200";

        when(path.getFileName().toString()).thenReturn("get200MyTest.json");

        // WHEN
        String actual = underTest.getStatus();

        // THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetStatusWhenFilenameIsPostShouldReturn200() throws Exception {
        // GIVEN
        String expected = "200";

        when(path.getFileName().toString()).thenReturn("post200MyTest.json");

        // WHEN
        String actual = underTest.getStatus();

        // THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetStatusWhenFilenameIsDeleteShouldReturn404() throws Exception {
        // GIVEN
        String expected = "404";

        when(path.getFileName().toString()).thenReturn("delete404MyTest.json");

        // WHEN
        String actual = underTest.getStatus();

        // THEN
        Assert.assertEquals(expected, actual);
    }

    // Coverage

    @Test
    public void testGetPath() throws Exception {
        // GIVEN
        Path expected = path;

        // WHEN
        Path actual = underTest.getPath();

        // THEN
        Assert.assertEquals(expected, actual);
    }
}