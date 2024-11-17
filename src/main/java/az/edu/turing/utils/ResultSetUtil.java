package az.edu.turing.utils;

import az.edu.turing.domain.dao.impl.database.BookingDatabaseDao;
import az.edu.turing.domain.dao.impl.database.PassengerDatabaseDao;
import az.edu.turing.domain.entities.BookingEntity;
import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ResultSetUtil {

    public static PassengerEntity mapToPassengerEntity(ResultSet resultSet) throws SQLException {
        return new PassengerEntity(
                resultSet.getLong("id"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName")
        );
    }

    public static List<PassengerEntity> mapToPassengerList(ResultSet resultSet) throws SQLException {
        List<PassengerEntity> passengers = new ArrayList<>();
        while (resultSet.next()) {
            passengers.add(mapToPassengerEntity(resultSet));
        }
        return passengers;
    }


    public static BookingEntity mapToBookingEntity(ResultSet resultSet) throws SQLException, DatabaseException {
        return new BookingEntity(
                resultSet.getLong("id"),
                new FlightEntity(resultSet.getLong("flight_id")),
                BookingDatabaseDao.getPassengersByBookingId(resultSet.getLong("id")),
                resultSet.getBoolean("is_cancelled")
        );
    }

    public static FlightEntity mapRowToFlightEntity(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }

        return new FlightEntity(
                resultSet.getLong("id"),
                resultSet.getTimestamp("departureDate").toLocalDateTime(),
                resultSet.getString("destination"),
                resultSet.getString("origin"),
                resultSet.getInt("totalSeats"),
                resultSet.getInt("availabilitySeats")
        );
    }


}
