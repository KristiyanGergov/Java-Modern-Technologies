package bg.sofia.uni.fmi.mjt.sentiment;

public class Pair {

    private double value;
    private int count;

    public Pair(){
        this.value = 0;
        this.count = 1;
    }

    public void increment(double value) {
        this.count++;
        this.value += value;
    }

    public double getValue(){
        return value / (double)count;
    }

    public Integer getCount(){
        return this.count;
    }
}