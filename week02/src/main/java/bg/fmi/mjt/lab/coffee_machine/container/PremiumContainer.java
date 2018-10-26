package bg.fmi.mjt.lab.coffee_machine.container;

public class PremiumContainer implements Container {

    private double waterCapacity = 1000;
    private double coffeeCapacity = 1000;
    private double milkCapacity = 1000;
    private double cacaoCapacity = 300;

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
