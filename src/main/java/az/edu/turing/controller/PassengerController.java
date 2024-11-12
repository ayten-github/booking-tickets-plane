package az.edu.turing.controller;

import az.edu.turing.model.dto.PassengerDto;
import az.edu.turing.model.dto.request.CreatePassengerRequest;
import az.edu.turing.service.PassengerService;

public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    public PassengerDto create(CreatePassengerRequest request) {
        return passengerService.createPassenger(request);
    }

    public PassengerDto findById(long id){
        return passengerService.findById(id);
    }

}
