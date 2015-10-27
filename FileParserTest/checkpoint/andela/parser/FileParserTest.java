package checkpoint.andela.parser;

import jdk.nashorn.internal.ir.annotations.Ignore;
import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GRACE on 10/24/2015.
 */
public class FileParserTest extends TestCase {
    List<String> keys = new ArrayList<String>();
    List<String> values = new ArrayList<String>();
    AttributeValueFile attributeValueFile;
    FileParser fileParser;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        attributeValueFile = new AttributeValueFile();
        attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");
        fileParser = new FileParser(attributeValueFile);
        fileParser.readAttributeFile();
        List<KeyValuePair<String, String>> keyVal= attributeValueFile.getKeyValues();
        for (KeyValuePair<String, String> pair :keyVal) {
            keys.add(pair.key);
            values.add(pair.value);
        }
    }

    @Test
    public void testReadFileWhenAKeyIsPresentInAnAntributeFile() {
        assertTrue(keys.contains("UNIQUE-ID"));
        assertTrue(keys.contains("ATOM-MAPPINGS"));
        assertTrue(keys.contains("RXN-LOCATIONS"));
        assertTrue(keys.contains("REACTION-LIST"));
        assertTrue(keys.contains("SPONTANEOUS?"));
    }

    @Test
    public void testReadFileWhenAKeyIsNotPresentInAnAntributeFile() {
        AttributeValueFile attributeValueFile = new AttributeValueFile();
        attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");
        FileParser fileParser = new FileParser(attributeValueFile);
        fileParser.readAttributeFile();
        //assertFalse(attributeValueFile.getKeyValues().containsKey("SPECIES-ID"));

    }

    @Test
    public void testReadFileWhenAValueIsPresentInAnAntributeFile() {
        assertTrue(values.contains("RXN-13572"));
        /*assertTrue(attributeValueFile.getKeyValues().containsValue(":NEED-RECOMPUTATION"));
        assertTrue(attributeValueFile.getKeyValues().containsValue("CCO-PM-BAC-POS"));
        assertTrue(attributeValueFile.getKeyValues().containsValue("RXN0-5265"));
        assertTrue(attributeValueFile.getKeyValues().containsValue("T"));*/
    }

    @Test
    public void testReadFileWhenAValueIsNotPresentInAnAntributeFile() {
        AttributeValueFile attributeValueFile = new AttributeValueFile();
        attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");
        FileParser fileParser = new FileParser(attributeValueFile);
        fileParser.readAttributeFile();
        //assertFalse(attributeValueFile.getKeyValues().containsValue("ANDELA"));

    }

    @Test
       public void testFileExistsWhenFileExist() {
        AttributeValueFile attributeValueFile = new AttributeValueFile();
        attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");
        FileParser fileParser = new FileParser(attributeValueFile);
        assertTrue(fileParser.fileExists());
    }

    @Test
    public void testFileExistsWhenFileDoesNotExist() {
        AttributeValueFile attributeValueFile = new AttributeValueFile();
        attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reaction.DAT");
        FileParser fileParser = new FileParser(attributeValueFile);
        fileParser.setFileToParse(attributeValueFile);
        assertFalse(fileParser.fileExists());
    }
}