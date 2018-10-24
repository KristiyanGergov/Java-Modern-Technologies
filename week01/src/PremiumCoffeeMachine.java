public class PremiumCoffeeMachine implements CoffeeMachine {

    private Container container;

    public PremiumCoffeeMachine(boolean autoRefill) {

    }

    public PremiumCoffeeMachine() {
    }

    public Product brew(Beverage beverage, int quantity) {
        Product product = new Product();

        product.setName(beverage.getName());

        return product;
    }


    @Override
    public Product brew(Beverage beverage) {
        return null;
    }

    @Override
    public Container getSupplies() {
        return null;
    }

    @Override
    public void refill() {

    }
}