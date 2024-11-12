package org.example.model.dto;

import org.example.domain.entity.PassengerEntity;

import java.util.List;

public class BookingDto {

    private long id;
    private long flightId;
    private String passengerName;
    private List<PassengerEntity> passengers;
    private boolean isCancelled;

    public BookingDto(long id, long flightId, String passengerName, List<PassengerEntity> passengers, boolean isCancelled) {
        this.id = id;
        this.flightId = flightId;
        this.passengerName = passengerName;
        this.passengers = passengers;
        this.isCancelled = isCancelled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public List<PassengerEntity> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerEntity> passengers) {
        this.passengers = passengers;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
