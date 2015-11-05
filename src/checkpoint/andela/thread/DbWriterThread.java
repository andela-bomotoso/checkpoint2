package checkpoint.andela.thread;

import checkpoint.andela.db.DatabaseManager;
import checkpoint.andela.db.DbWriter;
import checkpoint.andela.parser.KeyValuePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbWriterThread implements Runnable {

    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    DatabaseManager databaseManager;
    LogBuffer logBuffer;
    Buffer sharedBuffer = new Buffer();
    KeyValuePair bufferRead;
    DbWriter dbWriter = new DbWriter(databaseManager);

    private String databaseName;
    private String tableName;
    private List<String>tableFields;
    private String recordMaker;

    public DbWriterThread(DbWriter dbWriter, String databasename, String tableName,
                          List<String> tableFields, String recordMaker, LogBuffer logBuffer,Buffer sharedBuffer) {

        this.dbWriter = dbWriter;
        this.databaseName = databasename;
        this.tableName = tableName;
        this.tableFields = tableFields;
        this.recordMaker = recordMaker;
        this.logBuffer = logBuffer;
        this.sharedBuffer = sharedBuffer;
    }

    public void run() {
     List<KeyValuePair<String,String>> bufferedRecord = new ArrayList<KeyValuePair<String, String>>();

       while (true) {

            try {

                bufferRead = sharedBuffer.getContentFromBuffer();
                logThreadReadActivity();
                bufferedRecord.add(bufferRead);

                if(bufferRead.key.equals(recordMaker)) {
                    writeRecordToDatabase(bufferedRecord);
                }
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
   }

    public void logThreadReadActivity() {

        String bufferReadTime = dateTimeFormatter.print(DateTime.now());
        String key =   bufferRead.key.toString();
        String value = bufferRead.value.toString();
        String currentLog = "("+getClass().getSimpleName() + bufferReadTime+ ")----collected " + key + " " + value + " from buffer";

        logBuffer.writeToLogBuffer(currentLog);
    }

    public void writeRecordToDatabase(List<KeyValuePair<String,String>> bufferedRecord) {

        dbWriter.writeBufferToDatabase(bufferedRecord,databaseName,tableName,tableFields,recordMaker);
       bufferedRecord.clear();
    }
}

