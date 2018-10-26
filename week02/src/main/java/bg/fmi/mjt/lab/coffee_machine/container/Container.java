package bg.fmi.mjt.lab.coffee_machine.container;

public interface Container {

    public double getCurrentWater();

    public double getCurrentMilk();

    public double getCurrentCoffee();

    public double getCurrentCacao();

    public void makeCoffee(double water, double coffee, double milk, double cacao);
}