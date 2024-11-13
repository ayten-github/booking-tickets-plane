package az.edu.turing.conflig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5436/postgres";
        String user = "app";
        String password = "1234";
        return DriverManager.getConnection(url, user, password);
    }
}
