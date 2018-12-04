package bg.sofia.uni.fmi.mjt.sentiment;

import bg.sofia.uni.fmi.mjt.sentiment.interfaces.SentimentAnalyzer;

import java.io.*;
import java.util.*;

public class MovieReviewSentimentAnalyzer implements SentimentAnalyzer {

    BufferedReader stopwordsInput;
    BufferedReader reviewsInput;
    BufferedWriter reviewsOutput;

    HashMap<String, TreeMap<Integer, Double>> words;
    HashMap<Double, String> values;

    public MovieReviewSentimentAnalyzer(InputStream stopwordsInput, InputStream reviewsInput, OutputStream reviewsOutput) {
        this.stopwordsInput = new BufferedReader(new InputStreamReader(stopwordsInput));
        this.reviewsInput = new BufferedReader(new InputStreamReader(reviewsInput));
//        this.reviewsOutput = new BufferedWriter(new OutputStreamWriter(reviewsOutput));
        this.words = getReviewsWords();
        this.values = new HashMap<>();
    }

    @Override
    public double getReviewSentiment(String review) {
        double result = 0d;

        String[] reviewWords = review.split(" ");

        for (String word :
                reviewWords) {
            result += getWordSentiment(word);
        }

        return result;
    }

    @Override
    public String getReviewSentimentAsName(String review) {
        double reviewSentiment = getReviewSentiment(review);

        String result;

        if (reviewSentiment < 1) {
            result = "negative";
        } else if (reviewSentiment < 2) {
            result = "somewhat negative";
        } else if (reviewSentiment < 3) {
            result = "neutral";
        } else if (reviewSentiment < 4) {
            result = "somewhat positive";
        } else {
            result = "positive";
        }

        return result;
    }

    @Override
    public double getWordSentiment(String word) {

        TreeMap<Integer, Double> wordCountAndValue = words.get(word);

        if (wordCountAndValue == null) {
            return -1;
        }

        return (double) wordCountAndValue.firstEntry().getValue() / wordCountAndValue.firstEntry().getKey();
    }

    @Override
    public String getReview(double sentimentValue) {
        return null;
    }

    @Override
    public Collection<String> getMostFrequentWords(int n) {
        if (n < 0 || n > words.size()) {
            throw new IllegalArgumentException("N is either negative or bigger than the map size!");
        }


        Collection<String> mostFrequentWords = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            words.get(i);
        }

        return null;
    }

    @Override
    public Collection<String> getMostPositiveWords(int n) {
        return null;
    }

    @Override
    public Collection<String> getMostNegativeWords(int n) {
        return null;
    }

    @Override
    public void appendReview(String review, int sentimentValue) {

    }

    @Override
    public int getSentimentDictionarySize() {
        return 0;
    }

    @Override
    public boolean isStopWord(String word) {
        return false;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }

    private HashSet<String> getReviewsStopWords() {
        HashSet<String> stopwords = new HashSet<>();
        String line;

        try {
            while ((line = stopwordsInput.readLine()) != null) {
                stopwords.add(line);
            }
            stopwordsInput.close();

        } catch (IOException e) {
            throw new NullPointerException("demo");
        }

        return stopwords;
    }

    private HashMap<String, TreeMap<Integer, Double>> getReviewsWords() {

        HashMap<String, TreeMap<Integer, Double>> words = new HashMap<>();
        HashSet<String> stopwords = getReviewsStopWords();
        String line;

        try {
            while ((line = reviewsInput.readLine()) != null) {

                String wordsFromLine[] = line.split(" ");

                int rating = Integer.parseInt(wordsFromLine[0]);

                for (int i = 1; i < wordsFromLine.length; i++) {

                    String word = wordsFromLine[i];

                    if (stopwords.contains(word)) {
                        continue;
                    }

                    if (words.containsKey(word)) {
                        int count = words.get(word).firstEntry().getKey();
                        double value = words.get(word).firstEntry().getValue();

                        words.get(word).clear();

                        words.get(word).put(++count, value + rating);
                    } else {
                        TreeMap<Integer, Double> initialWordRate = new TreeMap<>();
                        initialWordRate.put(1, (double) rating);
                        words.put(word, initialWordRate);
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return words;
    }

}