package az.edu.turing.database;

import az.edu.turing.config.DataSourceConfig;
import az.edu.turing.domain.dao.impl.FlightDatabaseDao;
import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.exception.DatabaseException;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FlightDatabaseTest {

    private FlightDatabaseDao flightDatabaseDao;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private Statement statement;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        flightDatabaseDao = new FlightDatabaseDao();

        mockStatic(DataSourceConfig.class);
        when(DataSourceConfig.getConnection()).thenReturn(connection);
    }

    @AfterEach
    public void tearDown() throws Exception {
        Mockito.framework().clearInlineMocks();
    }

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS flights (" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "departureDate TIMESTAMP NOT NULL, " +
                    "destination VARCHAR(255) NOT NULL, " +
                    "origin VARCHAR(255) NOT NULL, " +
                    "totalSeats INT NOT NULL, " +
                    "availabilitySeats INT NOT NULL" +
                    ");";

    @Test
    public void testCreateTableFlights() throws SQLException {
        when(connection.createStatement()).thenReturn(statement);

        FlightDatabaseDao.createTableFlights(connection);

        verify(statement).execute(CREATE_TABLE_SQL);
    }

    @Test
    public void testGetById() throws SQLException, DatabaseException {
        Long flightId = 1L;
        String sql = "SELECT * FROM flights WHERE id = ?";

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(flightId);
        when(resultSet.getTimestamp("departureDate")).thenReturn(Timestamp.valueOf("2024-11-01 10:00:00"));
        when(resultSet.getString("destination")).thenReturn("New York");
        when(resultSet.getString("origin")).thenReturn("London");
        when(resultSet.getInt("totalSeats")).thenReturn(200);
        when(resultSet.getInt("availabilitySeats")).thenReturn(50);

        Optional<FlightEntity> flight = flightDatabaseDao.getById(flightId);

        assertTrue(flight.isPresent());
        assertEquals(flightId, flight.get().getId());
        assertEquals("New York", flight.get().getDestination());
        assertEquals("London", flight.get().getOrigin());

        verify(preparedStatement).setLong(1, flightId);
    }

//    @Test
//    public void testGetAll() throws SQLException, DatabaseException {
//        String sql = "SELECT * FROM flights";
//        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//
//        when(resultSet.next()).thenReturn(true, true, false);
//        when(resultSet.getLong("id")).thenReturn(1L, 2L);
//        when(resultSet.getString("destination")).thenReturn("New York", "Paris");
//        when(resultSet.getString("origin")).thenReturn("London", "Tokyo");
//        when(resultSet.getInt("totalSeats")).thenReturn(200, 150);
//        when(resultSet.getInt("availabilitySeats")).thenReturn(50, 30);
//
//        Collection<FlightEntity> flights = flightDatabaseDao.getAll();
//
//        assertNotNull(flights);
//        assertEquals(2, flights.size());
//    }

    @Test
    public void testSave() throws SQLException, DatabaseException {
        FlightEntity flight = new FlightEntity(null, Timestamp.valueOf("2024-11-01 10:00:00").toLocalDateTime(),
                "New York", "London", 200, 50);
        String sql = "INSERT INTO flights (departureDate, destination, origin, totalSeats, availabilitySeats) VALUES (?, ?, ?, ?, ?)";

        when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        FlightEntity savedFlight = flightDatabaseDao.save(flight);

        assertNotNull(savedFlight.getId());
        assertEquals(1L, savedFlight.getId());
        verify(preparedStatement).setTimestamp(1, Timestamp.valueOf(flight.getDepartureDate()));
        verify(preparedStatement).setString(2, "New York");
        verify(preparedStatement).setString(3, "London");
    }

    @Test
    public void testUpdate() throws SQLException, DatabaseException {
        FlightEntity flight = new FlightEntity(1L, Timestamp.valueOf("2024-11-01 10:00:00").toLocalDateTime(),
                "New York", "London", 200, 50);
        String sql = "UPDATE flights SET departureDate = ?, destination = ?, origin = ?, totalSeats = ?, availabilitySeats = ? WHERE id = ?";

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        FlightEntity updatedFlight = flightDatabaseDao.update(flight);

        assertEquals(flight.getId(), updatedFlight.getId());
        verify(preparedStatement).setTimestamp(1, Timestamp.valueOf(flight.getDepartureDate()));
        verify(preparedStatement).setString(2, "New York");
        verify(preparedStatement).setString(3, "London");
        verify(preparedStatement).setInt(4, 200);
        verify(preparedStatement).setInt(5, 50);
        verify(preparedStatement).setLong(6, 1L);
    }

    @Test
    public void testDelete() throws SQLException, DatabaseException {
        Long flightId = 1L;
        String sql = "DELETE FROM flights WHERE id = ?";

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        flightDatabaseDao.delete(flightId);

        verify(preparedStatement).setLong(1, flightId);
    }

//    @Test
//    public void testExistById() throws SQLException, DatabaseException {
//        Long flightId = 1L;
//
//        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//        when(resultSet.next()).thenReturn(true);
//
//        boolean exists = flightDatabaseDao.existById(flightId);
//
//        assertTrue(exists);
//        verify(preparedStatement).setLong(1, flightId);
//    }
}

