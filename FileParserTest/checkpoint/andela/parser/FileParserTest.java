package checkpoint.andela.parser;

import jdk.nashorn.internal.ir.annotations.Ignore;
import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

/**
 * Created by GRACE on 10/24/2015.
 */
public class FileParserTest extends TestCase {

    @Ignore
    public void testReadFile() {
        AttributeValueFile attributeValueFile = new AttributeValueFile();
        attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");
        FileParser fileParser = new FileParser(attributeValueFile);
        fileParser.readFile();
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