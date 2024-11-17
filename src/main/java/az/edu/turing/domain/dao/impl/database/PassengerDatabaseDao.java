package az.edu.turing.domain.dao.impl.database;

import az.edu.turing.config.DataSourceConfig;
import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.dao.impl.database.schema.DatabaseSchema;
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.utils.ResultSetUtil;

import java.sql.*;
import java.util.*;

public class PassengerDatabaseDao extends PassengerDao {

    public static void createTablePassengers(Connection con) throws SQLException {
        Statement statement = con.createStatement();
        statement.execute(DatabaseSchema.CREATE_PASSENGER_TABLE);
        System.out.println("Passengers table created");

    }

    @Override
    public Optional<PassengerEntity> getById(Long id) {
        String sql = "SELECT * FROM passengers WHERE id = ?;";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                PassengerEntity passenger = ResultSetUtil.mapToPassengerEntity(resultSet);
                return Optional.of(passenger);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Collection<PassengerEntity> getAll() {
        Set<PassengerEntity> passengerEntities = new HashSet<>();
        String sql = "SELECT * FROM passengers;";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PassengerEntity passenger = ResultSetUtil.mapToPassengerEntity(resultSet);
                passengerEntities.add(passenger);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return passengerEntities;
    }


    @Override
    public PassengerEntity save(PassengerEntity entity) throws DatabaseException {
        String sql = "INSERT INTO passengers (firstName, lastName) VALUES (?, ?);";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            System.out.println(statement.executeUpdate() + "Passenger saved");

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error saving passenger");
        }
        return entity;

    }

    @Override
    public PassengerEntity update(PassengerEntity entity) {
        String sql = "UPDATE passengers SET firstName = ?, lastName = ? WHERE id = ?;";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setLong(3, entity.getId());
            System.out.println(preparedStatement.executeUpdate() + "Passenger updated");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM passengers WHERE id = ?;";

        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);

            System.out.println(preparedStatement.executeUpdate() + " deleted");
            connection.commit();

        } catch (SQLException e) {
            try (Connection connection = DataSourceConfig.getConnection()) {
                System.out.println("Error occurred, rolling back transaction...");
                connection.rollback();
            } catch (SQLException rollbackException) {
                System.out.println("Error during rollback: " + rollbackException.getMessage());
            }
            System.out.println("Transaction failed: " + e.getMessage());

            System.out.println(e.getMessage());
        }

    }

    @Override
    public boolean existsById(long id) throws DatabaseException {
        return getById(id).isPresent();
    }

}
