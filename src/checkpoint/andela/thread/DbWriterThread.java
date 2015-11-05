package checkpoint.andela.thread;

import checkpoint.andela.db.DatabaseManager;
import checkpoint.andela.db.DbWriter;
import checkpoint.andela.parser.KeyValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbWriterThread implements Runnable {

    DatabaseManager databaseManager;
    LogBuffer logBuffer;
    Buffer sharedBuffer = new Buffer();
    DbWriter dbWriter = new DbWriter(databaseManager);

    public DbWriterThread( DbWriter dbWriter, LogBuffer logBuffer,Buffer sharedBuffer) {
        this.dbWriter = dbWriter;
        this.logBuffer = logBuffer;
        this.sharedBuffer = sharedBuffer;
    }

    public void run() {
     List<KeyValuePair<String,String>> bufferedRecord = new ArrayList<KeyValuePair<String, String>>();
    KeyValuePair bufferRead;
       while (true) {
            try {
                bufferRead = sharedBuffer.getContentFromBuffer();
                logThreadReadActivity();
                bufferedRecord.add(bufferRead);
                //writeRecordToDatabase(bufferedRecord);

            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
   }
    public void logThreadReadActivity() {
        List<String> bufferDetails = sharedBuffer.getBufferDetails();
        String bufferWriteTime = bufferDetails.get(0);
        String bufferKey = bufferDetails.get(1);
        String bufferValue = bufferDetails.get(2);
        String currentLog = getClass().getSimpleName() + bufferWriteTime + ")----collected " + bufferKey + " " + bufferValue + " from buffer";
        //System.out.println(currentLog);
       // threadManager.updateLog(currentLog);
//        try {
//            logBuffer.writeToLogBuffer(currentLog);
//        }
//        catch (InterruptedException interruptedException) {
//            interruptedException.printStackTrace();
//        }
    }

    public void writeRecordToDatabase(List<KeyValuePair<String,String>> bufferedRecord) {
        List<String> tableFields = new ArrayList<String>(Arrays.asList("UNIQUE-ID", "TYPES", "COMMON-NAME", "ATOM-MAPPINGS"));
       dbWriter.writeBufferToDatabase(bufferedRecord,"reactiondb","reactions",tableFields,"//");
    }
}

