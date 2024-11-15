package az.edu.turing.controller;

import az.edu.turing.exception.DatabaseException;
import az.edu.turing.model.dto.FlightDto;
import az.edu.turing.service.FlightService;

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


}
