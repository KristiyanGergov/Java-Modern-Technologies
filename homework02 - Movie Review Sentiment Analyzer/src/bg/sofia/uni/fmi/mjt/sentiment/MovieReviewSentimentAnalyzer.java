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
    HashMap<String, Pair> mostValuedWords;
    HashMap<String, Pair> leastValuedWords;
    HashMap<Double, String> valueWords;
    HashSet<String> stopwords;

    private void initSortedCollections() {
        this.frequencyWords = words
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(o -> o.getValue().getCount())))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        this.mostValuedWords = words
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(o -> o.getValue().getValue())))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        this.leastValuedWords = words
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(o -> o.getValue().getValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        for (Map.Entry<String, Pair> entry :
                words.entrySet()) {
            double value = entry.getValue().getValue();

            if (!valueWords.containsKey(value)) {
                valueWords.put(value, entry.getKey());
            }
        }
    }

    public MovieReviewSentimentAnalyzer(InputStream stopwordsInput,
                                        InputStream reviewsInput,
                                        OutputStream reviewsOutput) {
        this.stopwordsInput = new BufferedReader(new InputStreamReader(stopwordsInput));
        this.reviewsInput = new BufferedReader(new InputStreamReader(reviewsInput));
        this.reviewsOutput = new BufferedWriter(new OutputStreamWriter(reviewsOutput));
        this.stopwords = getReviewsStopWords();
        this.valueWords = new HashMap<>();
        getReviewsWords();
        initSortedCollections();
        initSortedCollections();
    }

    @Override
    public double getReviewSentiment(String review) {

        double result = 0d;
        int count = 0;

        if (review == null)
            return -1;

        String[] reviewWords = review.split("(?![A-Za-z0-9]+).");


        for (String word :
                reviewWords) {
            double adder = getWordSentiment(word);

            if (adder != -1) {
                result += adder;
                count++;
            }
        }
        return result == 0 ? -1 : result / count;
    }

    // R A T I N G S
    private final static double NEGATIVE = 0;
    private final static double SOMEWHAT_NEGATIVE = 1;
    private final static double NEUTRAL = 2;
    private final static double SOMEWHAT_POSITIVE = 3;

    @Override
    public String getReviewSentimentAsName(String review) {

        int reviewSentiment = (int) Math.round(getReviewSentiment(review));

        if (reviewSentiment < NEGATIVE) {
            return "unknown";
        } else if (reviewSentiment == NEGATIVE) {
            return "negative";
        } else if (reviewSentiment == SOMEWHAT_NEGATIVE) {
            return "somewhat negative";
        } else if (reviewSentiment == NEUTRAL) {
            return "neutral";
        } else if (reviewSentiment == SOMEWHAT_POSITIVE) {
            return "somewhat positive";
        } else {
            return "positive";
        }
    }

    @Override
    public double getWordSentiment(String word) {

        if (word == null || word.equals(""))
            return -1;

        Pair wordCountAndValue = words.get(word.toLowerCase());

        if (wordCountAndValue == null) {
            return -1;
        }

        return wordCountAndValue.getValue();
    }

    @Override
    public String getReview(double sentimentValue) {
        return valueWords.get(sentimentValue);
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

        LinkedList<String> result = new LinkedList<>();

        int count = 0;
        for (Map.Entry<String, Pair> entry :
                mostValuedWords.entrySet()) {

            if (count++ == n) break;

            result.add(entry.getKey());
        }

        return result;
    }

    @Override
    public Collection<String> getMostNegativeWords(int n) {

        Collection<String> result = new LinkedList<>();

        int count = 0;
        for (Map.Entry<String, Pair> entry :
                leastValuedWords.entrySet()) {

            if (count++ == n) break;

            result.add(entry.getKey());
        }

        return result;
    }

    @Override
    public void appendReview(String review, int sentimentValue) {

        try {
            reviewsOutput.write(sentimentValue + " " + review);
            reviewsOutput.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }

        initializeWords(review, sentimentValue, false);
        initSortedCollections();
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
        return true;
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
                String[] words = line.split("(?![A-Za-z0-9]+).");
                stopwords.addAll(Arrays.asList(words));
            }
            stopwordsInput.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stopwords;
    }

    private void getReviewsWords() {

        words = new HashMap<>();
        String line;

        try {
            while ((line = reviewsInput.readLine()) != null) {

                String[] wordsFromLine = line.split("(?![A-Za-z0-9]+).");

                int rating = Integer.parseInt(wordsFromLine[0]);

                initializeWords(line, rating, true);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeWords(String review, int sentimentValue, boolean containsValueAtReview) {

        for (String word :
                review.split("(?![A-Za-z0-9]+).")) {

            if (containsValueAtReview || word.equals("")) {
                containsValueAtReview = false;
                continue;
            }

            word = word.toLowerCase();

            if (stopwords.contains(word)) {
                continue;
            }

            if (words.containsKey(word)) {
                words.get(word).increment(sentimentValue);
            } else {
                Pair initialWordRate = new Pair();
                initialWordRate.increment(sentimentValue);
                words.put(word, initialWordRate);
            }
        }
    }
}