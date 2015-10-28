package checkpoint.andela.parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private String jdbcDriver;
    private String databaseUrl;

    private String username;
    private String password;

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void createConnection() {
        Connection connection = null;
        Statement stmt = null;
        try {
            Class.forName(this.getJdbcDriver());
        }
        catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(databaseUrl, username, password);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
