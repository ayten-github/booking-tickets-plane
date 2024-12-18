package az.edu.turing.domain.entities;

import az.edu.turing.model.dto.BookingDto;
import az.edu.turing.model.dto.FlightDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class BookingEntity {

    private Long id;
    private PassengerEntity passenger;
    private FlightEntity flightId;
    private FlightEntity flight;
    private List<PassengerEntity> passengers;
    private boolean isCancelled;


    public BookingEntity(Long id, FlightEntity flightId, List<PassengerEntity> passengers, boolean isCancelled) {
        this.id = id;
        this.flightId = flightId;
        this.passengers = passengers;
        this.isCancelled = isCancelled;
    }

    public BookingEntity(FlightEntity flight, FlightEntity flightId, Long id, boolean isCancelled, List<PassengerEntity> passengers) {
        this.flight = flight;
        this.flightId = flightId;
        this.id = id;
        this.isCancelled = isCancelled;
        this.passengers = passengers;
    }

    public BookingEntity() {

    }

    public BookingEntity(FlightDto flight, String fullName1) {
        this.flight = new FlightEntity();
        this.flight.setId(flight.getId());
        this.flight.setDestination(flight.getDestination());
        this.flight.setDepartureDate(flight.getDepartureDate());
        this.flight.setOrigin(flight.getOrigin());
        this.flight.setTotalSeats(flight.getTotalSeats());
        this.flight.setAvailabilitySeats(flight.getAvailabilitySeats());

        PassengerEntity passenger = new PassengerEntity();
        passenger.setFullName(fullName1);

        this.passengers = new ArrayList<>();
        this.passengers.add(passenger);

        this.isCancelled = false;
    }


    public FlightEntity getFlight() {
        return flight;
    }

    public PassengerEntity getPassenger() {
        return passenger;
    }

    public String getFullName() {
        return getPassenger().getFullName();
    }


    public void setPassenger(PassengerEntity passenger) {
        this.passenger = passenger;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getFlightId() {
        return flightId != null ? flightId.getId() : null;
    }

    public void setFlightId(FlightEntity flightId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingEntity that = (BookingEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(flightId, that.flightId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flightId);
    }

    @Override
    public String toString() {
        return String.format("BookingEntity{id=%d, flightId=%d, flight=%d, passengers=%s, isCancelled=%s}",
                id, flightId, flight, passengers, isCancelled);
    }

}
