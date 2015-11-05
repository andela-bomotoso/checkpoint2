package checkpoint.andela.thread;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class LogWriterThreadTest {
    LogWriterThread logWriterThread;
    String filePath1;
    String filePath2;

    @Before
    public void setUp() throws Exception {

        filePath1 =  "C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\andela.txt";
        filePath2 =  "C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.txt";
        LogBuffer logBuffer = new LogBuffer();
        logWriterThread = new LogWriterThread(filePath1,logBuffer);
    }

    @Test
    public void testCheckIfFileExistWhenFileDoesNotExist() throws Exception {
      File file1 = new File("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\andela.txt");
      assertFalse(file1.exists());
      logWriterThread.checkIfFileExist();
      assertTrue(file1.exists());
      boolean b = file1.delete();
    }

    @Test
    public void testCheckFileExistWhenFileExists() throws Exception {
        File file2 = new File("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.txt");
        assertTrue(file2.exists());
        logWriterThread.checkIfFileExist();
        assertTrue(file2.exists());

    }

    @Test
    public void testWriteBufferToFile() throws Exception {

    }
}