package az.edu.turing.mapper;

import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.model.dto.FlightDto;

public class FlightMapper implements EntityMapper<FlightEntity, FlightDto> {

    @Override
    public FlightEntity toEntity(FlightDto flightDto) {
        if (flightDto == null) {
            return null;
        }
        return new FlightEntity(
                flightDto.getId(),
                flightDto.getDepartureDate(),
                flightDto.getDestination(),
                flightDto.getFrom(),
                flightDto.getTotalSeats(),
                flightDto.getAvailabilitySeats()
        );
    }

    @Override
    public FlightDto toDto(FlightEntity flightEntity) {
        return new FlightDto(
                flightEntity.getId(),
                flightEntity.getDepartureDate(),
                flightEntity.getDestination(),
                flightEntity.getOrigin(),
                flightEntity.getTotalSeats(),
                flightEntity.getAvailabilitySeats()
        );
    }

}
