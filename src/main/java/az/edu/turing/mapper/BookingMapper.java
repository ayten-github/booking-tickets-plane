package az.edu.turing.mapper;

import az.edu.turing.domain.entity.BookingEntity;
import az.edu.turing.model.dto.BookingDto;

public class BookingMapper implements EntityMapper<BookingEntity, BookingDto> {
    @Override
    public BookingEntity toEntity(BookingDto bookingDto) {
        return null;
    }

    @Override
    public BookingDto toDto(BookingEntity bookingEntity) {
        return new BookingDto(
                bookingEntity.getId(),
                bookingEntity.getFlightId(),
                bookingEntity.getPassengers(),
                bookingEntity.getPassengerName(),
                bookingEntity.isCancelled()
        );
    }
}
