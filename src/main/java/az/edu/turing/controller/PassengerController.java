package az.edu.turing.controller;

import az.edu.turing.exception.DatabaseException;
import az.edu.turing.model.dto.PassengerDto;
import az.edu.turing.model.dto.request.CreatePassengerRequest;
import az.edu.turing.service.PassengerService;

public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    public PassengerDto create(CreatePassengerRequest request) throws DatabaseException {
        return passengerService.createPassenger(request);
    }

    public PassengerDto findById(long id) throws DatabaseException {
        return passengerService.findById(id);
    }
}
