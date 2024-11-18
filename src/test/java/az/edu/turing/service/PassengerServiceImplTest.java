package az.edu.turing.service;

import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.AlreadyExistsException;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.mapper.PassengerMapper;
import az.edu.turing.model.dto.PassengerDto;
import az.edu.turing.model.dto.request.CreatePassengerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PassengerServiceImplTest {

    private PassengerDao passengerDao;
    private PassengerMapper passengerMapper;
    private PassengerServiceImpl passengerService;

    @BeforeEach
    void setUp() {

        passengerDao = mock(PassengerDao.class);
        passengerMapper = mock(PassengerMapper.class);
        passengerService = new PassengerServiceImpl(passengerDao, passengerMapper);
    }

    @Test
    void testGetAllPassengers() throws DatabaseException {

        PassengerEntity passengerEntity = new PassengerEntity(1L, "John", "Doe");
        PassengerDto passengerDto = new PassengerDto(1L, "John", "Doe");

        when(passengerDao.getAll()).thenReturn(Collections.singletonList(passengerEntity));
        when(passengerMapper.toDto(passengerEntity)).thenReturn(passengerDto);

        List<PassengerDto> result = passengerService.getAllPassengers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(passengerDto, result.get(0));
        verify(passengerDao, times(1)).getAll();
    }

    @Test
    void testCreatePassenger() throws DatabaseException {

        CreatePassengerRequest request = new CreatePassengerRequest(1L, "John", "Doe");
        PassengerEntity savedPassengerEntity = new PassengerEntity(1L, "John", "Doe");
        PassengerDto savedPassengerDto = new PassengerDto(1L, "John", "Doe");

        when(passengerDao.existsById(request.getId())).thenReturn(false);
        when(passengerDao.save(any(PassengerEntity.class))).thenReturn(savedPassengerEntity);
        when(passengerMapper.toDto(savedPassengerEntity)).thenReturn(savedPassengerDto);

        PassengerDto result = passengerService.createPassenger(request);

        assertNotNull(result);
        assertEquals(savedPassengerDto, result);
        verify(passengerDao, times(1)).existsById(request.getId());
        verify(passengerDao, times(1)).save(any(PassengerEntity.class));
    }

    @Test
    void testCreatePassengerThrowsAlreadyExistsException() throws DatabaseException {

        CreatePassengerRequest request = new CreatePassengerRequest(1L, "John", "Doe");

        when(passengerDao.existsById(request.getId())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> passengerService.createPassenger(request));
        verify(passengerDao, times(1)).existsById(request.getId());
    }

    @Test
    void testFindById() throws DatabaseException {

        long id = 1L;
        PassengerEntity passengerEntity = new PassengerEntity(id, "John", "Doe");
        PassengerDto passengerDto = new PassengerDto(id, "John", "Doe");

        when(passengerDao.getById(id)).thenReturn(Optional.of(passengerEntity));
        when(passengerMapper.toDto(passengerEntity)).thenReturn(passengerDto);

        PassengerDto result = passengerService.findById(id);

        assertNotNull(result);
        assertEquals(passengerDto, result);
        verify(passengerDao, times(1)).getById(id);
    }

    @Test
    void testFindByIdThrowsNotFoundException() throws DatabaseException {

        long id = 1L;

        when(passengerDao.getById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> passengerService.findById(id));
        verify(passengerDao, times(1)).getById(id);
    }

    @Test
    void testExistsById() throws DatabaseException {

        long id = 1L;

        when(passengerDao.existsById(id)).thenReturn(true);

        boolean result = passengerService.existsById(id);

        assertTrue(result);
        verify(passengerDao, times(1)).existsById(id);
    }
}