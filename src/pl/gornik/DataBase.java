package pl.gornik;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private static String filePath;
    private static List<Vechicle> parkingList;
    public DataBase(String filePath) {
        DataBase.filePath = filePath;
        parkingList = new ArrayList<>();
    }
    public static void loadParkingData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String plateNumber = data[0].trim();
                    String brand = data[1].trim();
                    String model = data[2].trim();
                    parkingList.add(new Vechicle(plateNumber, brand, model));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Parking data file not found. A new one will be created.");
        } catch (IOException e) {
            System.out.println("Error reading parking data: " + e.getMessage());
        }
    }

    public static void saveParkingData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Vechicle car : parkingList) {
                writer.write(car.getPlateNumber() + "," + car.getBrand() + "," + car.getModel());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving parking data: " + e.getMessage());
        }
    }

    public static void addCar(Vechicle car) {
        for (Vechicle existingCar : parkingList) {
            if (existingCar.getPlateNumber().equalsIgnoreCase(car.getPlateNumber())) {
                throw new IllegalArgumentException("Car with plate number " + car.getPlateNumber() + " already exists.");
            }
        }
                parkingList.add(car);
                System.out.println("Car added: " + car);
            }

    public static void displayParking() {
        System.out.println("Current Parking List:");
        for (Vechicle car : parkingList) {
            System.out.println(car);
        }
    }
}
