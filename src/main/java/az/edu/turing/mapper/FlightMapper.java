package az.edu.turing.mapper;

import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.model.dto.FlightDto;

public class FlightMapper implements EntityMapper<FlightEntity, FlightDto> {

    @Override
    public FlightEntity toEntity(FlightDto flightDto) {
        return new FlightEntity.Builder()
                .id(flightDto.getId())
                .departureDate(flightDto.getDepartureDate())
                .destination(flightDto.getDestination())
                .origin(flightDto.getOrigin())
                .totalSeats(flightDto.getTotalSeats())
                .availabilitySeats(flightDto.getAvailabilitySeats())
                .build();
    }

    @Override
    public FlightDto toDto(FlightEntity flightEntity) {
        return new FlightDto.Builder()
                .id(flightEntity.getId())
                .departureDate(flightEntity.getDepartureDate())
                .destination(flightEntity.getDestination())
                .origin(flightEntity.getOrigin())
                .totalSeats(flightEntity.getTotalSeats())
                .availabilitySeats(flightEntity.getAvailabilitySeats())
                .build();
    }

}
