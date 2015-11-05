package checkpoint.andela.thread;

import java.io.*;

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

    public void writeBufferToFile(String currentLog) {
        checkIfFileExist();

            try {

                fileWriter = new FileWriter(fileToWrite, true);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(currentLog + "\n");
                bufferedWriter.close();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
    }

    public void run() {

        while (true){
        String currentLog = logBuffer.getLogBuffer();
        writeBufferToFile(currentLog);
       }
    }
}
