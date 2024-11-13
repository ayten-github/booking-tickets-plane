package az.edu.turing.mapper;

import az.edu.turing.domain.entities.BookingEntity;
import az.edu.turing.model.dto.BookingDto;

public class BookingMapper implements EntityMapper<BookingEntity, BookingDto> {
    @Override
    public BookingEntity toEntity(BookingDto bookingDto) {

        if (bookingDto == null) {
            return null;
        }
        return new BookingEntity(
                bookingDto.getId(),
                bookingDto.getFlightId(),
                bookingDto.getPassengerName(),
                bookingDto.getPassengers(),
                bookingDto.isCancelled()

        );
    }

    @Override
    public BookingDto toDto(BookingEntity bookingEntity) {
        return new BookingDto(
                bookingEntity.getId(),
                bookingEntity.getFlightId(),
                bookingEntity.getPassengerName(),
                bookingEntity.getPassengers(),
                bookingEntity.isCancelled()
        );
    }
}
