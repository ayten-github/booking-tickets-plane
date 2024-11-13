package az.edu.turing.domain.dao.impl;

import az.edu.turing.conflig.DataSource;
import az.edu.turing.domain.dao.PassengerDao;
import az.edu.turing.domain.entities.PassengerEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PassengerInDatabase extends PassengerDao {
    private final DataSource dataSource;
    public PassengerInDatabase(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public boolean existById(long id) {
        Optional<PassengerEntity> entity=getById(id);
        return entity.isPresent();
    }

    @Override
    public Optional<PassengerEntity> getById(Long id) {
        Optional<PassengerEntity> passengerEntity = Optional.empty();
        try(Connection connection= dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("select * from passenger where id ="+id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                passengerEntity=Optional.of(new PassengerEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("second_name")
                ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return passengerEntity;
    }

    @Override
    public Collection<PassengerEntity> getAll() {
        Set<PassengerEntity> passengerEntities = new HashSet<>();
        try (Connection connection= dataSource.getConnection()){
            PreparedStatement statement= connection.prepareStatement("select *from passengers");
            ResultSet resultSet= statement.executeQuery();
            while (resultSet.next()){
                PassengerEntity passengerEntity = new PassengerEntity();
                passengerEntity.setId(resultSet.getLong("id"));
                passengerEntity.setFirstName(resultSet.getString("first_name"));
                passengerEntity.setLastName(resultSet.getString("last_name"));
                passengerEntities.add(passengerEntity);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return passengerEntities;
    }

    @Override
    public PassengerEntity save(PassengerEntity entity) {

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("insert into passangers(id,name,surname) values (?,?,?)");
            statement.setLong(1,entity.getId());
            statement.setString(2,entity.getFirstName());
            statement.setString(3, entity.getLastName());
            System.out.println(statement.executeUpdate()+"Passengers saved");
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return entity ;
    }

    @Override
    public PassengerEntity update(PassengerEntity entity) {
        try (Connection connection= dataSource.getConnection()){
            PreparedStatement preparedStatement= connection.prepareStatement("update passengers set first_name=?,last_name=? where id=?");
            preparedStatement.setString(1,entity.getFirstName());
            preparedStatement.setString(2,entity.getLastName());
            preparedStatement.setLong(3,entity.getId());
            System.out.println(preparedStatement.executeUpdate()+"Passengers updated");

        }catch (SQLException e){
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void delete(Long id) {
        Optional<PassengerEntity> entity=getById(id);
        if(entity.isPresent()){
            try(Connection connection= dataSource.getConnection()){
                PreparedStatement preparedStatement= connection.prepareStatement("delete from passengers where id=?"+id);
                preparedStatement.setLong(1,id);
                System.out.println(preparedStatement.executeUpdate()+ " deleted");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        else System.out.println("PassengerInDatabase does not exist in this id");
    }
}
