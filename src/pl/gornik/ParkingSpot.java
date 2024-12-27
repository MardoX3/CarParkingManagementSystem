package pl.gornik;

import java.time.LocalDateTime;

public class ParkingSpot {
    private String plateNumber;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public ParkingSpot(String plateNumber, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.plateNumber = plateNumber;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public long calculateParkingCost() {
        long hours = java.time.Duration.between(startDateTime, endDateTime).toHours();
        return hours * 5;
    }

    @Override
    public String toString() {
        return "ParkingSpot for " + plateNumber + " | From: " + startDateTime + " | To: " + endDateTime + " | Cost: " + calculateParkingCost() + " PLN";
    }
}
