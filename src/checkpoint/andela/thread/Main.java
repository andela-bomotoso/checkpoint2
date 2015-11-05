package checkpoint.andela.thread;

import checkpoint.andela.db.DatabaseManager;
import checkpoint.andela.db.DbWriter;
import checkpoint.andela.parser.AttributeValueFile;
import checkpoint.andela.parser.FileParser;
import sun.nio.ch.FileKey;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

       Buffer sharedBuffer = new Buffer();
       LogBuffer logBuffer = new LogBuffer();
       ThreadManager threadManager = new ThreadManager();

       AttributeValueFile attributeValueFile = new AttributeValueFile("#"," - ","//");
       attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");

       DatabaseManager databaseManager = new DatabaseManager("jdbc:mysql://localhost/", "root", "admin");
       DbWriter dbWriter = new DbWriter(databaseManager);

       FileParserThread fileParserThread =  new FileParserThread(attributeValueFile,logBuffer,sharedBuffer);
       DbWriterThread dbWriterThread = new DbWriterThread(dbWriter,logBuffer,sharedBuffer);
       String filePath =  "C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.txt";
       LogWriterThread logWriterThread = new LogWriterThread(filePath,logBuffer);

       ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(fileParserThread);
        executorService.execute(dbWriterThread);
        executorService.execute(logWriterThread);

        executorService.shutdown();

        //fileParserThread.run();
        //dbWriterThread.run();


    }

}

