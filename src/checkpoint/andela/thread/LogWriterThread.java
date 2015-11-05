package checkpoint.andela.thread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by GRACE on 11/4/2015.
 */
public class LogWriterThread implements Runnable{

    private File fileToWrite;
    private String filePath;
    private BufferedWriter bufferedWriter;
    private FileWriter fileWriter;
    private LogBuffer logBuffer;

    public LogWriterThread(String filePath,LogBuffer logBuffer) {
        fileToWrite = new File(filePath);
        this.logBuffer = logBuffer;
    }

    public void checkIfFileExist() {
        try {
            if (!fileToWrite.exists()) {
                fileToWrite.createNewFile();
            }
        }

        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void writeBufferToFile(String logBuffer) {
        checkIfFileExist();
        try {
            fileWriter = new FileWriter(fileToWrite,true);
            bufferedWriter  = new BufferedWriter(fileWriter);
            bufferedWriter.write(logBuffer);
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void run() {

        try {
            writeBufferToFile(logBuffer.getLogBuffer());
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }


}
