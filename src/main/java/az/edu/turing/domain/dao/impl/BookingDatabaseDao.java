package az.edu.turing.domain.dao.impl;

import az.edu.turing.config.DataSourceConfig;
import az.edu.turing.domain.dao.BookingDao;
import az.edu.turing.domain.entities.BookingEntity;
import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.DatabaseException;

import java.sql.*;
import java.util.*;

public class BookingDatabaseDao extends BookingDao {

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS bookings (" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "flight_id BIGINT REFERENCES flights(id) NOT NULL, " +
                    "passenger_name VARCHAR(255) NOT NULL, " +
                    "is_cancelled BOOLEAN NOT NULL" +
                    ");";

    public static void createTableBookings(Connection con) throws SQLException {
        Statement statement = con.createStatement();
        statement.execute(CREATE_TABLE_SQL);
        System.out.println("Passengers table created");

    }


    @Override
    public Optional<BookingEntity> getById(Long id) throws DatabaseException {
        String sql = "SELECT * FROM bookings WHERE id = ?";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                BookingEntity booking = new BookingEntity(
                        resultSet.getLong("id"),
                        new FlightEntity(resultSet.getLong("flight_id")),
                        resultSet.getString("passenger_name"),
                        getPassengersByBookingId(resultSet.getLong("id")),
                        resultSet.getBoolean("is_cancelled")
                );
                return Optional.of(booking);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving booking by ID", e);
        }
        return Optional.empty();

    }

    @Override
    public Collection<BookingEntity> getAll() throws DatabaseException {
        Set<BookingEntity> bookings = new HashSet<>();
        String sql = "SELECT * FROM bookings";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                BookingEntity booking = new BookingEntity(
                        resultSet.getLong("id"),
                        new FlightEntity(resultSet.getLong("flight_id")),
                        resultSet.getString("passenger_name"),
                        getPassengersByBookingId(resultSet.getLong("id")),
                        resultSet.getBoolean("is_cancelled")
                );
                bookings.add(booking);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving all bookings", e);
        }
        return bookings;
    }

    @Override
    public BookingEntity save(BookingEntity entity) throws DatabaseException {
        String sql = "INSERT INTO bookings (flight_id, passenger_name, is_cancelled) VALUES (?, ?, ?)";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, entity.getFlightId().getId());
            statement.setString(2, entity.getPassengerName());
            statement.setBoolean(3, entity.isCancelled());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error saving booking", e);
        }
        return entity;
    }

    @Override
    public BookingEntity update(BookingEntity entity) throws DatabaseException {
        String sql = "UPDATE bookings SET flight_id = ?, passenger_name = ?, is_cancelled = ? WHERE id = ?";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, entity.getFlightId().getId());
            preparedStatement.setString(2, entity.getPassengerName());
            preparedStatement.setBoolean(3, entity.isCancelled());
            preparedStatement.setLong(4, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error updating booking", e);
        }
        return entity;
    }

    @Override
    public void delete(Long id) throws DatabaseException {
        String sql = "DELETE FROM bookings WHERE id = ?";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Booking deleted");
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting booking", e);
        }

    }

    @Override
    public boolean existById(long id) throws DatabaseException {
        String sql = "SELECT 1 FROM bookings WHERE id = ?";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();


            return resultSet.next();
        } catch (SQLException e) {
            throw new DatabaseException("Error checking if booking exists by ID", e);
        }
    }

    private List<PassengerEntity> getPassengersByBookingId(Long bookingId) throws DatabaseException {
        List<PassengerEntity> passengers = new ArrayList<>();
        String sql = "SELECT * FROM passengers WHERE booking_id = ?";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, bookingId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PassengerEntity passenger = new PassengerEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName")
                );
                passengers.add(passenger);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving passengers for booking", e);
        }
        return passengers;
    }

}
