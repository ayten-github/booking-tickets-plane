package az.edu.turing.mapper;

import az.edu.turing.domain.entity.PassengerEntity;
import az.edu.turing.model.dto.PassengerDto;

public class PassengerMapper implements EntityMapper<PassengerEntity, PassengerDto> {

    @Override
    public PassengerEntity toEntity(PassengerDto passengerDto) {
        return null;
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
