package az.edu.turing.model.dto;

import az.edu.turing.domain.entities.PassengerEntity;

import java.util.List;

public class BookingDto {

    private long id;
    private String fullName;
    private Long flightId;
    private List<PassengerEntity> passengers;
    private boolean isCancelled;

    public BookingDto(long id, String fullName, Long flightId, List<PassengerEntity> passengers, boolean isCancelled) {
        this.id = id;
        this.fullName = fullName;
        this.flightId = flightId;
        this.passengers = passengers;
        this.isCancelled = isCancelled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
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

