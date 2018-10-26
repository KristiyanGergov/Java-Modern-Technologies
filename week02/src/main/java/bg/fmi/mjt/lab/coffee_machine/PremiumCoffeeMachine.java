package bg.fmi.mjt.lab.coffee_machine;

import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.container.PremiumContainer;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;

public class PremiumCoffeeMachine implements CoffeeMachine {

    private Container container;
    private boolean autoRefill;
    private final boolean isPremium = true;
    private static final double PREMIUM_CONTAINER_INITIAL_WATER = 1000;

    public PremiumCoffeeMachine(boolean autoRefill) {
        this.autoRefill = autoRefill;
        this.container = new PremiumContainer();
    }

    public PremiumCoffeeMachine() {
        this.container = new PremiumContainer();
    }

    public Product brew(Beverage beverage, int quantity) {
        if (beverage.getWater() > PREMIUM_CONTAINER_INITIAL_WATER || beverage.getMilk() > PREMIUM_CONTAINER_INITIAL_WATER || beverage.getCoffee() > PREMIUM_CONTAINER_INITIAL_WATER || beverage.getCacao() > 300)
            return null;
        if (quantity <= 0)
            return null;

        if (autoRefill)
            return new Product(beverage.getName(), quantity, isPremium);

        int coffeesMade = 0;

        for (int i = 0; i < quantity; i++) {
            if (beverage.getCacao() > container.getCurrentCacao() || beverage.getMilk() > container.getCurrentMilk()
                    || beverage.getWater() > container.getCurrentWater() || beverage.getCoffee() > container.getCurrentCoffee())
                break;
            coffeesMade++;
            container.makeCoffee(beverage.getWater(), beverage.getCoffee(), beverage.getMilk(), beverage.getCacao());
        }

        return new Product(beverage.getName(), coffeesMade, isPremium);
    }


    @Override
    public Product brew(Beverage beverage) {
        if (beverage.getWater() > PREMIUM_CONTAINER_INITIAL_WATER || beverage.getMilk() > PREMIUM_CONTAINER_INITIAL_WATER || beverage.getCoffee() > PREMIUM_CONTAINER_INITIAL_WATER || beverage.getCacao() > 300)
            return null;

        if (autoRefill)
            return new Product(beverage.getName(), 1, isPremium);

        if (beverage.getCacao() > container.getCurrentCacao() || beverage.getMilk() > container.getCurrentMilk() ||
                beverage.getWater() > container.getCurrentWater() || beverage.getCoffee() > container.getCurrentCoffee())
            return null;

        container.makeCoffee(beverage.getWater(), beverage.getCoffee(), beverage.getMilk(), beverage.getCacao());

        return new Product(beverage.getName(), 1, isPremium);
    }

    @Override
    public Container getSupplies() {
        return this.container;
    }

    @Override
    public void refill() {
        this.container = new PremiumContainer();
    }
}