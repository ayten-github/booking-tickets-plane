package az.edu.turing.model.dto;

import az.edu.turing.domain.entities.FlightEntity;

import java.time.LocalDateTime;

public class FlightDto {

    private long id;
    private LocalDateTime departureDate;
    private String destination;
    private String origin;
    private int totalSeats;
    private int availabilitySeats;

    public FlightDto(long id, LocalDateTime departureDate, String destination, String origin, int totalSeats, int availabilitySeats) {
        this.id = id;
        this.departureDate = departureDate;
        this.destination = destination;
        this.origin = origin;
        this.totalSeats = totalSeats;
        this.availabilitySeats = availabilitySeats;
    }

    private FlightDto(Builder builder) {
        this.id = builder.id;
        this.departureDate = builder.departureDate;
        this.destination = builder.destination;
        this.origin = builder.origin;
        this.totalSeats = builder.totalSeats;
        this.availabilitySeats = builder.availabilitySeats;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

        public FlightDto build() {
            return new FlightDto(this);
        }
    }
}
