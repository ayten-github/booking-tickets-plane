package az.edu.turing.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class FlightEntity implements Comparable<FlightEntity> {

    private Long id;
    private LocalDateTime departureDate;
    private String destination;
    private String origin;
    private int totalSeats;
    private int availabilitySeats;

    public FlightEntity() {

    }

    public FlightEntity(LocalDateTime departureDate, String destination, String from, int totalSeats, int availabilitySeats) {
        this(null, departureDate, destination, from, totalSeats, availabilitySeats);
    }

    public FlightEntity(Long id, LocalDateTime departureDate, String destination, String from, int totalSeats, int availabilitySeats) {
        this.id = id;
        this.departureDate = departureDate;
        this.destination = destination;
        this.origin = from;
        this.totalSeats = totalSeats;
        this.availabilitySeats = availabilitySeats;
    }

    public FlightEntity(long flightId) {
        this.id = flightId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String from) {
        this.origin = from;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailabilitySeats() {
        return availabilitySeats;
    }

    public void setAvailabilitySeats(int availabilitySeats) {
        this.availabilitySeats = availabilitySeats;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightEntity that = (FlightEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("FlightEntity{id=%d, departureDate=%s, destination='%s', from='%s', totalSeats=%d, availabilitySeats=%d}", id, departureDate, destination, origin, totalSeats, availabilitySeats);
    }

    @Override
    public int compareTo(FlightEntity o) {
        return Long.compare(this.id, o.id);
    }
}
