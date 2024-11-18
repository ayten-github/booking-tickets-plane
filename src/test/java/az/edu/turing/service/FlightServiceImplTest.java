package az.edu.turing.service;

import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.exception.AlreadyExistsException;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.mapper.FlightMapper;
import az.edu.turing.model.dto.FlightDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FlightServiceImplTest {

    private FlightDao flightDao;
    private FlightMapper flightMapper;
    private FlightServiceImpl flightService;

    @BeforeEach
    void setUp() {

        flightDao = mock(FlightDao.class);
        flightMapper = mock(FlightMapper.class);
        flightService = new FlightServiceImpl(flightDao, flightMapper);
    }

    @Test
    void testCreateFlight() throws DatabaseException {

        FlightDto flightDto = new FlightDto();
        flightDto.setId(1L);
        FlightDto savedFlightDto = new FlightDto();

        when(flightDao.existsById(flightDto.getId())).thenReturn(false);
        when(flightMapper.toEntity(flightDto)).thenReturn(new FlightEntity());
        when(flightDao.save(any())).thenReturn(new FlightEntity());
        when(flightMapper.toDto(any())).thenReturn(savedFlightDto);

        FlightDto result = flightService.createFlight(flightDto);

        assertNotNull(result);
        assertEquals(savedFlightDto, result);
        verify(flightDao, times(1)).existsById(flightDto.getId());
        verify(flightDao, times(1)).save(any());
    }

    @Test
    void testCreateFlightThrowsAlreadyExistsException() throws DatabaseException {

        FlightDto flightDto = new FlightDto();
        flightDto.setId(1L);

        when(flightDao.existsById(flightDto.getId())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> flightService.createFlight(flightDto));
        verify(flightDao, times(1)).existsById(flightDto.getId());
    }

    @Test
    void testFindFlightById() throws DatabaseException {

        long flightId = 1L;
        FlightDto flightDto = new FlightDto();
        FlightEntity flightEntity = new FlightEntity();

        when(flightDao.getById(flightId)).thenReturn(Optional.of(flightEntity));
        when(flightMapper.toDto(flightEntity)).thenReturn(flightDto);

        FlightDto result = flightService.findFlightById(flightId);

        assertNotNull(result);
        assertEquals(flightDto, result);
        verify(flightDao, times(1)).getById(flightId);
    }

    @Test
    void testFindFlightByIdThrowsNotFoundException() throws DatabaseException {

        long flightId = 1L;

        when(flightDao.getById(flightId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> flightService.findFlightById(flightId));
        verify(flightDao, times(1)).getById(flightId);
    }

    @Test
    void testFindAllFlights() throws DatabaseException {

        FlightDto flightDto = new FlightDto();
        when(flightDao.getAll()).thenReturn(Collections.singletonList(new FlightEntity()));
        when(flightMapper.toDto(any())).thenReturn(flightDto);

        List<FlightDto> result = flightService.findAllFlights();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(flightDto, result.get(0));
        verify(flightDao, times(1)).getAll();
    }

    @Test
    void testFindFlightsToDateNumber() throws DatabaseException {

        String destination = "New York";
        LocalDate date = LocalDate.now();
        int number = 2;
        FlightDto flightDto = new FlightDto();
        flightDto.setDestination(destination);
        flightDto.setDepartureDate(LocalDateTime.now().plusDays(1));

        when(flightDao.getAll()).thenReturn(Collections.singletonList(new FlightEntity()));
        when(flightMapper.toDto(any())).thenReturn(flightDto);

        List<FlightDto> result = flightService.findFlightsToDateNumber(destination, date, number);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(flightDto, result.get(0));
        verify(flightDao, times(1)).getAll();
    }

    @Test
    void testGetFlightFromKievInADay() throws DatabaseException {

        FlightDto flightDto = new FlightDto();
        flightDto.setOrigin("Kiev");
        flightDto.setDepartureDate(LocalDateTime.now().plusHours(1));

        when(flightDao.getAll()).thenReturn(Collections.singletonList(new FlightEntity()));
        when(flightMapper.toDto(any())).thenReturn(flightDto);

        List<FlightDto> result = flightService.getFlightFromKievInADay();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(flightDto, result.get(0));
        verify(flightDao, times(1)).getAll();
    }

    @Test
    void testGetFlightById() throws DatabaseException {

        long flightId = 1L;
        FlightDto flightDto = new FlightDto();
        FlightEntity flightEntity = new FlightEntity();

        when(flightDao.getById(flightId)).thenReturn(Optional.of(flightEntity));
        when(flightMapper.toDto(flightEntity)).thenReturn(flightDto);

        FlightDto result = flightService.getFlightById(flightId);

        assertNotNull(result);
        assertEquals(flightDto, result);
        verify(flightDao, times(1)).getById(flightId);
    }

    @Test
    void testGetFlightByIdThrowsNotFoundException() throws DatabaseException {

        long flightId = 1L;

        when(flightDao.getById(flightId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> flightService.getFlightById(flightId));
        verify(flightDao, times(1)).getById(flightId);
    }
}