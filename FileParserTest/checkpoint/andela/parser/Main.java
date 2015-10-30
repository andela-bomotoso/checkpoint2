package checkpoint.andela.parser;

import checkpoint.andela.db.DbWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GRACE on 10/28/2015.
 */
public class Main {

    public static void main(String[]args) {
        AttributeValueFile attributeValueFile;
        FileParser fileParser;
        attributeValueFile = new AttributeValueFile(" - ","//");
        attributeValueFile.setComment("#");
        attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");
        fileParser = new FileParser(attributeValueFile);
        fileParser.readAttributeFile();

        List<String>tableFields = new ArrayList<String>(Arrays.asList("COMMON-NAME", "TEMPLATE-FILE","^COEFFCIENT", "^COMPARTMENT"));


        DatabaseManager  databaseManager =  new DatabaseManager("jdbc:mysql://localhost/","root","admin");
        databaseManager.createDatabase("reactiondb");

        databaseManager.createTable("reactiondb","reactions",tableFields);

        DbWriter dbWriter = new DbWriter(fileParser.readAttributeFile(),"//",databaseManager,tableFields);

        dbWriter.writeBufferToDatabase("reactiondb","reactions",tableFields);


    }





}
