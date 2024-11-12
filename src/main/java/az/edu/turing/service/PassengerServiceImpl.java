package az.edu.turing.service;

import az.edu.turing.domain.dao.PassengerDao;
import az.edu.turing.domain.entity.PassengerEntity;
import az.edu.turing.exception.AlreadyExistsException;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.mapper.PassengerMapper;
import az.edu.turing.model.dto.PassengerDto;
import az.edu.turing.model.dto.request.CreatePassengerRequest;

public class PassengerServiceImpl implements PassengerService {

    private final PassengerDao passengerDao;
    private final PassengerMapper passengerMapper;

    public PassengerServiceImpl(PassengerDao passengerDao, PassengerMapper passengerMapper) {
        this.passengerDao = passengerDao;
        this.passengerMapper = passengerMapper;
    }

    @Override
    public PassengerDto createPassenger(CreatePassengerRequest request) {
        final String login = request.getLogin();

        if(passengerDao.existByLogin(login)) {
            throw new AlreadyExistsException("login already exists");
        }
        return passengerMapper.toDto(passengerDao.save(new PassengerEntity(
                request.getLogin(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword()
        )));
    }

    @Override
    public PassengerDto findById(long id) {
        return passengerDao.getById(id)
                .map(passengerMapper::toDto)
                .orElseThrow(() -> new NotFoundException("login" + id + " not found"));

    }
}
