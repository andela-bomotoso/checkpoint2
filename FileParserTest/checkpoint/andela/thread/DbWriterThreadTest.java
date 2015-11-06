package checkpoint.andela.thread;

import checkpoint.andela.db.DatabaseManager;
import checkpoint.andela.db.DbWriter;
import checkpoint.andela.parser.KeyValuePair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DbWriterThreadTest {

    Buffer sharedBuffer = new Buffer();
    LogBuffer logBuffer = new LogBuffer();
    DbWriterThread dbWriterThread;

    @Before
    public void setUp() throws Exception {
        List<String> tableFields = new ArrayList<String>(Arrays.asList("UNIQUE-ID", "TYPES", "COMMON-NAME", "ATOM-MAPPINGS"));

        DatabaseManager databaseManager = new DatabaseManager("jdbc:mysql://localhost/", "root", "admin");
        DbWriter dbWriter = new DbWriter(databaseManager);
        dbWriterThread = new DbWriterThread(dbWriter,"reactiondb","reactions",tableFields,"//",logBuffer,sharedBuffer);
    }

    @Test
    public void testLogThreadReadActivity() throws Exception {

        KeyValuePair bufferRead = new KeyValuePair<>("ENZYMATIC-REACTION","ENZRXNMT2-562");
        dbWriterThread.logThreadReadActivity(bufferRead);
        String currentLog = logBuffer.getLogBuffer();
        assertTrue(currentLog.contains(")----collected ENZYMATIC-REACTION ENZRXNMT2-562 from buffer"));
    }

    @Test
    public void testWriteRecordToDatabase() throws Exception {

    }
}