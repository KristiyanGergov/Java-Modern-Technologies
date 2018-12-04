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
//        reviewsOutput = new FileOutputStream("C:\\Users\\Kristiyan Gergov\\Desktop\\Projects\\Java-Modern-Technologies\\homework02\\Movie Review Sentiment Analyzer\\resources\\reviews.txt");

        movieReviewSentimentAnalyzer = new MovieReviewSentimentAnalyzer(stopwordsInput, reviewsInput, null);

        stopwordsInput.close();
        reviewsInput.close();
//        reviewsOutput.close();
    }

    @Test
    public void testGetReviewSentiment() {

        assertEquals(0.0, movieReviewSentimentAnalyzer.getReviewSentiment(""), 0.00001);

        double first = movieReviewSentimentAnalyzer.getReviewSentiment("Dire disappointment: dull and unamusing freakshow");

        double second = movieReviewSentimentAnalyzer.getReviewSentiment("Immersive ecstasy: energizing artwork!");

    }

}
