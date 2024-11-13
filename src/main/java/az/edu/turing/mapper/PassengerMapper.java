package az.edu.turing.mapper;

import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.model.dto.PassengerDto;

public class PassengerMapper implements EntityMapper<PassengerEntity, PassengerDto> {

    @Override
    public PassengerEntity toEntity(PassengerDto passengerDto) {
        if (passengerDto == null) {
            return null;
        }
        return new PassengerEntity(
                passengerDto.getId(),
                passengerDto.getFirstName(),
                passengerDto.getLastName()
        );
    }

    @Override
    public PassengerDto toDto(PassengerEntity passengerEntity) {
        return new PassengerDto(
                passengerEntity.getId(),
                passengerEntity.getFirstName(),
                passengerEntity.getLastName()
        );
    }
}
