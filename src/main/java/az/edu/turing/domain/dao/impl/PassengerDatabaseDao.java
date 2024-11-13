package az.edu.turing.domain.dao.impl;

import az.edu.turing.config.DataSourceConfig;
import az.edu.turing.domain.dao.PassengerDao;
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.DatabaseException;

import java.sql.*;
import java.util.*;

public class PassengerDatabaseDao extends PassengerDao {

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS passengers (" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "firstName VARCHAR(255) NOT NULL, " +
                    "lastName VARCHAR(255) NOT NULL" +
                    ");";


    public static void createTablePassengers(Connection con) throws SQLException {
        Statement statement = con.createStatement();
        statement.execute(CREATE_TABLE_SQL);
        System.out.println("Passengers table created");


    }


    @Override
    public Optional<PassengerEntity> getById(Long id) throws DatabaseException {
        String sql = "SELECT * FROM passengers WHERE id = ?";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                PassengerEntity passenger = new PassengerEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName")
                );
                return Optional.of(passenger);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving passenger by ID", e);
        }
        return Optional.empty();

    }


    @Override
    public Collection<PassengerEntity> getAll() throws DatabaseException {
        Set<PassengerEntity> passengerEntities = new HashSet<>();
        String sql = "SELECT * FROM passengers";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PassengerEntity passengerEntity = new PassengerEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("LastName")
                );

                passengerEntities.add(passengerEntity);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving all passengers", e);
        }
        return passengerEntities;
    }


    @Override
    public PassengerEntity save(PassengerEntity entity) throws DatabaseException {
        String sql = "INSERT INTO passengers (firstName, lastName) VALUES (?, ?)";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            System.out.println(statement.executeUpdate() + "Passengers saved");

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error saving passenger", e);
        }
        return entity;

    }


    @Override
    public PassengerEntity update(PassengerEntity entity) throws DatabaseException {
        String sql = "UPDATE passengers SET firstName = ?, lastName = ? WHERE id = ?";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setLong(3, entity.getId());
            System.out.println(preparedStatement.executeUpdate() + "Passengers updated");

        } catch (SQLException e) {
            throw new DatabaseException("Error updating passenger", e);
        }
        return entity;
    }

    @Override
    public void delete(Long id) throws DatabaseException {
        String sql = "DELETE FROM passengers WHERE id = ?";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement.executeUpdate() + " deleted");

        } catch (SQLException e) {
            throw new DatabaseException("Error deleting passenger", e);
        }

    }

    @Override
    public boolean existById(long id) throws DatabaseException {
        Optional<PassengerEntity> entity = getById(id);
        return entity.isPresent();
    }

}
