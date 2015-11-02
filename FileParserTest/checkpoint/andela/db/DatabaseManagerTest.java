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
        assertTrue(databaseManager.databaseAlreadyExist("reactiondb"));
    }

    @Test
    public void testDatabaseAlreadyExistWhenThereIsNoExistingDatabase() throws Exception {
        assertFalse(databaseManager.databaseAlreadyExist("Andela"));
    }

    @Ignore
    public void testTableAlreadyExistWhenThereIsAnExistingDatabaseTable() throws Exception {
        assertTrue(databaseManager.tableAlreadyExist("reactiondb", "reactions"));
    }

    @Test
    public void testCreateDatabase() throws Exception {
        databaseManager.createDatabase("helpmebuy");
        assertTrue(databaseManager.databaseAlreadyExist("helpmebuy"));
    }

    @Ignore
    public void testCreateTable() throws Exception {
       List<String> tableFields = new ArrayList<String>(Arrays.asList("UNIQUE-ID", "TYPES", "COMMON-NAME", "ATOM-MAPPINGS"));
        databaseManager.createTable("helpmebuy","reactions",tableFields);
        assertTrue(databaseManager.tableAlreadyExist("helpmebuy","reactions"));

    }

    @Ignore
    public void testRunQuery() throws Exception {

    }

    @Test
    public void testRemoveLastCharacter() throws Exception {
        String str1 = "CREATE TABLE ANDELA (Name,Age,Class,";
        String str2 = "CREATE TABLE ANDELA (Name,Age,Class";
        String str3 = databaseManager.removeLastCharacter(str1);
        assertEquals(str2,str3);

    }
}