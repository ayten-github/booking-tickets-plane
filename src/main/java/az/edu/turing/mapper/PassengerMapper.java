package az.edu.turing.mapper;

import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.model.dto.PassengerDto;

public class PassengerMapper implements EntityMapper<PassengerEntity, PassengerDto> {

    @Override
    public PassengerEntity toEntity(PassengerDto passengerDto) {
        return new PassengerEntity.Builder()
                .id(passengerDto.getId())
                .firstName(passengerDto.getFirstName())
                .lastName(passengerDto.getLastName())
                .build();
    }

    @Override
    public PassengerDto toDto(PassengerEntity passengerEntity) {
        return new PassengerDto.Builder()
                .id(passengerEntity.getId())
                .firstName(passengerEntity.getFirstName())
                .lastName(passengerEntity.getLastName())
                .build();
    }
}
