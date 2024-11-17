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

    public FlightEntity(LocalDateTime departureDate, String destination, String origin, int totalSeats, int availabilitySeats) {
        this(null, departureDate, destination, origin, totalSeats, availabilitySeats);
    }

    public FlightEntity(Long id, LocalDateTime departureDate, String destination, String origin, int totalSeats, int availabilitySeats) {
        this.id = id;
        this.departureDate = departureDate;
        this.destination = destination;
        this.origin = origin;
        this.totalSeats = totalSeats;
        this.availabilitySeats = availabilitySeats;
    }

    public FlightEntity(long flightId) {
        this.id = flightId;
    }

    private FlightEntity(Builder builder) {
        this.id = builder.id;
        this.departureDate = builder.departureDate;
        this.destination = builder.destination;
        this.origin = builder.origin;
        this.totalSeats = builder.totalSeats;
        this.availabilitySeats = builder.availabilitySeats;
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
        return String.format("{id=%d, departureDate=%s, destination=%s, from=%s, totalSeats=%d, availabilitySeats=%d}",
                id, departureDate, destination, origin, totalSeats, availabilitySeats);
    }

    @Override
    public int compareTo(FlightEntity o) {
        return Long.compare(this.id, o.id);
    }

    public static class Builder {

        private Long id;
        private LocalDateTime departureDate;
        private String destination;
        private String origin;
        private int totalSeats;
        private int availabilitySeats;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder departureDate(LocalDateTime departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        public Builder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder origin(String origin) {
            this.origin = origin;
            return this;
        }

        public Builder totalSeats(int totalSeats) {
            this.totalSeats = totalSeats;
            return this;
        }

        public Builder availabilitySeats(int availabilitySeats) {
            this.availabilitySeats = availabilitySeats;
            return this;
        }

        public FlightEntity build() {
            return new FlightEntity(this);
        }

    }
}
