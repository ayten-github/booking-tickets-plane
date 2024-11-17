package az.edu.turing.service;

import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.AlreadyExistsException;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.mapper.PassengerMapper;
import az.edu.turing.model.dto.PassengerDto;
import az.edu.turing.model.dto.request.CreatePassengerRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PassengerServiceImpl implements PassengerService {

    private final PassengerDao passengerDao;
    private final PassengerMapper passengerMapper;

    public PassengerServiceImpl(PassengerDao passengerDao, PassengerMapper passengerMapper) {
        this.passengerDao = passengerDao;
        this.passengerMapper = passengerMapper;
    }

    @Override
    public List<PassengerDto> getAllPassengers() throws DatabaseException {
        return passengerDao.getAll().stream().
                map(passengerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PassengerDto createPassenger(CreatePassengerRequest request) throws DatabaseException {
        if (passengerDao.existsById(request.getId())) {
            throw new AlreadyExistsException("Passenger already exists with ID: " + request.getId());
        }

        PassengerEntity save = passengerDao.save(new PassengerEntity(
                request.getId(),
                request.getFirstName(),
                request.getLastName()
        ));
        return passengerMapper.toDto(save);

    }

    @Override
    public PassengerDto findById(long id) throws DatabaseException {
        return passengerDao.getById(id)
                .map(passengerMapper::toDto)
                .orElseThrow(() -> new NotFoundException("id " + id + " not found"));

    }

    @Override
    public boolean existsById(long id) throws DatabaseException {
        return passengerDao.existsById(id);
    }


}
