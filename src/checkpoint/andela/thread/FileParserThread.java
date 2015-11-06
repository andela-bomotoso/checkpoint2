package checkpoint.andela.thread;

import checkpoint.andela.parser.AttributeValueFile;
import checkpoint.andela.parser.KeyValuePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileParserThread implements Runnable {

    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    Buffer sharedBuffer = new Buffer();
    AttributeValueFile fileToParse;
    LogBuffer logBuffer;
    File file;
    BufferedReader bufferedReader;
    public static boolean runState;


    public FileParserThread(AttributeValueFile fileToParse, LogBuffer logBuffer, Buffer sharedBuffer) {

        this.fileToParse = fileToParse;
        file = new File(fileToParse.getFileAddress());
        this.logBuffer = logBuffer;
        this.sharedBuffer = sharedBuffer;
    }

    public void run() {

        readAttributeFile();
        runState = true;
        String line;
        System.out.println("Reading file and writing to Database.....");
        try {
            while (((line = bufferedReader.readLine()) != null)) {
                KeyValuePair bufferWrite;

                if (!lineToBeSkipped(line)) {

                    String[] pair = line.trim().split(fileToParse.getKeyValueSeparator(), 2);
                    bufferWrite = new KeyValuePair<>(pair[0], pair[1]);
                    sharedBuffer.writeContentToBuffer(bufferWrite);
                    logThreadWriteActivity(bufferWrite);

                } else if (line.startsWith(fileToParse.getRecordMarker())) {
                    bufferWrite = new KeyValuePair<>(fileToParse.getRecordMarker(), "");
                    sharedBuffer.writeContentToBuffer(bufferWrite);
                    logThreadWriteActivity(bufferWrite);
                }

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        runState = false;
    }

    public void readAttributeFile() {
        try {
            if (fileExists()) {
                bufferedReader = new BufferedReader(new FileReader(file));
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public boolean fileExists() {
        return file.exists();
    }

    public boolean lineToBeSkipped(String line) {
        return (line.startsWith("/") && (line.trim() != fileToParse.getRecordMarker())) || line.startsWith(fileToParse.getCommentDelimiter()) || line.isEmpty();
    }

    public void logThreadWriteActivity(KeyValuePair bufferWrite) {

        String bufferWriteTime = dateTimeFormatter.print(DateTime.now());
        String key = bufferWrite.key.toString();
        String value = bufferWrite.value.toString();
        String currentLog = "("+getClass().getSimpleName() + bufferWriteTime + ")----wrote " + key + " " + value + " to buffer";
        logBuffer.writeToLogBuffer(currentLog);
    }
}
