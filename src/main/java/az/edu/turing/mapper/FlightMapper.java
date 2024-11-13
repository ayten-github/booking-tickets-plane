package az.edu.turing.mapper;

import az.edu.turing.domain.dao.FlightDao;
import az.edu.turing.domain.entity.FlightEntity;
import az.edu.turing.model.dto.FlightDto;

public class FlightMapper implements EntityMapper<FlightEntity, FlightDto> {

    @Override
    public FlightEntity toEntity(FlightDto flightDto) {
        return null;
    }

    @Override
    public FlightDto toDto(FlightEntity flightEntity) {
        return new FlightDto(
                flightEntity.getId(),
                flightEntity.getDepartureDate(),
                flightEntity.getDestination(),
                flightEntity.getAvailableSeats()
        );
    }

}
