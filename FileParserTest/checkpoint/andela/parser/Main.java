package checkpoint.andela.parser;

import checkpoint.andela.db.DatabaseManager;
import checkpoint.andela.db.DbWriter;
import checkpoint.andela.thread.Buffer;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by GRACE on 10/28/2015.
 */
public class Main {

    public static void main(String[] args) {

       DatabaseManager databaseManager = new DatabaseManager("jdbc:mysql://localhost/", "root", "admin");

      AttributeValueFile  attributeValueFile = new AttributeValueFile("#", " - ", "//");
        attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");

       FileParser fileParser = new FileParser(attributeValueFile);

        List<KeyValuePair<String, String>> bufferedFileContent = fileParser.writeFileToBuffer();
        List<String> tableFields = new ArrayList<String>(Arrays.asList("UNIQUE-ID", "TYPES", "COMMON-NAME", "ATOM-MAPPINGS"));
        DbWriter   dbWriter = new DbWriter( databaseManager);
        dbWriter.writeBufferToDatabase(bufferedFileContent,"reactiondb","reactions",tableFields,"//");


    }
}










