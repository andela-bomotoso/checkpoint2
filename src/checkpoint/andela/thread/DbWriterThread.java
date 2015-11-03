package checkpoint.andela.thread;

import checkpoint.andela.db.DatabaseManager;
import checkpoint.andela.parser.KeyValuePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DbWriterThread implements Runnable {

    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    DatabaseManager databaseManager;
    Buffer sharedBuffer = new Buffer();

    public DbWriterThread( DatabaseManager databaseManager, Buffer sharedBuffer) {
        this.databaseManager = databaseManager;
        this.sharedBuffer = sharedBuffer;
    }

    public void run() {
        KeyValuePair recordBuffer;
        //for (int count = 1; count <= 100; count++) {
            //while (sharedBuffer.readStatus()) {
            try {
                Thread.sleep(1000);
                recordBuffer = sharedBuffer.getContentFromBuffer();
                logBufferReadActivity(recordBuffer);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
           // }
        }
    }


        public void logBufferReadActivity(KeyValuePair keyValuePair) {
            dateTimeFormatter.print(DateTime.now());
            String str = getClass().getSimpleName() + "(" + dateTimeFormatter.print(DateTime.now()) + ")---- collected " + keyValuePair.key + " " + keyValuePair.value + " from buffer";
            System.out.println(str);
        }
    }

