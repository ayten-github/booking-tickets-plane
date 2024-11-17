package az.edu.turing.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceConfig {

    private static final String URL = System.getenv("DATABASE_URL");
    private static final String USER = System.getenv("POSTGRESQL_USER");
    private static final String PASSWORD = System.getenv("POSTGRESQL_PASSWORD");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
