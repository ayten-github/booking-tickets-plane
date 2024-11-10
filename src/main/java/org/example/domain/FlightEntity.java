package org.example.domain;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FlightEntity implements Comparable<FlightEntity> {

    private Long id;
    private Date departureDate;
    private String destination;
    private List<Boolean> seatAvailability;

    public FlightEntity(Date departureDate, String destination, List<Boolean> seatAvailability) {
        this(null, departureDate, destination, seatAvailability);
    }

    public FlightEntity(Long id, Date departureDate, String destination, List<Boolean> seatAvailability) {
        this.id = id;
        this.departureDate = departureDate;
        this.destination = destination;
        this.seatAvailability = seatAvailability;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Boolean> getSeatAvailability() {
        return seatAvailability;
    }

    public void setSeatAvailability(List<Boolean> seatAvailability) {
        this.seatAvailability = seatAvailability;
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
        return String.format
                ("FlightEntity{id='%s', departureDate=%s, destination='%s', seatAvailability=%s}",
                        id, departureDate, destination, seatAvailability);
    }

    @Override
    public int compareTo(FlightEntity o) {
        return Long.compare(this.id, o.id);
    }
}
