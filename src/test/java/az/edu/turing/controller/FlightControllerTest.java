package az.edu.turing.controller;

import az.edu.turing.exception.DatabaseException;
import az.edu.turing.model.dto.FlightDto;
import az.edu.turing.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FlightControllerTest {

    private FlightService flightService;
    private FlightController flightController;

    @BeforeEach
    void setUp() {
        flightService = mock(FlightService.class);
        flightController = new FlightController(flightService);
    }

    @Test
    void testCreateFlight() throws DatabaseException {

        FlightDto flightDto = new FlightDto();
        when(flightService.createFlight(any(FlightDto.class))).thenReturn(flightDto);

        FlightDto result = flightController.create(flightDto);

        assertNotNull(result);
        assertEquals(flightDto, result);
        verify(flightService, times(1)).createFlight(flightDto);
    }

    @Test
    void testFindById() throws DatabaseException {

        long flightId = 1L;
        FlightDto flightDto = new FlightDto();
        when(flightService.findFlightById(flightId)).thenReturn(flightDto);

        FlightDto result = flightController.findById(flightId);

        assertNotNull(result);
        assertEquals(flightDto, result);
        verify(flightService, times(1)).findFlightById(flightId);
    }

    @Test
    void testFindAll() throws DatabaseException {

        List<FlightDto> flightList = new ArrayList<>();
        when(flightService.findAllFlights()).thenReturn(flightList);

        List<FlightDto> result = flightController.findAll();

        assertNotNull(result);
        assertEquals(flightList, result);
        verify(flightService, times(1)).findAllFlights();
    }

    @Test
    void testFindFlightsToDateNumber() throws DatabaseException {

        String to = "New York";
        LocalDate date = LocalDate.now();
        int number = 5;
        List<FlightDto> flightList = new ArrayList<>();
        when(flightService.findFlightsToDateNumber(to, date, number)).thenReturn(flightList);

        List<FlightDto> result = flightController.findFlightsToDateNumber(to, date, number);

        assertNotNull(result);
        assertEquals(flightList, result);
        verify(flightService, times(1)).findFlightsToDateNumber(to, date, number);
    }

    @Test
    void testFindFlightsFromKievInADay() throws DatabaseException {

        List<FlightDto> flightList = new ArrayList<>();
        when(flightService.getFlightFromKievInADay()).thenReturn(flightList);

        List<FlightDto> result = flightController.findFlightsFromKievInADay();

        assertNotNull(result);
        assertEquals(flightList, result);
        verify(flightService, times(1)).getFlightFromKievInADay();
    }

    @Test
    void testCreateFlightThrowsDatabaseException() throws DatabaseException {

        FlightDto flightDto = new FlightDto();
        when(flightService.createFlight(any(FlightDto.class))).thenThrow(new DatabaseException("Database error"));

        assertThrows(DatabaseException.class, () -> flightController.create(flightDto));
        verify(flightService, times(1)).createFlight(flightDto);
    }

}