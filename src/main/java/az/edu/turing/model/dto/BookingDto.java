package az.edu.turing.model.dto;

import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.domain.entities.PassengerEntity;

import java.util.List;

public class BookingDto {

    private long id;
    private Long flightId;
    private List<PassengerEntity> passengers;
    private boolean isCancelled;

    public BookingDto(long id, Long flightId, List<PassengerEntity> passengers, boolean isCancelled) {
        this.id = id;
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
