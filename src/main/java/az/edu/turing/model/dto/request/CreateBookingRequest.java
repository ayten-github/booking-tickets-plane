package az.edu.turing.model.dto.request;

import az.edu.turing.domain.entity.FlightEntity;
import az.edu.turing.domain.entity.PassengerEntity;

import java.util.List;

public class CreateBookingRequest {

    private final FlightEntity flightId;
    private final String passengerName;
    private final List<PassengerEntity> passengers;
    private final boolean isCancelled;

    public CreateBookingRequest(FlightEntity flightId, String passengerName, List<PassengerEntity> passengers, boolean isCancelled) {
        this.flightId = flightId;
        this.passengerName = passengerName;
        this.passengers = passengers;
        this.isCancelled = isCancelled;
    }

    public FlightEntity getFlightId() {
        return flightId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public List<PassengerEntity> getPassengers() {
        return passengers;
    }

    public boolean isCancelled() {
        return isCancelled;
    }
}
