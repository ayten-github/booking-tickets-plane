package az.edu.turing.model.dto.request;

import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.domain.entities.PassengerEntity;

import java.util.List;

public class CreateBookingRequest {

    private long flightId;
    private final String name;
    private final String surname;

    public CreateBookingRequest(long flightId,String name, String surname ) {
        this.flightId = flightId;
        this.name = name;
        this.surname = surname;

    }

    public long getFlightId() {
        return flightId;
    }


    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
