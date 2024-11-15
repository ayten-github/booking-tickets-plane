package az.edu.turing.service;

import az.edu.turing.exception.DatabaseException;
import az.edu.turing.model.dto.FlightDto;

public interface FlightService {

    FlightDto createFlight(FlightDto flightDto) throws DatabaseException;

    FlightDto findFlightById(long flightId) throws DatabaseException;
}
