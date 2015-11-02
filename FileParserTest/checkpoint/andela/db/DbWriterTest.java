package checkpoint.andela.db;

import checkpoint.andela.parser.*;
import checkpoint.andela.db.DbWriter;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DbWriterTest {

    AttributeValueFile attributeValueFile;
    DatabaseManager databaseManager;
    FileParser fileParser;
    DbWriter dbWriter;
    List <String> tableFields;

    @Before
    public void setUp() throws Exception {
        databaseManager =  new DatabaseManager("jdbc:mysql://localhost/","root","admin");
        attributeValueFile = new AttributeValueFile("#"," - ","//");
        attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");

        fileParser = new FileParser(attributeValueFile);

        dbWriter = new DbWriter(fileParser.readAttributeFile(),databaseManager,tableFields,"//");

        tableFields = new ArrayList<String>(Arrays.asList("UNIQUE-ID", "TYPES", "COMMON-NAME", "ATOM-MAPPINGS"));

    }

    @Ignore
    public void testWriteBufferToDatabase() throws Exception {
        dbWriter.writeBufferToDatabase("reactiondb","reactions",tableFields);
    }

    @Test
    public void testModifyExistingField() throws Exception {

        List<String> recordKeys = new ArrayList<>(Arrays.asList("RIGHT","^COEFFICIENT"));
        List<String> recordValues = new ArrayList<>(Arrays.asList("PROTON,CPD-12230,D-ALANINE","4"));

        String currentKey = "RIGHT";
        String currentValue = "UNDECAPRENYL-DIPHOSPHATE";

        List<String> recordValuesAfterModification = new ArrayList<>(Arrays.asList("PROTON,CPD-12230,D-ALANINE,UNDECAPRENYL-DIPHOSPHATE","4"));
        dbWriter.modifyExistingField(recordKeys,recordValues, currentKey, currentValue);

        assertEquals(recordValues.size(), recordKeys.size());
        assertEquals(recordValuesAfterModification.get(0),recordValues.get(0));

    }

    @Test
    public void testGenerateInsertStatement() throws Exception {
        List<String>recordKeys = new ArrayList<>(Arrays.asList("UNIQUE-ID", "TYPES", "COMMON-NAME"));
        List<String>recordValues = new ArrayList<>(Arrays.asList("RXN-8748","Small-Molecule-Reactions","6-phospho-lactosidase"));

        String insertQuery = "(`UNIQUE-ID`,`TYPES`,`COMMON-NAME`) VALUES (\"RXN-8748\""+","+"\"Small-Molecule-Reactions\""+","+"\"6-phospho-lactosidase\")";
        String insertQueryGenerated = dbWriter.generateInsertStatement(recordKeys, recordValues);

        assertEquals(insertQuery, insertQueryGenerated);
    }

    @Test
    public void testEscapeDoubleQuotes() throws Exception {
        String str1 = "CobA [ambiguous - see <a href=\\\"query.php?ec=2.5.1.17\\\" target=\\\"new\\\">EC 2.5.1.17</a>] SUMT";
        String str2 = dbWriter.escapeDoubleQuotes("CobA [ambiguous - see <a href=\"query.php?ec=2.5.1.17\" target=\"new\">EC 2.5.1.17</a>] SUMT");
       assertEquals(str1, str2);
    }
}