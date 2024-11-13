package az.edu.turing.service;

import az.edu.turing.model.dto.FlightDto;

public interface FlightService {

    FlightDto createFlight(FlightDto flightDto);

    FlightDto findFlightById(int flightId);
}
