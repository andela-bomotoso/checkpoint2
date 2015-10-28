package checkpoint.andela.parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class DatabaseManager {
    Properties connectionProperties = new Properties();
    private String databaseUrl;
    private String username;
    private String password;
    private String databaseName;
    private String query;

    Statement statement;
    Connection connection;

    public boolean connectionEstablished;

    public DatabaseManager(String databaseUrl,String username,String password) {
        this.databaseUrl = databaseUrl;
        this.username = username;
        this.password = password;
        connectionProperties.put("user",this.username);
        connectionProperties.put("password",this.password);
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

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Connection establishConnection() {
        try {
            connection = DriverManager.getConnection(databaseUrl, connectionProperties);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }

    public void createDatabase(String databaseName) {
        establishConnection();
        try {
            statement = connection.createStatement();

        String sqlCreateDbQuery =  "CREATE DATABASE "+databaseName;
        statement.executeUpdate(sqlCreateDbQuery);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public void createTable(String Tablename) {
       try {
           statement = connection.createStatement();
           statement.executeLargeUpdate(getQuery());
       }
       catch (SQLException sqlException) {
           sqlException.printStackTrace();
       }


    }

}
