package pl.gornik;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<Vehicle> vehicles;
    private double balance;

    public User(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.vehicles = new ArrayList<>();
        this.balance = balance;
    }

    public User(String username, String password, List<Vehicle> vehicles, double balance) {
        this.username = username;
        this.password = password;
        this.vehicles = vehicles;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    @Override
    public String toString() {
        return "User: " + username + " | Balance: " + balance + " PLN | Vehicles: " + vehicles;
    }
}
