package az.edu.turing.service;

import az.edu.turing.exception.DatabaseException;
import az.edu.turing.model.dto.PassengerDto;
import az.edu.turing.model.dto.request.CreatePassengerRequest;

public interface PassengerService {

    PassengerDto createPassenger(CreatePassengerRequest request) throws DatabaseException;

    PassengerDto findById(long id) throws DatabaseException;
    boolean existsById(long id) throws DatabaseException;
}
