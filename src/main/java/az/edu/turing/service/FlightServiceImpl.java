package az.edu.turing.service;

import az.edu.turing.domain.dao.FlightDao;
import az.edu.turing.exception.AlreadyExistsException;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.mapper.FlightMapper;
import az.edu.turing.model.dto.FlightDto;

public class FlightServiceImpl implements FlightService {

    private final FlightDao flightDao;
    private final FlightMapper flightMapper;

    public FlightServiceImpl(FlightDao flightDao, FlightMapper flightMapper) {
        this.flightDao = flightDao;
        this.flightMapper = flightMapper;
    }

    @Override
    public FlightDto createFlight(FlightDto flightDto) throws DatabaseException {
        if (flightDao.existById(flightDto.getId())) {
            throw new AlreadyExistsException("Flight already exists with id: " + flightDto.getId());
        }

        return flightMapper.toDto(flightDao.save(flightMapper.toEntity(flightDto)));
    }

    @Override
    public FlightDto findFlightById(long flightId) throws DatabaseException {
        return flightDao.getById(flightId)
                .map(flightMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Flight with id " + flightId + " not found"));
    }

}
