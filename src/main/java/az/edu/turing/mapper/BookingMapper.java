package az.edu.turing.mapper;

import az.edu.turing.domain.entities.BookingEntity;
import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.model.dto.BookingDto;

public class BookingMapper implements EntityMapper<BookingEntity, BookingDto> {

    @Override
    public BookingEntity toEntity(BookingDto bookingDto) {
        FlightEntity flightEntity = new FlightEntity();
        flightEntity.setId(bookingDto.getFlightId());

        return new BookingEntity(
                bookingDto.getId(),
                flightEntity,
                bookingDto.getPassengers(),
                bookingDto.isCancelled()

        );
    }

    @Override
    public BookingDto toDto(BookingEntity bookingEntity) {
        return new BookingDto(
                bookingEntity.getId(),
                bookingEntity.getFlightId(),
                bookingEntity.getPassengers(),
                bookingEntity.isCancelled()
        );
    }
}
