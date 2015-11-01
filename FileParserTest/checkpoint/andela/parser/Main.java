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
        List<String>tableFields = new ArrayList<String>(Arrays.asList("UNIQUE-ID", "TYPES", "COMMON-NAME", "ATOM-MAPPINGS"));
        DbWriter dbWriter = new DbWriter(fileParser.readAttributeFile(),"#",databaseManager,tableFields);

        List<String> recordKeys = new ArrayList<>(Arrays.asList("RIGHT","^COEFFICIENT"));
        List<String> recordValues = new ArrayList<>(Arrays.asList("PROTON,CPD-12230,D-ALANINE","4"));
        String currentKey = "RIGHT";
        String currentValue = "UNDECAPRENYL-DIPHOSPHATE";
        List<String> recordValuesAfterModification1 = new ArrayList<>(Arrays.asList("PROTON,CPD-12230,D-ALANINE,UNDECAPRENYL-DIPHOSPHATE","4"));
        dbWriter.modifyExistingField(recordKeys, recordValues, currentKey, currentValue);
        System.out.println(recordValues.size() == recordKeys.size());
        System.out.println(recordValuesAfterModification1.get(0).equals(recordValues.get(0)));

        //for(String s : recordValues)
            //System.out.println(s);

        databaseManager.createDatabase("reactiondb");

        databaseManager.createTable("reactiondb","reactions",tableFields);



        dbWriter.writeBufferToDatabase("reactiondb","reactions",tableFields);



     //String str =    "(\"RXN-8748\""+","+"\"Small-Molecule-Reactions\""+","+"\"6-phospho-lactosidase\")";
        //System.out.println(str);
        //System.out.println( dbWriter.escapeDoubleQuotes("CobA [ambiguous - see <a href=\"query.php?ec=2.5.1.17\" target=\"new\">EC 2.5.1.17</a>] SUMT"));
        //System.out.println("CobA [ambiguous - see <a href=\\\"query.php?ec=2.5.1.17\\\" target=\\\"new\\\">EC 2.5.1.17</a>] SUMT");
    }





}
