package pl.gornik;

public class Vechicle {
    private String plateNumber;
    private String brand;
    private String model;

    public Vechicle(String plateNumber, String brand, String model) {
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
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

    @Override
    public String toString() {
        return "PlateNumber: "+getPlateNumber() +" | Brand: "+getBrand()+" | Model: "+getModel();
    }
}
