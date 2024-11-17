package az.edu.turing.controller;

import az.edu.turing.exception.DatabaseException;
import az.edu.turing.model.dto.FlightDto;
import az.edu.turing.service.FlightService;

import java.time.LocalDate;
import java.util.List;

public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    public FlightDto create(FlightDto request) throws DatabaseException {
        return flightService.createFlight(request);
    }

    public FlightDto findById(long id) throws DatabaseException {
        return flightService.findFlightById(id);
    }

    public List<FlightDto> findAll() throws DatabaseException {
        return flightService.findAllFlights();
    }

    public List<FlightDto> findFlightsToDateNumber(String to, LocalDate date, int number) throws DatabaseException {
        return flightService.findFlightsToDateNumber(to, date, number);
    }

    public List<FlightDto> findFlightsFromKievInADay() throws DatabaseException {
        return flightService.getFlightFromKievInADay();
    }
}
