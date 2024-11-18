package az.edu.turing.service;


import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.entities.BookingEntity;
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.AlreadyExistsException;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.mapper.BookingMapper;
import az.edu.turing.model.dto.BookingDto;
import az.edu.turing.model.dto.FlightDto;

import java.util.List;
import java.util.stream.Collectors;


public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;
    private final BookingMapper bookingMapper;

    private FlightService flightService;


    public BookingServiceImpl(BookingDao bookingDao, BookingMapper bookingMapper) {
        this.bookingDao = bookingDao;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public BookingDto createBooking(BookingDto bookingDto) throws DatabaseException {
        if (bookingDao.existsById(bookingDto.getId())) {
            throw new AlreadyExistsException("Booking already exists with id: " + bookingDto.getId());
        }

        return bookingMapper.toDto(bookingDao.save(bookingMapper.toEntity(bookingDto)));
    }

    @Override
    public BookingDto findBookingById(long id) throws DatabaseException {
        return bookingDao.getById(id)
                .map(bookingMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Booking with id " + id + " not found"));
    }

    @Override
    public List<BookingDto> findAllBookings() throws DatabaseException {
        return bookingDao.getAll().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDto getBookingDetail(long id) throws DatabaseException {
        return bookingDao.getById(id).map(bookingMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Booking with id " + id + " not found"));
    }

    @Override
    public BookingDto findNameSurname(String fullName) throws DatabaseException {
        return bookingDao.getAll().stream()
                .filter(booking -> {
                    String storedFullName = booking.getPassenger().getFullName().trim();
                    String inputFullName = fullName.trim();
                    return storedFullName.equalsIgnoreCase(inputFullName);
                })
                .map(bookingMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Passenger with full name " + fullName + " not found"));
    }


    public BookingDto createBooking(long flightId, String fullName) throws DatabaseException {
        FlightDto flight = flightService.getFlightById(flightId);

        if (flight == null) {
            throw new NotFoundException("Flight with ID " + flightId + " not found.");
        }

        PassengerEntity passenger = new PassengerEntity();
        String fullName1 = passenger.getFullName();

        BookingEntity booking = new BookingEntity(flight, fullName1);
        bookingDao.save(booking);
        return bookingMapper.toDto(booking);

    }

}
