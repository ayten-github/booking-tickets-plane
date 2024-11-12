package org.example.model.dto;

import java.util.Date;
import java.util.List;

public class FlightDto {

    private long id;
    private Date departureDate;
    private String destination;
    private List<Boolean> seatAvailability;

    public FlightDto(long id, Date departureDate, String destination, List<Boolean> seatAvailability) {
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

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
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
