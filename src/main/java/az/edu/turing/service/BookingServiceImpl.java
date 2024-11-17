package az.edu.turing.service;

import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.exception.AlreadyExistsException;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.mapper.BookingMapper;
import az.edu.turing.model.dto.BookingDto;

public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;
    private final BookingMapper bookingMapper;

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
}
