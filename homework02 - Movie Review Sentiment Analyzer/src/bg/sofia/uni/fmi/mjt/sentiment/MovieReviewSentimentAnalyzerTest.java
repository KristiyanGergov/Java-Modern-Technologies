package bg.sofia.uni.fmi.mjt.sentiment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieReviewSentimentAnalyzerTest {

    private MovieReviewSentimentAnalyzer analyzer;

    private InputStream reviewsStream;
    private InputStream stopwordsStream;
    private OutputStream resultStream;

    @Before
    public void init() throws FileNotFoundException {
        stopwordsStream = new FileInputStream("resources/stopwords.txt");
        reviewsStream = new FileInputStream("resources/reviews.txt");
        resultStream = new FileOutputStream("resources/reviews.txt", true);
        analyzer = new MovieReviewSentimentAnalyzer(stopwordsStream, reviewsStream, resultStream);
    }

    @Test
    public void testIsStopWordNegativeFromDictionary() {
        String assertMessage = "A word should not be incorrectly identified as a stopword," +
                " if it is not part of the stopwords list";
        assertFalse(assertMessage, analyzer.isStopWord("effects"));
    }

    @Test
    public void testIsStopWordNegativeNotFromDictionary() {
        String assertMessage = "A word should not be incorrectly identified as a stopword, " +
                "if it is not part of the stopwords list";
        assertFalse(assertMessage, analyzer.isStopWord("stoyo"));
    }

    @Test
    public void testIsStopWordPositive() {
        assertTrue("Stop word not counted as stop word", analyzer.isStopWord("a"));
    }

    @Test
    public void testReviewSentimentCalculatesCorrectly() {
        String message = "escapades introspective misfortune Aggressive realization";
        double escapades = analyzer.getReviewSentiment("escapades");
        double introspective = analyzer.getWordSentiment("introspective");
        double aggressive = analyzer.getWordSentiment("aggressive");
        double realization = analyzer.getWordSentiment("realization");

        final double delta = 0.0001;
        final double testingElementsCount = 4.0;

        assertEquals((escapades +
                        introspective +
                        aggressive +
                        realization) / testingElementsCount,
                analyzer.getReviewSentiment(message), delta);
    }

    final double delta = 0.0;

    @Test
    public void testWordSentimentReturnMinusOneOnEmptyString() {
        assertEquals(-1, analyzer.getWordSentiment(""), delta);
        assertEquals(-1, analyzer.getWordSentiment(null), delta);
    }


    @Test
    public void testMostFrequentWords() {
        Collection<String> frequentWords = analyzer.getMostFrequentWords(1);
        assertNotEquals(frequentWords.toArray()[0], "");
    }

    @Test
    public void testGetReviewSentimentShouldReturnUnknown() {
        assertEquals(-1, analyzer.getReviewSentiment("all agasdggq gasvascv qwerqwer"), delta);
        assertEquals(-1, analyzer.getReviewSentiment("all between further having he he'll"), delta);
    }

    @Test
    public void testGetReviewSentimentAsName() {
        String line = "movie staggeringly overcome witless changes fascinating";
        assertEquals("somewhat positive", analyzer.getReviewSentimentAsName(line));

        line = "laughs like cartoonish Charlie character";
        assertEquals("somewhat positive", analyzer.getReviewSentimentAsName(line));

        line = "delightful thriller incompetent sulky drama";
        assertEquals("positive", analyzer.getReviewSentimentAsName(line));
    }

    @Test
    public void testValueWordsReturnsCorrectResult() {
        String word = analyzer.getReview(4);
        assertEquals("year", word);
        assertEquals("sweet", analyzer.getReview(2.5));
    }

    @Test
    public void testGetReviewSentimentReturnsMinusOne() {
        assertEquals(-1, analyzer.getReviewSentiment(null), delta);
    }

    @Test
    public void testAddReview() {

        final int sentimentValue = 4;
        String review = "test";

        analyzer.appendReview(review, sentimentValue);

        assertEquals(analyzer.getReviewSentimentAsName(review), "positive");

    }
}