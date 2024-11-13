package az.edu.turing.domain.dao.impl;

import az.edu.turing.conflig.DataSourceConfig;
import az.edu.turing.domain.dao.FlightDao;
import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.exception.DatabaseException;

import java.sql.*;
import java.util.*;

public class FlightDatabaseDao extends FlightDao {

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS flights (" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "departureDate TIMESTAMP NOT NULL, " +
                    "destination VARCHAR(255) NOT NULL, " +
                    "origin VARCHAR(255) NOT NULL, " +
                    "totalSeats INT NOT NULL, " +
                    "availabilitySeats INT NOT NULL" +
                    ");";


    public static void createTableFlights(Connection con) throws SQLException {
        Statement statement = con.createStatement();
        statement.execute(CREATE_TABLE_SQL);
        System.out.println("Flights table created");


    }


    @Override
    public Optional<FlightEntity> getById(Long id) throws DatabaseException {
        String sql = "SELECT * FROM flights WHERE id = ?";
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                FlightEntity flight = new FlightEntity(
                        resultSet.getLong("id"),
                        resultSet.getTimestamp("departureDate").toLocalDateTime(),
                        resultSet.getString("destination"),
                        resultSet.getString("origin"),
                        resultSet.getInt("totalSeats"),
                        resultSet.getInt("availabilitySeats")
                );
                return Optional.of(flight);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving flight by ID", e);
        }
        return Optional.empty();


    }

    @Override
    public Collection<FlightEntity> getAll() throws DatabaseException {
        Set<FlightEntity> flights = new HashSet<>();
        String sql = "SELECT * FROM flights";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                FlightEntity flight = new FlightEntity(
                        resultSet.getLong("id"),
                        resultSet.getTimestamp("departureDate").toLocalDateTime(),
                        resultSet.getString("destination"),
                        resultSet.getString("origin"),
                        resultSet.getInt("totalSeats"),
                        resultSet.getInt("availabilitySeats")
                );
                flights.add(flight);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving all flights", e);
        }
        return flights;
    }

    @Override
    public FlightEntity save(FlightEntity entity) throws DatabaseException {
        String sql = "INSERT INTO flights (departureDate, destination, origin, totalSeats, availabilitySeats) VALUES (?, ?, ?, ?, ?)";

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
            throw new DatabaseException("Error saving flight", e);
        }
        return entity;
    }

    @Override
    public FlightEntity update(FlightEntity entity) throws DatabaseException {
        String sql = "UPDATE flights SET departureDate = ?, destination = ?, origin = ?, totalSeats = ?, availabilitySeats = ? WHERE id = ?";

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
            throw new DatabaseException("Error updating flight", e);
        }
        return entity;
    }

    @Override
    public void delete(Long id) throws DatabaseException {
        String sql = "DELETE FROM flights WHERE id = ?";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("Flight deleted");
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting flight", e);
        }

    }

    @Override
    public boolean existById(long id) throws DatabaseException {
        return getById(id).isPresent();
    }

}
