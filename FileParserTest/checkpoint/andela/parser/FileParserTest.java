package checkpoint.andela.parser;

import jdk.nashorn.internal.ir.annotations.Ignore;
import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
        assertEquals(attributeValueFile, fileParser.getFileToParse());
    }

    @Test
    public void lineOfTextfileStartswithSpecialCharacterHash() {
        String line = "# The format of this file is defined at http://bioinformatics.ai.sri.com/ptools/flatfile-format.html.";
        assertTrue(fileParser.lineToBeSkipped(line));
    }

    @Test
    public void lineOfTextfileStartswithSpecialCharacterSlash() {
        String line = "// ";
        assertTrue(fileParser.lineToBeSkipped(line));
    }

    @Test
    public void lineOfTextfileIsEmpty() {
        String line = "";
        assertTrue(fileParser.lineToBeSkipped(line));
    }

    @Test
    public void lineOfTextfileDoesNotStartWithSpecialCharacters() {
        String line = "(:NO-HYDROGEN-ENCODING (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56) (((NADH 0 43) (|Pyruvate-dehydrogenase-lipoate| 44 56)) ((NAD 0 43) (|Pyruvate-dehydrogenase-dihydrolipoate| 44 56))))";
        assertFalse(fileParser.lineToBeSkipped(line));

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
        assertFalse(keys.contains("SPECIES-ID"));

    }

    @Test
    public void testReadFileWhenAValueIsPresentInAnAntributeFile() {
        assertTrue(values.contains("RXN-13572"));
        assertTrue(values.contains("(:NO-HYDROGEN-ENCODING (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56) (((NADH 0 43) (|Pyruvate-dehydrogenase-lipoate| 44 56)) ((NAD 0 43) (|Pyruvate-dehydrogenase-dihydrolipoate| 44 56))))"));
        assertTrue(values.contains("CCO-PM-BAC-POS"));
        assertTrue(values.contains("RXN0-5265"));
        assertTrue(values.contains("T"));
    }

    @Test
    public void testReadFileWhenAValueIsNotPresentInAnAntributeFile() {
        AttributeValueFile attributeValueFile = new AttributeValueFile();
        attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");
        FileParser fileParser = new FileParser(attributeValueFile);
        fileParser.readAttributeFile();
        assertFalse(values.contains("ANDELA"));

    }


}