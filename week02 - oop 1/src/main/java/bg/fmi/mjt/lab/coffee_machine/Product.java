package bg.fmi.mjt.lab.coffee_machine;

public class Product {
    private String name;
    private int quantity;
    private String luck;
    private boolean isPremium;

    private static int luckIndex = 0;

    private static final String[] LucksType = {
            "If at first you don't succeed call it version 1.0.",
            "Today you will make magic happen!",
            "Have you tried turning it off and on again?",
            "Life would be much more easier if you had the source code."};

    public Product(String name, int quantity, boolean isPremium) {
        this.name = name;
        this.quantity = quantity;
        this.isPremium = isPremium;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getLuck() {
        if (!isPremium)
            return null;

        if (luckIndex == 3)
            luckIndex = 0;

        return LucksType[luckIndex++];
    }
}