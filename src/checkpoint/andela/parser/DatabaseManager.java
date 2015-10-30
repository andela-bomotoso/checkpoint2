package checkpoint.andela.parser;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class DatabaseManager {
    Properties connectionProperties = new Properties();
    private String databaseUrl;
    private String username;
    private String password;
    private String databaseName;


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

    public Connection establishConnection() {
        try {
            connection = DriverManager.getConnection(databaseUrl, connectionProperties);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }

    public boolean databaseAlreadyExist(String dbName){

        try{
           establishConnection();

            ResultSet resultSet = connection.getMetaData().getCatalogs();

            while (resultSet.next()) {

                String databaseName = resultSet.getString(1);
                if(databaseName.equals(dbName)){
                    return true;
                }
            }
            resultSet.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public void createDatabase(String databaseName) {
        establishConnection();
        if( databaseAlreadyExist(databaseName)) {
            String dropDatabaseQuery = "DROP DATABASE " + databaseName;
            runQuery(dropDatabaseQuery);
        }
        String createDatabaseQuery = "CREATE DATABASE " + databaseName;
        runQuery(createDatabaseQuery);
    }

    public void createTable( String databaseName, String tableName,List<String>fieldNames) {
        establishConnection();
        String query = "create table "+databaseName+"."+tableName+" (";
        String createTableQuery;
        for(String field:fieldNames) {
            query+="`"+field+"` text,";
        }
        createTableQuery = removeLastCharacter(query);
        String createTableSql =createTableQuery+")";
        System.out.println(createTableSql);
       runQuery(createTableSql);
    }

    public void runQuery(String query) {

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public String removeLastCharacter(String str) {
        String output="";

        for(int i = 0; i < str.length()-1; i++) {
            output+=str.charAt(i);
        }
        return output;
    }
}
