package com.beginsecure.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class MainTest {

    private StringLengthSort stringLengthSort;

    @BeforeClass
    public void setupClass() {
        System.out.println("Before all tests.");
    }

    @BeforeMethod
    public void setUp() {
        stringLengthSort = new StringLengthSort();
        System.out.println("Before each test.");
    }
    @BeforeGroups("sortingLength")
    public void setUpStringLengthSort() {
        stringLengthSort = new StringLengthSort();
        System.out.println("Before StringLengthSort tests.");
    }

    @Test(groups = {"sortingLength"})
    public void testNullPointerExceptionWhenComparingNullValues() {
        assertThrows(NullPointerException.class, () -> stringLengthSort.compare(null, "test"));
        assertThrows(NullPointerException.class, () -> stringLengthSort.compare("test", null));
        assertThrows(NullPointerException.class, () -> stringLengthSort.compare(null, null));
    }

    @Test(groups = {"sortingLength"})
    public void testOutputDoesNotContainUnexpectedWords() throws IOException {
        Main.main(new String[]{});

        List<String> actualOutput = Files.readAllLines(Path.of("/Users/mariakardas/Desktop/Laboratory2/src/main/java/com/beginsecure/domain/output.txt"));

        assertThat(actualOutput, not(hasItems("Elephant1", "hamster")));
    }

    @Test(groups = {"sortingLength"})
    public void testStringLengthSort() {
        String str1 = "a";
        String str2 = "bb";
        String str3 = "ccc";

        assertTrue(stringLengthSort.compare(str1, str2) < 0);
        assertTrue(stringLengthSort.compare(str2, str3) < 0);
        assertTrue(stringLengthSort.compare(str1, str3) < 0);

        assertFalse(stringLengthSort.compare(str1, str2) > 0);
        assertFalse(stringLengthSort.compare(str2, str3) > 0);
        assertFalse(stringLengthSort.compare(str1, str3) > 0);

        assertEquals(0, stringLengthSort.compare(str1, str1));
        assertEquals(0, stringLengthSort.compare(str2, str2));
        assertEquals(0, stringLengthSort.compare(str3, str3));

        assertThat(stringLengthSort.compare(str1, str2), lessThan(0));
        assertThat(stringLengthSort.compare(str2, str3), lessThan(0));
        assertThat(stringLengthSort.compare(str1, str3), lessThan(0));
    }


    @DataProvider(name = "wordsDataProvider")
    public Object[][] provideWords() {
        return new Object[][] {
                {"apple", "banana", -1},
                {"cat", "dog", 0},
                {"elephant", "giraffe", 1}
        };
    }

    @Test(dataProvider = "wordsDataProvider", groups = {"parametrized"})
    public void testCompareWords(String word1, String word2, int expected) {
        StringLengthSort stringLengthSort = new StringLengthSort();
        int result = stringLengthSort.compare(word1, word2);
        assertEquals(result, expected);
    }

    @DataProvider(name = "inputDataProvider")
    public Object[][] provideInputData() {
        return new Object[][] {
                { "apple, banana, cat", Arrays.asList("cat", "apple", "banana") },
                { "dog, elephant, giraffe", Arrays.asList("dog", "giraffe", "elephant") },
                { "horse, snake, tiger", Arrays.asList("horse", "snake", "tiger") }
        };
    }

    @Test(dataProvider = "inputDataProvider", groups = {"parametrized"})
    public void testMainFunction(String input, List<String> expectedOutput) throws IOException {

        Files.write(Path.of("/Users/mariakardas/Desktop/Laboratory2/src/main/java/com/beginsecure/domain/input.txt"), input.getBytes());

        Main.main(new String[]{});

        List<String> actualOutput = Files.readAllLines(Path.of("/Users/mariakardas/Desktop/Laboratory2/src/main/java/com/beginsecure/domain/output.txt"));

        Assert.assertEquals(actualOutput, expectedOutput);
    }


}


