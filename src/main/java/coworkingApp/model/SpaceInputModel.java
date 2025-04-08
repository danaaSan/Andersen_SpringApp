package coworkingApp.model;

import coworkingApp.entity.SpaceType;

public class SpaceInputModel {

    private double price;
    private SpaceType type;

    public SpaceInputModel() {}

    public SpaceInputModel(double price, SpaceType type) {
        this.price = price;
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public SpaceType getType() {
        return type;
    }

    public void setType(SpaceType type) {
        this.type = type;
    }
}
