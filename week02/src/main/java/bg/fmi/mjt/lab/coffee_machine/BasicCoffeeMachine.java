package bg.fmi.mjt.lab.coffee_machine;

import bg.fmi.mjt.lab.coffee_machine.container.BasicContainer;
import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;

public class BasicCoffeeMachine implements CoffeeMachine {

    private Container container;

    public BasicCoffeeMachine() {
        this.container = new BasicContainer();
    }

    @Override
    public Product brew(Beverage beverage) {
        if (beverage.getCacao() > container.getCurrentCacao() || beverage.getMilk() > container.getCurrentMilk() ||
            beverage.getWater() > container.getCurrentWater() || beverage.getCoffee() > container.getCurrentCoffee())
            return null;

        container.makeCoffee(beverage.getWater(), beverage.getCoffee(), beverage.getMilk(), beverage.getCacao());

        return new Product(beverage.getName(), 1, false);
    }

    @Override
    public Container getSupplies() {
        return this.container;
    }

    @Override
    public void refill() {
        this.container = new BasicContainer();
    }
}
