public class BasicCoffeeMachine implements CoffeeMachine {

    public BasicCoffeeMachine() {

    }

    @Override
    public Product brew(Beverage beverage) {
        Product product = new Product();

        product.setName(beverage.getName());
        product.setQuantity((int)beverage.getWater());

        return product;
    }

    @Override
    public Container getSupplies() {
        return null;
    }

    @Override
    public void refill() {

    }
}
