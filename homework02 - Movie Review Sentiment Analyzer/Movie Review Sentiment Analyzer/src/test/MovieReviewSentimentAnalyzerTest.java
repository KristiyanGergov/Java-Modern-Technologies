package test;

import bg.sofia.uni.fmi.mjt.sentiment.MovieReviewSentimentAnalyzer;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class MovieReviewSentimentAnalyzerTest {

    private MovieReviewSentimentAnalyzer movieReviewSentimentAnalyzer;

    @Before
    public void init() throws IOException {
        InputStream stopwordsInput;
        InputStream reviewsInput;
        OutputStream reviewsOutput;

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        stopwordsInput = classloader.getResourceAsStream("stopwords.txt");
        reviewsInput = classloader.getResourceAsStream("reviews.txt");
        reviewsOutput = new FileOutputStream("resources/reviews.txt", true);

        movieReviewSentimentAnalyzer = new MovieReviewSentimentAnalyzer(stopwordsInput, reviewsInput, reviewsOutput);

        stopwordsInput.close();
        reviewsInput.close();
        reviewsOutput.close();
    }

    @Test
    public void testGetReviewSentiment() {

        movieReviewSentimentAnalyzer.getMostFrequentWords(5);

        assertEquals(-1, movieReviewSentimentAnalyzer.getReviewSentiment(""), 0.00001);

        double first = movieReviewSentimentAnalyzer.getReviewSentiment("Dire disappointment: dull and unamusing freakshow");

        double second = movieReviewSentimentAnalyzer.getReviewSentiment("Immersive ecstasy: energizing artwork!");

        assertTrue("Stop word not counted as stop word", movieReviewSentimentAnalyzer.isStopWord("a"));

        String assertMessage = "A word should not be incorrectly identified as a stopword, if it is not part of the stopwords list";
        assertFalse(assertMessage, movieReviewSentimentAnalyzer.isStopWord("stoyo"));

        String assertMessage1 = "A word should not be incorrectly identified as a stopword, if it is not part of the stopwords list";
        assertFalse(assertMessage1, movieReviewSentimentAnalyzer.isStopWord("effects"));
    }

}
