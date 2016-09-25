package hu.hevi.havesomerest.io;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestDirectoryTest {

    @Mock
    private TestFile testFile;

    private TestDirectory underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new TestDirectory();
    }

    @Ignore
    @Test
    public void testAddTestFile() {
        // GIVEN
        List<TestFile> expected = Arrays.asList(testFile);

        TestDirectory testDirectory = TestDirectory.builder()
                                                   .testFile(testFile)
                                                   .build();
        // WHEN
        List<TestFile> actual = underTest.getTestFiles();

        // THEN
        assertEquals(expected, actual);
    }
}