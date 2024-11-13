package az.edu.turing.service;

import az.edu.turing.model.dto.PassengerDto;
import az.edu.turing.model.dto.request.CreatePassengerRequest;

public interface PassengerService {

    PassengerDto createPassenger(CreatePassengerRequest request);

    PassengerDto findById(long id);
    boolean existById(long id);
}
