package checkpoint.andela.thread;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.Assert.*;

public class LogWriterThreadTest {
    LogBuffer logBuffer;
    LogWriterThread logWriterThread;
    Scanner scanner;
    String filePath1;
    String filePath2;
    String filePath3;

    @Before
    public void setUp() throws Exception {

        filePath1 =  "C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\andela.txt";
        filePath2 =  "C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.txt";
        filePath3 =  "C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactionsTest.txt";

        LogBuffer logBuffer = new LogBuffer();
        logWriterThread = new LogWriterThread(filePath1,logBuffer);
    }

    @Test
    public void testCheckIfFileExistWhenFileDoesNotExist() throws Exception {

        logWriterThread = new LogWriterThread(filePath1,logBuffer);
        File file1 = new File(filePath1);
        assertFalse(file1.exists());
        logWriterThread.checkIfFileExist();
        assertTrue(file1.exists());
        file1.delete();
    }

    @Test
    public void testCheckFileExistWhenFileExists() throws Exception {

        logWriterThread = new LogWriterThread(filePath2,logBuffer);
        File file2 = new File(filePath2);
        assertTrue(file2.exists());
        logWriterThread.checkIfFileExist();
        assertTrue(file2.exists());

    }

    @Test
    public void testWriteBufferToFile() throws Exception {

        logWriterThread = new LogWriterThread(filePath3,logBuffer);
        File file3 = new File(filePath3);
        String str1 = "";
        String str2 = "";
        logWriterThread.writeBufferToFile(str1);

        assertTrue(file3.length() > 0);

        scanner = new Scanner(file3);
        while(scanner.hasNextLine()){
            str2 = scanner.nextLine();
        }
        assertEquals(str1,str2);
    }
}