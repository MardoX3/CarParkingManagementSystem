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
            System.out.println(ConsoleColors.CYAN_BOLD + "\nParking Management System" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + "1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.GREEN + "Select an option: " + ConsoleColors.RESET);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print(ConsoleColors.YELLOW + "Enter username: " + ConsoleColors.RESET);
                    String username = scanner.nextLine();
                    System.out.print(ConsoleColors.YELLOW + "Enter password: " + ConsoleColors.RESET);
                    String password = scanner.nextLine();

                    currentUser = usersDatabase.loginUser(username, password);

                    if (currentUser != null) {
                        System.out.println(ConsoleColors.GREEN_BOLD + "Login successful! Welcome " + currentUser.getUsername() + ConsoleColors.RESET);
                        userMenu(scanner, usersDatabase, currentUser);
                    } else {
                        System.out.println(ConsoleColors.RED_BOLD + "Invalid username or password." + ConsoleColors.RESET);
                    }
                }
                case 2 -> {
                    System.out.print(ConsoleColors.YELLOW + "Enter new username: " + ConsoleColors.RESET);
                    String username = scanner.nextLine();
                    System.out.print(ConsoleColors.YELLOW + "Enter new password: " + ConsoleColors.RESET);
                    String password = scanner.nextLine();

                    try {
                        User newUser = new User(username, password, new ArrayList<>(),0);
                        usersDatabase.addUser(newUser);
                        usersDatabase.saveUsersData();
                    } catch (IllegalArgumentException e) {
                        System.out.println(ConsoleColors.RED_BOLD + e.getMessage() + ConsoleColors.RESET);
                    }
                }
                case 3 -> {
                    exit = true;
                    usersDatabase.saveUsersData();
                    System.out.println(ConsoleColors.GREEN_BOLD + "Data saved. Exiting..." + ConsoleColors.RESET);
                }
                default -> System.out.println(ConsoleColors.RED_BOLD + "Invalid choice. Please try again." + ConsoleColors.RESET);
            }
        }
    }

    private static void userMenu(Scanner scanner, UsersDatabase usersDatabase, User currentUser) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println(ConsoleColors.CYAN_BOLD + "\nUser Menu" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + "1. Add Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Your Wallet");
            System.out.println("4. Parking Reservation");
            System.out.println("5. Logout" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.GREEN + "Select an option: " + ConsoleColors.RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print(ConsoleColors.YELLOW + "Enter vehicle plate number: " + ConsoleColors.RESET);
                    String plateNumber = scanner.nextLine();
                    System.out.print(ConsoleColors.YELLOW + "Enter vehicle brand: " + ConsoleColors.RESET);
                    String brand = scanner.nextLine();
                    System.out.print(ConsoleColors.YELLOW + "Enter vehicle model: " + ConsoleColors.RESET);
                    String model = scanner.nextLine();
                    System.out.println(ConsoleColors.CYAN + "Available colors: " + Arrays.toString(Color.values()) + ConsoleColors.RESET);
                    System.out.print(ConsoleColors.YELLOW + "Enter vehicle color: " + ConsoleColors.RESET);
                    String colorInput = scanner.nextLine().toUpperCase();
                    try {
                        Color color = Color.valueOf(colorInput);
                        Vehicle newVehicle = new Vehicle(plateNumber, brand, model, color);
                        usersDatabase.addVehicleToUser(currentUser.getUsername(), newVehicle);
                        usersDatabase.saveUsersData();
                        System.out.println(ConsoleColors.GREEN_BOLD + "Database updated with new vehicle." + ConsoleColors.RESET);
                    } catch (IllegalArgumentException e) {
                        System.out.println(ConsoleColors.RED_BOLD + "Invalid color. Please try again." + ConsoleColors.RESET);
                    }
                }
                case 2 -> {
                    System.out.print(ConsoleColors.YELLOW + "Enter vehicle plate number to remove: " + ConsoleColors.RESET);
                    String plateNumber = scanner.nextLine();
                    try {
                        usersDatabase.removeVehicleFromUser(currentUser.getUsername(), plateNumber);
                        usersDatabase.saveUsersData();
                        System.out.println(ConsoleColors.GREEN_BOLD + "Database updated after vehicle removal." + ConsoleColors.RESET);
                    } catch (IllegalArgumentException e) {
                        System.out.println(ConsoleColors.RED_BOLD + e.getMessage() + ConsoleColors.RESET);
                    }
                }
                case 3 -> {
                    boolean walletMenuActive = true;
                    while (walletMenuActive) {
                        System.out.println(ConsoleColors.CYAN_BOLD + "\nWallet Menu" + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.BLUE + "1. View Balance");
                        System.out.println("2. Top-up Balance (BLIK)");
                        System.out.println("3. Return to Main Menu" + ConsoleColors.RESET);
                        System.out.print(ConsoleColors.GREEN + "Select an option: " + ConsoleColors.RESET);

                        int walletChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (walletChoice) {
                            case 1 -> {
                                System.out.println();
                                System.out.printf(ConsoleColors.GREEN_BOLD + "Your current balance: %.2f\n" + ConsoleColors.RESET, currentUser.getBalance());
                            }
                            case 2 -> {
                                System.out.print(ConsoleColors.YELLOW + "Enter BLIK code (6 digits): " + ConsoleColors.RESET);
                                String blikCode = scanner.nextLine();
                                if (blikCode.length() == 6) {
                                    System.out.print(ConsoleColors.YELLOW + "Enter amount to top-up: " + ConsoleColors.RESET);
                                    double amount = scanner.nextDouble();
                                    scanner.nextLine();

                                    if (amount > 0) {
                                        usersDatabase.updateBalance(currentUser.getUsername(), amount);
                                        usersDatabase.saveUsersData();
                                        System.out.printf(ConsoleColors.GREEN_BOLD + "Your account has been topped up by %.2f. New balance: %.2f\n" + ConsoleColors.RESET,
                                                amount, currentUser.getBalance());
                                    } else {
                                        System.out.println(ConsoleColors.RED_BOLD + "Invalid amount. Please try again." + ConsoleColors.RESET);
                                    }
                                } else {
                                    System.out.println(ConsoleColors.RED_BOLD + "Invalid BLIK code. Please try again." + ConsoleColors.RESET);
                                }
                            }
                            case 3 -> walletMenuActive = false;
                            default -> System.out.println(ConsoleColors.RED_BOLD + "Invalid choice. Please try again." + ConsoleColors.RESET);
                        }
                    }
                }
                case 4 -> {
                    System.out.print(ConsoleColors.YELLOW + "Enter vehicle plate number for parking: " + ConsoleColors.RESET);
                    String plateNumber = scanner.nextLine();

                    boolean vehicleFound = currentUser.getVehicles().stream()
                            .anyMatch(vehicle -> vehicle.getPlateNumber().equalsIgnoreCase(plateNumber));

                    if (!vehicleFound) {
                        System.out.println(ConsoleColors.RED_BOLD + "Vehicle with plate number " + plateNumber + " is not associated with your account." + ConsoleColors.RESET);
                        break;
                    }

                    System.out.print(ConsoleColors.YELLOW + "Enter parking start date and time (yyyy-MM-dd HH:mm): " + ConsoleColors.RESET);
                    String startDateTimeStr = scanner.nextLine();
                    System.out.print(ConsoleColors.YELLOW + "Enter parking end date and time (yyyy-MM-dd HH:mm): " + ConsoleColors.RESET);
                    String endDateTimeStr = scanner.nextLine();

                    try {
                        LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                        ParkingSpot spot = new ParkingSpot(plateNumber, startDateTime, endDateTime);
                        usersDatabase.reserveParkingSpot(currentUser.getUsername(), spot);
                        usersDatabase.saveUsersData();
                        System.out.println(ConsoleColors.GREEN_BOLD + "Parking reservation saved. Cost deducted from your balance." + ConsoleColors.RESET);
                    } catch (Exception e) {
                        System.out.println(ConsoleColors.RED_BOLD + "Error: " + e.getMessage() + ConsoleColors.RESET);
                    }
                }
                case 5 -> {
                    loggedIn = false;
                    System.out.println(ConsoleColors.GREEN_BOLD + "Logged out." + ConsoleColors.RESET);
                }
                default -> System.out.println(ConsoleColors.RED_BOLD + "Invalid choice. Please try again." + ConsoleColors.RESET);
            }
        }
    }
}