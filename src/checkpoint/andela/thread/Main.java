package checkpoint.andela.thread;

import checkpoint.andela.db.DatabaseManager;
import checkpoint.andela.parser.AttributeValueFile;
import checkpoint.andela.parser.FileParser;
import sun.nio.ch.FileKey;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by GRACE on 11/3/2015.
 */
public class Main {

    public static void main(String[] args) {

       Buffer sharedBuffer = new Buffer();

       AttributeValueFile attributeValueFile = new AttributeValueFile("#"," - ","//");
       attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");

       DatabaseManager databaseManager = new DatabaseManager("jdbc:mysql://localhost/", "root", "admin");
       FileParserThread fileParserThread =  new FileParserThread(attributeValueFile,sharedBuffer);
       DbWriterThread dbWriterThread = new DbWriterThread(databaseManager,sharedBuffer);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(fileParserThread);
        executorService.execute(dbWriterThread);

        executorService.shutdown();

        //fileParserThread.run();
        //dbWriterThread.run();
    }

}

