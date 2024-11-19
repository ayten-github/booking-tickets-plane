package az.edu.turing.database;

import az.edu.turing.config.DataSourceConfig;
import az.edu.turing.domain.dao.impl.database.PassengerDatabaseDao;
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.DatabaseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PassengerDatabaseTest {

    private PassengerDatabaseDao passengerDatabaseDao;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() throws SQLException {

        MockitoAnnotations.openMocks(this);
        passengerDatabaseDao = new PassengerDatabaseDao();

        mockStatic(DataSourceConfig.class);
        when(DataSourceConfig.getConnection()).thenReturn(connection);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Close mocks after each test
        Mockito.framework().clearInlineMocks();
    }

    @Test
    public void testGetById() throws SQLException, DatabaseException {
        String sql = "SELECT * FROM passengers WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("firstName")).thenReturn("John");
        when(resultSet.getString("lastName")).thenReturn("Doe");

        Optional<PassengerEntity> passenger = passengerDatabaseDao.getById(1L);
        assertTrue(passenger.isPresent());
        assertEquals(1L, passenger.get().getId());
        assertEquals("John", passenger.get().getFirstName());
        assertEquals("Doe", passenger.get().getLastName());

        verify(preparedStatement).setLong(1, 1L);
    }

    @Test
    public void testGetAll() throws SQLException, DatabaseException {
        String sql = "SELECT * FROM passengers";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getLong("id")).thenReturn(1L, 2L);
        when(resultSet.getString("firstName")).thenReturn("John", "Jane");
        when(resultSet.getString("lastName")).thenReturn("Doe", "Smith");

        Set<PassengerEntity> passengers = (Set<PassengerEntity>) passengerDatabaseDao.getAll();
        assertEquals(2, passengers.size());
    }

    @Test
    public void testSave() throws SQLException, DatabaseException {
        String sql = "INSERT INTO passengers (firstName, lastName) VALUES (?, ?)";
        when(connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        PassengerEntity passenger = new PassengerEntity(null, "John", "Doe");
        PassengerEntity savedPassenger = passengerDatabaseDao.save(passenger);

        assertNotNull(savedPassenger.getId());
        assertEquals(1L, savedPassenger.getId());
        verify(preparedStatement).setString(1, "John");
        verify(preparedStatement).setString(2, "Doe");
    }

    @Test
    public void testUpdate() throws SQLException, DatabaseException {
        String sql = "UPDATE passengers SET firstName = ?, lastName = ? WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        PassengerEntity passenger = new PassengerEntity(1L, "John", "Smith");
        PassengerEntity updatedPassenger = passengerDatabaseDao.update(passenger);

        assertEquals("Smith", updatedPassenger.getLastName());
        verify(preparedStatement).setString(1, "John");
        verify(preparedStatement).setString(2, "Smith");
        verify(preparedStatement).setLong(3, 1L);
    }

    @Test
    public void testDelete() throws SQLException, DatabaseException {
        String sql = "DELETE FROM passengers WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        passengerDatabaseDao.delete(1L);
        verify(preparedStatement).setLong(1, 1L);
    }

    @Test
    public void testExistById() throws SQLException, DatabaseException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);

        String sql = "SELECT * FROM passengers WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        assertTrue(passengerDatabaseDao.existsById(1L));
        verify(preparedStatement).setLong(1, 1L);
    }
}
