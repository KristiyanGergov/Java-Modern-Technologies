package bg.fmi.mjt.lab.coffee_machine.container;

public class BasicContainer implements Container {

    private double waterCapacity = 600;
    private double coffeeCapacity = 600;
    private double milkCapacity = 0;
    private double cacaoCapacity = 0;

    @Override
    public double getCurrentWater() {
        return waterCapacity;
    }

    @Override
    public double getCurrentMilk() {
        return milkCapacity;
    }

    @Override
    public double getCurrentCoffee() {
        return coffeeCapacity;
    }

    @Override
    public double getCurrentCacao() {
        return cacaoCapacity;
    }

    @Override
    public void makeCoffee(double water, double coffee, double milk, double cacao) {
        this.coffeeCapacity -= coffee;
        this.waterCapacity -= water;
        this.cacaoCapacity -= cacao;
        this.milkCapacity -= milk;
    }
}