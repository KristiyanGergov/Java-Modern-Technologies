package bg.fmi.mjt.lab.coffee_machine;

import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;

public interface CoffeeMachine {

    public Product brew(Beverage beverage);

    public Container getSupplies();

    public void refill();

}
