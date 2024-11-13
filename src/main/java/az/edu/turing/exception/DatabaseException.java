package az.edu.turing.exception;

import java.sql.SQLException;

public class DatabaseException extends Exception {
    public DatabaseException(String message, SQLException e) {
        super(message);
    }
}
