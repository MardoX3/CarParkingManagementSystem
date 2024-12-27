package pl.gornik;

import java.io.*;
import java.util.*;

public class UsersDatabase {
    private static String filePath;
    private static List<User> usersList;

    public UsersDatabase(String filePath) {
        UsersDatabase.filePath = filePath;
        usersList = new ArrayList<>();
    }

    public void loadUsersData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    String username = data[0].trim();
                    String password = data[1].trim();
                    double balance = Double.parseDouble(data[2].trim());

                    List<Vehicle> vehicles = new ArrayList<>();
                    if (data.length > 3) {
                        for (int i = 3; i < data.length; i += 4) {
                            if (i + 3 < data.length) {
                                String plateNumber = data[i].trim();
                                String brand = data[i + 1].trim();
                                String model = data[i + 2].trim();
                                Color color = Color.valueOf(data[i + 3].trim().toUpperCase());
                                vehicles.add(new Vehicle(plateNumber, brand, model, color));
                            }
                        }
                    }

                    usersList.add(new User(username, password, vehicles, balance));
                } else {
                    System.out.println("Invalid data line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("User data file not found. A new one will be created.");
        } catch (IOException e) {
            System.out.println("Error reading user data: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error processing user data: " + e.getMessage());
        }
    }



    public void saveUsersData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : usersList) {
                StringBuilder userData = new StringBuilder(user.getUsername() + "," + user.getPassword() + "," + user.getBalance());
                for (Vehicle vehicle : user.getVehicles()) {
                    userData.append(",").append(vehicle.getPlateNumber())
                            .append(",").append(vehicle.getBrand())
                            .append(",").append(vehicle.getModel())
                            .append(",").append(vehicle.getColor());
                }
                writer.write(userData.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }


    public void addUser(User user) {
        for (User existingUser : usersList) {
            if (existingUser.getUsername().equalsIgnoreCase(user.getUsername())) {
                throw new IllegalArgumentException("User with username " + user.getUsername() + " already exists.");
            }
        }
        usersList.add(user);
        System.out.println("User added: " + user);
    }

    public User loginUser(String username, String password) {
        for (User user : usersList) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void updateBalance(String username, double amount) {
        for (User user : usersList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                user.setBalance(user.getBalance() + amount);
                System.out.println("Balance updated for user " + username + ". New balance: " + user.getBalance());
                return;
            }
        }
        throw new IllegalArgumentException("User with username " + username + " not found.");
    }

    public void addVehicleToUser(String username, Vehicle vehicle) {
        for (User user : usersList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                user.addVehicle(vehicle);
                System.out.println("Vehicle added to user " + username + ": " + vehicle);
                return;
            }
        }
        throw new IllegalArgumentException("User with username " + username + " not found.");
    }




    public void removeVehicleFromUser(String username, String plateNumber) {
        for (User user : usersList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                Iterator<Vehicle> vehicleIterator = user.getVehicles().iterator();
                while (vehicleIterator.hasNext()) {
                    Vehicle vehicle = vehicleIterator.next();
                    if (vehicle.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                        vehicleIterator.remove();
                        System.out.println("Vehicle with plate number " + plateNumber + " removed from user " + username);
                        return;
                    }
                }
                throw new IllegalArgumentException("Vehicle with plate number " + plateNumber + " not found for user " + username);
            }
        }
        throw new IllegalArgumentException("User with username " + username + " not found.");
    }

    public void reserveParkingSpot(String username, ParkingSpot spot) {
        for (User user : usersList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                boolean vehicleExists = user.getVehicles().stream()
                        .anyMatch(vehicle -> vehicle.getPlateNumber().equalsIgnoreCase(spot.getPlateNumber()));

                if (!vehicleExists) {
                    throw new IllegalArgumentException("Vehicle with plate number " + spot.getPlateNumber() + " is not associated with your account.");
                }

                long cost = spot.calculateParkingCost();
                if (user.getBalance() < cost) {
                    throw new IllegalArgumentException("Insufficient balance. Parking cost is " + cost + " PLN.");
                }

                user.setBalance(user.getBalance() - cost);
                System.out.println("Parking spot reserved: " + spot);
                return;
            }
        }
        throw new IllegalArgumentException("User with username " + username + " not found.");
    }
}
