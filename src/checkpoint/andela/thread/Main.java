package checkpoint.andela.thread;

import checkpoint.andela.db.DatabaseManager;
import checkpoint.andela.db.DbWriter;
import checkpoint.andela.parser.AttributeValueFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

       Buffer sharedBuffer = new Buffer();
       LogBuffer logBuffer = new LogBuffer();

       AttributeValueFile attributeValueFile = new AttributeValueFile("#"," - ","//");
       attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");

       DatabaseManager databaseManager = new DatabaseManager("jdbc:mysql://localhost/", "root", "admin");
       DbWriter dbWriter = new DbWriter(databaseManager);

       FileParserThread fileParserThread =  new FileParserThread(attributeValueFile,logBuffer,sharedBuffer);

       List<String> tableFields = new ArrayList<String>(Arrays.asList("UNIQUE-ID", "TYPES", "COMMON-NAME", "ATOM-MAPPINGS"));

       DbWriterThread dbWriterThread = new DbWriterThread(dbWriter,"reactiondb","reactions",tableFields,"//",logBuffer,sharedBuffer);

       String filePath =  "C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.txt";
       LogWriterThread logWriterThread = new LogWriterThread(filePath,logBuffer);

       ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(fileParserThread);
        executorService.execute(dbWriterThread);
        executorService.execute(logWriterThread);

        executorService.shutdown();


    }
}