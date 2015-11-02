package checkpoint.andela.parser;

import checkpoint.andela.db.DatabaseManager;
import checkpoint.andela.db.DbWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GRACE on 10/28/2015.
 */
public class Main {

    public static void main(String[]args) {
        DatabaseManager databaseManager =  new DatabaseManager("jdbc:mysql://localhost/","root","admin");
        AttributeValueFile attributeValueFile = new AttributeValueFile("#"," - ","//");
        attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");
        FileParser fileParser = new FileParser(attributeValueFile);
       List<String>tableFields = new ArrayList<String>(Arrays.asList("UNIQUE-ID","TYPES","COMMON-NAME", "ATOM-MAPPINGS",
               "SYNONYMS", "SYSTEMATIC-NAME", "TEMPLATE-FILE","^COEFFICIENT","^COMPARTMENT"));
        fileParser.run();
       DbWriter dbWriter = new DbWriter(fileParser.getFileToParse().getKeyValues(),databaseManager,tableFields,"//");

       databaseManager.establishConnection();
       databaseManager.createDatabase("reactiondb");

        databaseManager.createTable("reactiondb","reactions",tableFields);

       //System.out.println(databaseManager.databaseAlreadyExist("reactiondb"));
       // System.out.println(databaseManager.tableAlreadyExist("reactiondb", "reactions"));

       dbWriter.writeBufferToDatabase("reactiondb","reactions",tableFields);

    }





}
