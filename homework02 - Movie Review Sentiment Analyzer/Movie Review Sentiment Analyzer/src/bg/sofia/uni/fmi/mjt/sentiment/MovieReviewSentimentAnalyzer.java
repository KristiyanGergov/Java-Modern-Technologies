package bg.sofia.uni.fmi.mjt.sentiment;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class MovieReviewSentimentAnalyzer implements SentimentAnalyzer {

    BufferedReader stopwordsInput;
    BufferedReader reviewsInput;
    BufferedWriter reviewsOutput;

    HashMap<String, Pair> words;
    HashMap<String, Pair> frequencyWords;
    HashMap<String, Double> mostValuedWords;
    HashMap<String, Double> leastValuedWords;
    HashSet<String> stopwords;

    private LinkedList<String> getMostValued() {

        LinkedList<String> result = new LinkedList<>();
        double MaxValue = 0;

        for (Map.Entry<String, Pair> entry :
                words.entrySet()) {

            double currentValue = entry.getValue().getValue();

            if (currentValue > MaxValue) {
                result.add(entry.getKey());
            }
        }

        return result;
    }

    private void initSortedCollections() {
        this.frequencyWords = words
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(o -> o.getValue().getCount())))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

//        this.mostValuedWords = words
//                .entrySet()
//                .stream()
//                .sorted(Comparator.comparing(o -> o.getValue().getValue()))
//                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
//                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

    }

    public MovieReviewSentimentAnalyzer(InputStream stopwordsInput, InputStream reviewsInput, OutputStream reviewsOutput) {
        this.stopwordsInput = new BufferedReader(new InputStreamReader(stopwordsInput));
        this.reviewsInput = new BufferedReader(new InputStreamReader(reviewsInput));
        this.reviewsOutput = new BufferedWriter(new OutputStreamWriter(reviewsOutput));
        this.stopwords = getReviewsStopWords();
        this.words = getReviewsWords();
        initSortedCollections();
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

        Pair wordCountAndValue = words.get(word);

        if (wordCountAndValue == null) {
            return -1;
        }

        return wordCountAndValue.getValue();
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

        LinkedList<String> mostFrequentWords = new LinkedList<>();

        int count = 0;
        for (Map.Entry<String, Pair> entry :
                frequencyWords.entrySet()) {

            if (count++ == n) break;

            mostFrequentWords.add(entry.getKey());
        }

        return mostFrequentWords.size() > 0 ? mostFrequentWords : null;
    }

    @Override
    public Collection<String> getMostPositiveWords(int n) {

        LinkedList<String> res = getMostValued();

        LinkedList<String> result = new LinkedList<>();

        for (int i = n - 1; i >= 0; i--) {
            result.add(res.get(i));
        }

        return result;
    }

    @Override
    public Collection<String> getMostNegativeWords(int n) {

        LinkedList<String> res = getMostValued();

        LinkedList<String> result = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            result.add(res.get(i));
        }

        return result;
    }

    @Override
    public void appendReview(String review, int sentimentValue) {

    }

    @Override
    public int getSentimentDictionarySize() {
        return this.words.size();
    }

    @Override
    public boolean isStopWord(String word) {
        return stopwords.contains(word);
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

    private HashMap<String, Pair> getReviewsWords() {

        HashMap<String, Pair> words = new HashMap<>();
        String line;

        try {
            while ((line = reviewsInput.readLine()) != null) {

                String wordsFromLine[] = line.split(" ");

                int rating = Integer.parseInt(wordsFromLine[0]);

                for (int i = 1; i < wordsFromLine.length; i++) {

                    String word = wordsFromLine[i].toLowerCase();

                    if (stopwords.contains(word) || !word.matches("[a-zA-Z0-9]*")) {
                        continue;
                    }

                    if (words.containsKey(word)) {
                        words.get(word).increment(rating);
                    } else {
                        Pair initialWordRate = new Pair();
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