public interface CoffeeMachine {

    public Product brew(Beverage beverage);

    public Container getSupplies();

    public void refill();

}
