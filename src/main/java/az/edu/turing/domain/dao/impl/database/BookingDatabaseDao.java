package az.edu.turing.domain.dao.impl.database;

import az.edu.turing.config.DataSourceConfig;
import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.dao.impl.database.schema.DatabaseSchema;
import az.edu.turing.domain.entities.BookingEntity;
import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.utils.ResultSetUtil;

import java.sql.*;
import java.util.*;

public class BookingDatabaseDao extends BookingDao {

    public static void createTableBookings(Connection con) throws SQLException {
        Statement statement = con.createStatement();
        statement.execute(DatabaseSchema.CREATE_BOOKING_TABLE);
        System.out.println("Passengers table created");

    }


    @Override
    public Optional<BookingEntity> getById(Long id) throws DatabaseException {
        String sql = "SELECT * FROM bookings WHERE id = ?;";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                BookingEntity booking = new BookingEntity(
                        resultSet.getLong("id"),
                        new FlightEntity(resultSet.getLong("flight_id")),
                        getPassengersByBookingId(resultSet.getLong("id")),
                        resultSet.getBoolean("is_cancelled")
                );
                return Optional.of(booking);
            }
        } catch (SQLException | DatabaseException e) {
            throw new DatabaseException("Error retrieving booking by ID");
        }
        return Optional.empty();

    }

    @Override
    public Collection<BookingEntity> getAll() throws DatabaseException {
        Set<BookingEntity> bookings = new HashSet<>();
        String sql = "SELECT * FROM bookings;";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                BookingEntity booking = new BookingEntity(
                        resultSet.getLong("id"),
                        new FlightEntity(resultSet.getLong("flight_id")),
                        getPassengersByBookingId(resultSet.getLong("id")),
                        resultSet.getBoolean("is_cancelled")
                );
                bookings.add(booking);
            }
        } catch (SQLException | DatabaseException e) {
            throw new DatabaseException("Error retrieving all bookings");
        }
        return bookings;
    }

    @Override
    public BookingEntity save(BookingEntity entity) {
        String sql = "INSERT INTO bookings (flight_id, is_cancelled) VALUES (?, ?);";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, entity.getFlightId());
            statement.setBoolean(2, entity.isCancelled());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public BookingEntity update(BookingEntity entity) {
        String sql = "UPDATE bookings SET flight_id = ?, is_cancelled = ? WHERE id = ?;";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, entity.getFlightId());
            preparedStatement.setBoolean(2, entity.isCancelled());
            preparedStatement.setLong(3, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM bookings WHERE id = ?;";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Booking deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public boolean existsById(long id) throws DatabaseException {
        String sql = "SELECT 1 FROM bookings WHERE id = ?;";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();


            return resultSet.next();
        } catch (SQLException e) {
            throw new DatabaseException("Error checking if booking exists by ID");
        }
    }

    private List<PassengerEntity> getPassengersByBookingId(Long bookingId) throws DatabaseException {
        List<PassengerEntity> passengers;

        String sql = "SELECT * FROM passengers WHERE booking_id = ?;";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, bookingId);
            ResultSet resultSet = statement.executeQuery();

            passengers = ResultSetUtil.mapToPassengerList(resultSet);
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving passengers for booking");
        }
        return passengers;
    }

}
