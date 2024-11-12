package az.edu.turing.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class FlightDto {

    private long id;
    private LocalDateTime departureDate;
    private String destination;
    private List<Boolean> seatAvailability;

    public FlightDto(long id, LocalDateTime departureDate, String destination, int seatAvailability) {
        this.id = id;
        this.departureDate = departureDate;
        this.destination = destination;
        this.seatAvailability = seatAvailability;
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

    public List<Boolean> getSeatAvailability() {
        return seatAvailability;
    }

    public void setSeatAvailability(List<Boolean> seatAvailability) {
        this.seatAvailability = seatAvailability;
    }
}
