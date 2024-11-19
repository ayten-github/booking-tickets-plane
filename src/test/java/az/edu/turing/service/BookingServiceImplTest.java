package az.edu.turing.service;

import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.entities.BookingEntity;
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.AlreadyExistsException;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.mapper.BookingMapper;
import az.edu.turing.model.dto.BookingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookingServiceImplTest {

    private BookingDao bookingDao;
    private BookingMapper bookingMapper;
    private FlightService flightService;
    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {

        bookingDao = mock(BookingDao.class);
        bookingMapper = mock(BookingMapper.class);
        flightService = mock(FlightService.class);
        bookingService = new BookingServiceImpl(bookingDao, bookingMapper);
    }

    @Test
    void testCreateBooking() throws DatabaseException {

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        BookingEntity bookingEntity = new BookingEntity();

        when(bookingDao.existsById(bookingDto.getId())).thenReturn(false);
        when(bookingMapper.toEntity(bookingDto)).thenReturn(bookingEntity);
        when(bookingDao.save(bookingEntity)).thenReturn(bookingEntity);
        when(bookingMapper.toDto(bookingEntity)).thenReturn(bookingDto);

        BookingDto result = bookingService.createBooking(bookingDto);

        assertNotNull(result);
        assertEquals(bookingDto, result);
        verify(bookingDao, times(1)).existsById(bookingDto.getId());
        verify(bookingDao, times(1)).save(bookingEntity);
    }

    @Test
    void testCreateBookingThrowsAlreadyExistsException() throws DatabaseException {

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);

        when(bookingDao.existsById(bookingDto.getId())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> bookingService.createBooking(bookingDto));
        verify(bookingDao, times(1)).existsById(bookingDto.getId());
    }

    @Test
    void testFindBookingById() throws DatabaseException {

        long bookingId = 1L;
        BookingEntity bookingEntity = new BookingEntity();
        BookingDto bookingDto = new BookingDto();

        when(bookingDao.getById(bookingId)).thenReturn(Optional.of(bookingEntity));
        when(bookingMapper.toDto(bookingEntity)).thenReturn(bookingDto);

        BookingDto result = bookingService.findBookingById(bookingId);

        assertNotNull(result);
        assertEquals(bookingDto, result);
        verify(bookingDao, times(1)).getById(bookingId);
    }

    @Test
    void testFindBookingByIdThrowsNotFoundException() throws DatabaseException {

        long bookingId = 1L;

        when(bookingDao.getById(bookingId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookingService.findBookingById(bookingId));
        verify(bookingDao, times(1)).getById(bookingId);
    }

    @Test
    void testFindAllBookings() throws DatabaseException {

        BookingEntity bookingEntity = new BookingEntity();
        BookingDto bookingDto = new BookingDto();

        when(bookingDao.getAll()).thenReturn(Collections.singletonList(bookingEntity));
        when(bookingMapper.toDto(bookingEntity)).thenReturn(bookingDto);

        List<BookingDto> result = bookingService.findAllBookings();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bookingDto, result.get(0));
        verify(bookingDao, times(1)).getAll();
    }

    @Test
    void testGetBookingDetail() throws DatabaseException {

        long bookingId = 1L;
        BookingEntity bookingEntity = new BookingEntity();
        BookingDto bookingDto = new BookingDto();

        when(bookingDao.getById(bookingId)).thenReturn(Optional.of(bookingEntity));
        when(bookingMapper.toDto(bookingEntity)).thenReturn(bookingDto);

        BookingDto result = bookingService.getBookingDetail(bookingId);

        assertNotNull(result);
        assertEquals(bookingDto, result);
        verify(bookingDao, times(1)).getById(bookingId);
    }

    @Test
    void testGetBookingDetailThrowsNotFoundException() throws DatabaseException {

        long bookingId = 1L;

        when(bookingDao.getById(bookingId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookingService.getBookingDetail(bookingId));
        verify(bookingDao, times(1)).getById(bookingId);
    }

    @Test
    void testFindNameSurname() throws DatabaseException {

        String fullName = "John Doe";
        BookingEntity bookingEntity = new BookingEntity();
        PassengerEntity passengerEntity = new PassengerEntity();
        passengerEntity.setFullName(fullName);
        bookingEntity.setPassenger(passengerEntity);
        BookingDto bookingDto = new BookingDto();

        when(bookingDao.getAll()).thenReturn(Collections.singletonList(bookingEntity));
        when(bookingMapper.toDto(bookingEntity)).thenReturn(bookingDto);

        BookingDto result = bookingService.findNameSurname(fullName);

        assertNotNull(result);
        assertEquals(bookingDto, result);
        verify(bookingDao, times(1)).getAll();
    }

    @Test
    void testFindNameSurnameThrowsNotFoundException() throws DatabaseException {

        String fullName = "Jane Doe";

        when(bookingDao.getAll()).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> bookingService.findNameSurname(fullName));
        verify(bookingDao, times(1)).getAll();
    }

}