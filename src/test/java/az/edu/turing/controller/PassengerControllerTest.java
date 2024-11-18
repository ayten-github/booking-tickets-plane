package az.edu.turing.controller;

import az.edu.turing.exception.DatabaseException;
import az.edu.turing.model.dto.PassengerDto;
import az.edu.turing.model.dto.request.CreatePassengerRequest;
import az.edu.turing.service.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PassengerControllerTest {

    private PassengerService passengerService;
    private PassengerController passengerController;

    @BeforeEach
    void setUp() {
        passengerService = mock(PassengerService.class);
        passengerController = new PassengerController(passengerService);
    }

    @Test
    void testCreatePassenger() throws DatabaseException {
        CreatePassengerRequest request = new CreatePassengerRequest(1L, "Saleh", "Samadli");
        PassengerDto passengerDto = new PassengerDto();
        when(passengerService.createPassenger(any(CreatePassengerRequest.class))).thenReturn(passengerDto);

        PassengerDto result = passengerController.create(request);

        assertNotNull(result);
        assertEquals(passengerDto, result);
        verify(passengerService, times(1)).createPassenger(request);
    }

    @Test
    void testFindById() throws DatabaseException {
        long passengerId = 1L;
        PassengerDto passengerDto = new PassengerDto();
        when(passengerService.findById(passengerId)).thenReturn(passengerDto);

        PassengerDto result = passengerController.findById(passengerId);

        assertNotNull(result);
        assertEquals(passengerDto, result);
        verify(passengerService, times(1)).findById(passengerId);
    }

    @Test
    void testCreatePassengerThrowsDatabaseException() throws DatabaseException {
        CreatePassengerRequest request = new CreatePassengerRequest(1L, "Saleh", "Samadli");
        when(passengerService.createPassenger(any(CreatePassengerRequest.class)))
                .thenThrow(new DatabaseException("Database error"));

        assertThrows(DatabaseException.class, () -> passengerController.create(request));
        verify(passengerService, times(1)).createPassenger(request);
    }

    @Test
    void testFindByIdThrowsDatabaseException() throws DatabaseException {
        long passengerId = 1L;
        when(passengerService.findById(passengerId)).thenThrow(new DatabaseException("Database error"));

        assertThrows(DatabaseException.class, () -> passengerController.findById(passengerId));
        verify(passengerService, times(1)).findById(passengerId);
    }
}