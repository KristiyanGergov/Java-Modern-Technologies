package bg.sofia.uni.fmi.mjt.sentiment;

public class Pair {

    private double value;
    private int count;

    public void increment(double value) {
        this.count++;
        this.value += value;
    }

    public double getValue() {
        return value / (double) count;
    }

    public Integer getCount() {
        return this.count;
    }
}