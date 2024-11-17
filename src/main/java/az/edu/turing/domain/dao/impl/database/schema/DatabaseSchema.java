package az.edu.turing.domain.dao.impl.database.schema;

public class DatabaseSchema {

    public static final String CREATE_FLIGHT_TABLE =
            "CREATE TABLE IF NOT EXISTS flights (" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "departureDate TIMESTAMP NOT NULL, " +
                    "destination VARCHAR(255) NOT NULL, " +
                    "origin VARCHAR(255) NOT NULL, " +
                    "totalSeats INT NOT NULL, " +
                    "availabilitySeats INT NOT NULL" +
                    ");";

    public static final String CREATE_BOOKING_TABLE =
            "CREATE TABLE IF NOT EXISTS bookings (" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "flight_id BIGINT REFERENCES flights(id) NOT NULL, " +
                    "is_cancelled BOOLEAN NOT NULL" +
                    ");";

    public static final String CREATE_PASSENGER_TABLE =
            "CREATE TABLE IF NOT EXISTS passengers (" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "firstName VARCHAR(255) NOT NULL, " +
                    "lastName VARCHAR(255) NOT NULL" +
                    ");";
}
