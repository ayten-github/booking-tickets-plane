package az.edu.turing.service;

import az.edu.turing.exception.DatabaseException;
import az.edu.turing.model.dto.FlightDto;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {

    FlightDto createFlight(FlightDto flightDto) throws DatabaseException;

    FlightDto findFlightById(long flightId) throws DatabaseException;

    List<FlightDto> findAllFlights() throws DatabaseException;

    List<FlightDto> findFlightsToDateNumber(String to, LocalDate time,int number) throws DatabaseException;

    List<FlightDto> getFlightFromKievInADay() throws DatabaseException;

}
