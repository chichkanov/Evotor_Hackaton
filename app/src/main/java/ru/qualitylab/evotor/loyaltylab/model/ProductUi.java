package ru.qualitylab.evotor.loyaltylab.model;

public class ProductUi {

    private String name;
    private double price;
    private boolean enabled;

    public ProductUi(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public double getPrice() {
        return price;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
