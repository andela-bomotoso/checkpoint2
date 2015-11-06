package checkpoint.andela.thread;

import checkpoint.andela.parser.AttributeValueFile;
import checkpoint.andela.parser.KeyValuePair;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.Assert.*;

/**
 * Created by GRACE on 11/5/2015.
 */
public class FileParserThreadTest {

    AttributeValueFile fileToParse;
    LogBuffer logBuffer;
    Buffer sharedBuffer;
    File file;
    BufferedReader bufferedReader;
    FileParserThread fileParserThread;

    @Before
    public void setUp() throws Exception {

        fileToParse = new AttributeValueFile("#"," - ","//");
        fileToParse.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");
        file = new File(fileToParse.getFileAddress());

        logBuffer = new LogBuffer();
        sharedBuffer = new Buffer();
        bufferedReader = new BufferedReader(new FileReader(file));

        fileParserThread = new FileParserThread(fileToParse,logBuffer,sharedBuffer);

    }

    @Test
    public void testReadAttributeFile() throws Exception {
        fileParserThread.readAttributeFile();

        assertNotNull(bufferedReader);
    }

    @Test
    public void testFileExistsWhenFileExists() throws Exception {

        assertTrue(fileParserThread.fileExists());
    }

    @Test
    public void testFileExistsWhenFileDoesNotExist() throws Exception {

        fileToParse.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\andela.DAT");
        file = new File(fileToParse.getFileAddress());
        fileParserThread = new FileParserThread(fileToParse,logBuffer,sharedBuffer);

        assertFalse(fileParserThread.fileExists());
    }

    @Test
    public void testLineToBeSkippedWhenLineOfTextfileStartswithCommentDelimiterHash() {

        String line = "# The format of this file is defined at http://bioinformatics.ai.sri.com/ptools/flatfile-format.html.";
        assertTrue(fileParserThread.lineToBeSkipped(line));
    }

    @Test
    public void testLineToBeSkippedWhenLineStartswithRecordMarkerDoubleSlash() {
        String line = "// ";
        assertTrue(fileParserThread.lineToBeSkipped(line));
    }

    @Test
    public void testLineToBeSkippedWhenlineOfTextfileIsEmpty() {
        String line = "";
        assertTrue(fileParserThread.lineToBeSkipped(line));
    }

    @Test
    public void testLineToBeSkippedWhenlineOfTextfileDoesNotStartWithSpecialCharacters() {
        String line = "(:NO-HYDROGEN-ENCODING (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56) (((NADH 0 43) (|Pyruvate-dehydrogenase-lipoate| 44 56)) ((NAD 0 43) (|Pyruvate-dehydrogenase-dihydrolipoate| 44 56))))";
        assertFalse(fileParserThread.lineToBeSkipped(line));
    }

    @Test
    public void testLogThreadWriteActivity() throws Exception {

        KeyValuePair bufferWrite = new KeyValuePair<>("ENZYMATIC-REACTION","ENZRXNMT2-562");
        fileParserThread.logThreadWriteActivity(bufferWrite);
        String currentLog = logBuffer.getLogBuffer();
        assertTrue(currentLog.contains(")----wrote ENZYMATIC-REACTION ENZRXNMT2-562 to buffer"));
    }
}