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
        while (true) {
            try {
                recordBuffer = sharedBuffer.getContentFromBuffer();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    }

