package pl.gornik;

public class Vehicle {
    private String plateNumber;
    private String brand;
    private String model;
    private Color color;

    public Vehicle(String plateNumber, String brand, String model, Color color) {
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
        this.color = color;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "PlateNumber: " + plateNumber + " | Brand: " + brand + " | Model: " + model + " | Color: " + color;
    }
}
