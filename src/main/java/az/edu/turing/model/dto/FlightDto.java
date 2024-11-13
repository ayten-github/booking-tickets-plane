package az.edu.turing.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class FlightDto {

    private long id;
    private LocalDateTime departureDate;
    private String destination;
    private String from;
    private int totalSeats;
    private int availabilitySeats;

    public FlightDto(long id, LocalDateTime departureDate, String destination, String from, int totalSeats, int availabilitySeats) {
        this.id = id;
        this.departureDate = departureDate;
        this.destination = destination;
        this.from = from;
        this.totalSeats = totalSeats;
        this.availabilitySeats = availabilitySeats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailabilitySeats() {
        return availabilitySeats;
    }

    public void setAvailabilitySeats(int availabilitySeats) {
        this.availabilitySeats = availabilitySeats;
    }
}
