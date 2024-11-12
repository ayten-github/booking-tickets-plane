package az.edu.turing.model.dto.request;

import java.time.LocalDateTime;

public class CreateFlightRequest {

    private final LocalDateTime departureDate;
    private final String destination;
    private final String from;
    private final int totalSeats;
    private final int availabilitySeats;

    public CreateFlightRequest(LocalDateTime departureDate, String destination, String from, int totalSeats, int availabilitySeats) {
        this.departureDate = departureDate;
        this.destination = destination;
        this.from = from;
        this.totalSeats = totalSeats;
        this.availabilitySeats = availabilitySeats;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public String getDestination() {
        return destination;
    }

    public String getFrom() {
        return from;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailabilitySeats() {
        return availabilitySeats;
    }
}
