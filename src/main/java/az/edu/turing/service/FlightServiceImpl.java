package az.edu.turing.service;

import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.exception.AlreadyExistsException;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.mapper.FlightMapper;
import az.edu.turing.model.dto.FlightDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FlightServiceImpl implements FlightService {


    private final FlightDao flightDao;
    private final FlightMapper flightMapper;

    public FlightServiceImpl(FlightDao flightDao, FlightMapper flightMapper) {
        this.flightDao = flightDao;
        this.flightMapper = flightMapper;
    }

    @Override
    public FlightDto createFlight(FlightDto flightDto) throws DatabaseException {
        if (flightDao.existsById(flightDto.getId())) {
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

    @Override
    public List<FlightDto> findAllFlights() throws DatabaseException {
        return flightDao.getAll().stream().map(flightMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightDto> findFlightsToDateNumber(String to, LocalDate time, int number) throws DatabaseException {
        return flightDao.getAll().stream().
                filter(f->f.getDestination().equalsIgnoreCase(to))
                .filter(f->f.getDepartureDate().toLocalDate().equals(time))
                .filter(f->f.getAvailabilitySeats()>=number)
                .map(flightMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightDto> getFlightFromKievInADay() throws DatabaseException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twentyFourHoursLater = now.plusHours(24);
        return flightDao.getAll().stream().filter(f -> f.getOrigin().equals("Kiev") &&
                f.getDepartureDate().isAfter(now) &&
                f.getDepartureDate().isBefore(twentyFourHoursLater)).map(flightMapper::toDto).
                collect(Collectors.toList());
    }
}



