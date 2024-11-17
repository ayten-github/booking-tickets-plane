package az.edu.turing.domain.dao.impl.database;

import az.edu.turing.config.DataSourceConfig;
import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.dao.impl.database.schema.DatabaseSchema;
import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.utils.ResultSetUtil;

import java.sql.*;
import java.util.*;

public class FlightDatabaseDao extends FlightDao {

    public static void createTableFlights(Connection con) throws SQLException {
        Statement statement = con.createStatement();
        statement.execute(DatabaseSchema.CREATE_FLIGHT_TABLE);
        System.out.println("Flights table created");

    }

    @Override
    public Optional<FlightEntity> getById(Long id) {
        String sql = "SELECT * FROM flights WHERE id = ?;";
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                FlightEntity flight = ResultSetUtil.mapRowToFlightEntity(resultSet);
                return Optional.of(flight);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();


    }

    @Override
    public Collection<FlightEntity> getAll() {
        Set<FlightEntity> flights = new HashSet<>();
        String sql = "SELECT * FROM flights;";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                FlightEntity flight = ResultSetUtil.mapRowToFlightEntity(resultSet);
                flights.add(flight);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return flights;
    }

    @Override
    public FlightEntity save(FlightEntity entity) throws DatabaseException {
        String sql = "INSERT INTO flights (departureDate, destination, origin, totalSeats, availabilitySeats) VALUES (?, ?, ?, ?, ?);";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (entity.getDepartureDate() != null) {
                statement.setTimestamp(1, Timestamp.valueOf(entity.getDepartureDate()));
            } else {
                statement.setNull(1, Types.TIMESTAMP);
            }
            statement.setString(2, entity.getDestination());
            statement.setString(3, entity.getOrigin());
            statement.setInt(4, entity.getTotalSeats());
            statement.setInt(5, entity.getAvailabilitySeats());

            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows inserted.");

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error saving flight");
        }
        return entity;
    }

    @Override
    public FlightEntity update(FlightEntity entity) {
        String sql = "UPDATE flights SET departureDate = ?, destination = ?, origin = ?, totalSeats = ?, availabilitySeats = ? WHERE id = ?;";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setTimestamp(1, Timestamp.valueOf(entity.getDepartureDate()));
            statement.setString(2, entity.getDestination());
            statement.setString(3, entity.getOrigin());
            statement.setInt(4, entity.getTotalSeats());
            statement.setInt(5, entity.getAvailabilitySeats());
            statement.setLong(6, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM flights WHERE id = ?;";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("Flight deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public boolean existsById(long id) throws DatabaseException {
        return getById(id).isPresent();
    }

}
