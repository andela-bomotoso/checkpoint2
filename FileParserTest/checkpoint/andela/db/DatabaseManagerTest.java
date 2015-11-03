package checkpoint.andela.db;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseManagerTest {

DatabaseManager databaseManager;

    @Before
    public void setUp() throws Exception {
        databaseManager =  new DatabaseManager("jdbc:mysql://localhost/","root","admin");
        databaseManager.establishConnection();
    }

    @Test
     public void testEstablishConnectionWhenConnectionCanBeEstablished() throws Exception {
        databaseManager =  new DatabaseManager("jdbc:mysql://localhost/","root","admin");
        Connection connection =  databaseManager.establishConnection();
        assertNotNull(connection);
    }
    @Test
    public void testDatabaseAlreadyExistWhenThereIsAnExistingDatabase() throws Exception {
        databaseManager.createDatabase("reactiondb");
        assertTrue(databaseManager.databaseAlreadyExist("reactiondb"));
    }

    @Test
    public void testDatabaseAlreadyExistWhenThereIsNoExistingDatabase() throws Exception {
        assertFalse(databaseManager.databaseAlreadyExist("ANDELA"));
    }

    @Test
    public void testCreateTable() throws Exception {
        List<String> tableFields = new ArrayList<String>(Arrays.asList("UNIQUE-ID", "TYPES", "COMMON-NAME", "ATOM-MAPPINGS"));
        databaseManager.createTable("reactiondb","reactions",tableFields);
    }

    @Test
    public void testRunQuery() throws Exception {
        String dropDbIfExists = "DROP DATABASE IF EXISTS ANDELA";
        databaseManager.runQuery(dropDbIfExists);
        String createDatabaseQuery = "CREATE DATABASE ANDELA";
        databaseManager.runQuery(createDatabaseQuery);
        assertTrue(databaseManager.databaseAlreadyExist("andela"));
        databaseManager.runQuery(dropDbIfExists);
        assertFalse(databaseManager.databaseAlreadyExist("andela"));
    }

    @Test
    public void testRemoveLastCharacter() throws Exception {
        String str1 = "CREATE TABLE ANDELA (Name,Age,Class,";
        String str2 = "CREATE TABLE ANDELA (Name,Age,Class";
        String str3 = databaseManager.removeLastCharacter(str1);
        assertEquals(str2,str3);

    }
}