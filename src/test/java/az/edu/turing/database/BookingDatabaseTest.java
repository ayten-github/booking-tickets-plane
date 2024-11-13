//package az.edu.turing.database;
//
//import az.edu.turing.config.DataSourceConfig;
//import az.edu.turing.domain.dao.impl.BookingDatabaseDao;
//import az.edu.turing.domain.entities.BookingEntity;
//import az.edu.turing.domain.entities.FlightEntity;
//import az.edu.turing.domain.entities.PassengerEntity;
//import az.edu.turing.exception.DatabaseException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//
//import java.sql.*;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class BookingDatabaseTest {
//
//    private BookingDatabaseDao bookingDatabaseDao;
//
//    @Mock
//    private Connection connection;
//
//    @Mock
//    private PreparedStatement preparedStatement;
//
//    @Mock
//    private ResultSet resultSet;
//
//    @Mock
//    private Statement statement;
//
//    @Mock
//    private FlightEntity flightEntity;
//
//    @Mock
//    private PassengerEntity passengerEntity;
//
//    @BeforeEach
//    public void setUp() throws SQLException {
//        MockitoAnnotations.openMocks(this);
//        bookingDatabaseDao = new BookingDatabaseDao();
//
//        mockStatic(DataSourceConfig.class);
//        when(DataSourceConfig.getConnection()).thenReturn(connection);
//    }
//
//    private static final String CREATE_TABLE_SQL =
//            "CREATE TABLE IF NOT EXISTS bookings (" +
//                    "id BIGSERIAL PRIMARY KEY, " +
//                    "flight_id BIGINT REFERENCES flights(id) NOT NULL, " +
//                    "passenger_name VARCHAR(255) NOT NULL, " +
//                    "is_cancelled BOOLEAN NOT NULL" +
//                    ");";
//
//    @Test
//    public void testCreateTableBookings() throws SQLException {
//        when(connection.createStatement()).thenReturn(statement);
//
//        BookingDatabaseDao.createTableBookings(connection);
//
//        verify(statement).execute(CREATE_TABLE_SQL);
//    }
//
//    @Test
//    public void testGetById() throws SQLException, DatabaseException {
//        long bookingId = 1L;
//        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//
//        when(resultSet.next()).thenReturn(true);
//        when(resultSet.getLong("id")).thenReturn(bookingId);
//        when(resultSet.getString("passenger_name")).thenReturn("John Doe");
//        when(resultSet.getBoolean("is_cancelled")).thenReturn(false);
//        when(resultSet.getLong("flight_id")).thenReturn(1L);
//
//        List<PassengerEntity> passengers = Arrays.asList(new PassengerEntity(1L, "John", "Doe"));
//        when(bookingDatabaseDao.getById(bookingId)).thenReturn();
//
//        Optional<BookingEntity> bookingOptional = bookingDatabaseDao.getById(bookingId);
//        assertTrue(bookingOptional.isPresent());
//
//        BookingEntity booking = bookingOptional.get();
//        assertEquals(bookingId, booking.getId());
//        assertEquals("John Doe", booking.getPassengerName());
//        assertEquals(false, booking.isCancelled());
//        assertEquals(1, booking.getPassengers().size());
//
//        verify(preparedStatement).setLong(1, bookingId);
//    }
//
//    @Test
//    public void testGetAll() throws SQLException, DatabaseException {
//        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//
//        when(resultSet.next()).thenReturn(true, true, false);
//        when(resultSet.getLong("id")).thenReturn(1L, 2L);
//        when(resultSet.getString("passenger_name")).thenReturn("John Doe", "Jane Smith");
//        when(resultSet.getBoolean("is_cancelled")).thenReturn(false, true);
//        when(resultSet.getLong("flight_id")).thenReturn(1L, 2L);
//
//        List<PassengerEntity> passengers = Arrays.asList(new PassengerEntity(1L, "John", "Doe"));
//        when(bookingDatabaseDao.getById(1L)).thenReturn(passengers);
//        when(bookingDatabaseDao.getById(2L)).thenReturn(passengers);
//
//        Collection<BookingEntity> bookings = bookingDatabaseDao.getAll();
//        assertEquals(2, bookings.size());
//    }
//
//    @Test
//    public void testSave() throws SQLException, DatabaseException {
//        String sql = "INSERT INTO bookings (flight_id, passenger_name, is_cancelled) VALUES (?, ?, ?)";
//        when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
//        when(preparedStatement.executeUpdate()).thenReturn(1);
//        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
//        when(resultSet.next()).thenReturn(true);
//        when(resultSet.getLong(1)).thenReturn(1L);
//
//        BookingEntity bookingEntity = new BookingEntity(null, flightEntity, "John Doe", Collections.singletonList(passengerEntity), false);
//        BookingEntity savedBooking = bookingDatabaseDao.save(bookingEntity);
//
//        assertNotNull(savedBooking.getId());
//        assertEquals(1L, savedBooking.getId());
//        verify(preparedStatement).setLong(1, flightEntity.getId());
//        verify(preparedStatement).setString(2, "John Doe");
//        verify(preparedStatement).setBoolean(3, false);
//    }
//
//    @Test
//    public void testUpdate() throws SQLException, DatabaseException {
//        String sql = "UPDATE bookings SET flight_id = ?, passenger_name = ?, is_cancelled = ? WHERE id = ?";
//        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
//
//        BookingEntity bookingEntity = new BookingEntity(1L, flightEntity, "John Doe", Collections.singletonList(passengerEntity), false);
//        BookingEntity updatedBooking = bookingDatabaseDao.update(bookingEntity);
//
//        assertEquals("John Doe", updatedBooking.getPassengerName());
//        verify(preparedStatement).setLong(1, flightEntity.getId());
//        verify(preparedStatement).setString(2, "John Doe");
//        verify(preparedStatement).setBoolean(3, false);
//        verify(preparedStatement).setLong(4, 1L);
//    }
//
//    @Test
//    public void testDelete() throws SQLException, DatabaseException {
//        String sql = "DELETE FROM bookings WHERE id = ?";
//        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
//
//        bookingDatabaseDao.delete(1L);
//        verify(preparedStatement).setLong(1, 1L);
//    }
//
//    @Test
//    public void testExistById() throws SQLException, DatabaseException {
//        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//
//        when(resultSet.next()).thenReturn(true);
//        when(resultSet.getLong("id")).thenReturn(1L);
//
//        boolean exists = bookingDatabaseDao.existById(1L);
//        assertTrue(exists);
//
//        verify(preparedStatement).setLong(1, 1L);
//    }
//}
