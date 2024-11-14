package az.edu.turing.model.dto.request;

import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.domain.entities.PassengerEntity;

import java.util.List;

public class CreateBookingRequest {

    private final FlightEntity flightId;
    private final List<PassengerEntity> passengers;
    private final boolean isCancelled;

    public CreateBookingRequest(FlightEntity flightId, List<PassengerEntity> passengers, boolean isCancelled) {
        this.flightId = flightId;
        this.passengers = passengers;
        this.isCancelled = isCancelled;
    }

    public FlightEntity getFlightId() {
        return flightId;
    }

    public List<PassengerEntity> getPassengers() {
        return passengers;
    }

    public boolean isCancelled() {
        return isCancelled;
    }
}
