package pl.gornik;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CarParkingManagementSystem {
    public static void main(String[] args) {
        UsersDatabase usersDatabase = new UsersDatabase("users_data.txt");
        usersDatabase.loadUsersData();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        User currentUser;

        while (!exit) {
            System.out.println("\nParking Management System");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    currentUser = usersDatabase.loginUser(username, password);

                    if (currentUser != null) {
                        System.out.println("Login successful! Welcome " + currentUser.getUsername());
                        userMenu(scanner, usersDatabase, currentUser);
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                }
                case 2 -> {
                    System.out.print("Enter new username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String password = scanner.nextLine();

                    try {
                        User newUser = new User(username, password, new ArrayList<>(),0);
                        usersDatabase.addUser(newUser);
                        usersDatabase.saveUsersData();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    exit = true;
                    usersDatabase.saveUsersData();
                    System.out.println("Data saved. Exiting...");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void userMenu(Scanner scanner, UsersDatabase usersDatabase, User currentUser) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\nUser Menu");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Your Wallet");
            System.out.println("4. Parking Reservation");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter vehicle plate number: ");
                    String plateNumber = scanner.nextLine();
                    System.out.print("Enter vehicle brand: ");
                    String brand = scanner.nextLine();
                    System.out.print("Enter vehicle model: ");
                    String model = scanner.nextLine();
                    System.out.println("Available colors: " + Arrays.toString(Color.values()));
                    System.out.print("Enter vehicle color: ");
                    String colorInput = scanner.nextLine().toUpperCase();
                    try {
                        Color color = Color.valueOf(colorInput);
                        Vehicle newVehicle = new Vehicle(plateNumber, brand, model, color);
                        usersDatabase.addVehicleToUser(currentUser.getUsername(), newVehicle);
                        usersDatabase.saveUsersData();
                        System.out.println("Database updated with new vehicle.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid color. Please try again.");
                    }
                } // Add Vehicle
                case 2 -> {
                    System.out.print("Enter vehicle plate number to remove: ");
                    String plateNumber = scanner.nextLine();
                    try {
                        usersDatabase.removeVehicleFromUser(currentUser.getUsername(), plateNumber);
                        usersDatabase.saveUsersData();
                        System.out.println("Database updated after vehicle removal.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                } // Remove Vehicle
                case 3 -> {
                    boolean walletMenuActive = true;
                    while (walletMenuActive) {
                        System.out.println("\nWallet Menu");
                        System.out.println("1. View Balance");
                        System.out.println("2. Top-up Balance (BLIK)");
                        System.out.println("3. Return to Main Menu");
                        System.out.print("Select an option: ");

                        int walletChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (walletChoice) {
                            case 1 -> {
                                System.out.printf("Your current balance: %.2f\n", currentUser.getBalance());
                            }
                            case 2 -> {
                                System.out.print("Enter BLIK code (6 digits): ");
                                String blikCode = scanner.nextLine();
                                if (blikCode.length() == 6) {
                                    System.out.print("Enter amount to top-up: ");
                                    double amount = scanner.nextDouble();
                                    scanner.nextLine();

                                    if (amount > 0) {
                                        usersDatabase.updateBalance(currentUser.getUsername(), amount);
                                        usersDatabase.saveUsersData();
                                        System.out.printf("Your account has been topped up by %.2f. New balance: %.2f\n",
                                                amount, currentUser.getBalance());
                                    } else {
                                        System.out.println("Invalid amount. Please try again.");
                                    }
                                } else {
                                    System.out.println("Invalid BLIK code. Please try again.");
                                }
                            }
                            case 3 -> walletMenuActive = false;
                            default -> System.out.println("Invalid choice. Please try again.");
                        }
                    }
                } // Your Wallet
                case 4 -> {
                        System.out.print("Enter vehicle plate number for parking: ");
                        String plateNumber = scanner.nextLine();

                        boolean vehicleFound = currentUser.getVehicles().stream()
                                .anyMatch(vehicle -> vehicle.getPlateNumber().equalsIgnoreCase(plateNumber));

                        if (!vehicleFound) {
                            System.out.println("Vehicle with plate number " + plateNumber + " is not associated with your account.");
                            break;
                        }

                        System.out.print("Enter parking start date and time (yyyy-MM-dd HH:mm): ");
                        String startDateTimeStr = scanner.nextLine();
                        System.out.print("Enter parking end date and time (yyyy-MM-dd HH:mm): ");
                        String endDateTimeStr = scanner.nextLine();

                        try {
                            LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                            LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                            ParkingSpot spot = new ParkingSpot(plateNumber, startDateTime, endDateTime);
                            usersDatabase.reserveParkingSpot(currentUser.getUsername(), spot);
                            usersDatabase.saveUsersData();
                            System.out.println("Parking reservation saved. Cost deducted from your balance.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }// Parking Reservation
                case 5 -> {
                    loggedIn = false;
                    System.out.println("Logged out.");
                } // Logout
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
