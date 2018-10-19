package anagrams;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnagramTest {

    private Anagram anagram;

    @Before
    public void setUp() {
        anagram = new Anagram();
    }

    @Test
    public void testAnagrams_PlainAnagrams() {
        Assert.assertTrue(anagram.isAnagram("night thing"));
    }

    @Test
    public void testAnagrams_MissingLetter() {
        Assert.assertFalse(anagram.isAnagram("tired dire"));
    }

    @Test
    public void testAnagrams_IgnoreCaseAndSpecialChars() {
        Assert.assertTrue(anagram.isAnagram("eaT ate$"));
    }

    @Test
    public void testAnagrams_SameLettersDifferentCount() {
        Assert.assertFalse(anagram.isAnagram("beer beere"));
    }
}