package pl.gornik;

public class CarParkingManagementSystem {
    public static void main(String[] args) {
    DataBase database = new DataBase("parking_data.txt");
        DataBase.loadParkingData();
        database.addCar(new Vechicle("ABC123", "Toyota", "Corolla"));
        database.saveParkingData();
    }
}