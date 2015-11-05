package checkpoint.andela.thread;

import checkpoint.andela.parser.AttributeValueFile;
import checkpoint.andela.parser.FileParser;
import checkpoint.andela.parser.KeyValuePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileParserThread implements Runnable {


        private AttributeValueFile fileToParse;
        LogBuffer logBuffer;
        Buffer sharedBuffer = new Buffer();
        ThreadManager threadManager = new ThreadManager();
        private File file;
        BufferedReader bufferedReader;

        public FileParserThread(AttributeValueFile fileToParse,LogBuffer logBuffer,  Buffer sharedBuffer) {
        this.fileToParse = fileToParse;
        file = new File(fileToParse.getFileAddress());
        this.logBuffer = logBuffer;
        this.sharedBuffer = sharedBuffer;
        }

        public void run() {
            readAttributeFile();

            String line;
            try {
                    while ((line = bufferedReader.readLine()) != null && sharedBuffer.getCanWrite()) {

                        if (!lineToBeSkipped(line)) {

                            String[] pair = line.trim().split(fileToParse.getKeyValueSeparator(), 2);
                            KeyValuePair keyValuePair = new KeyValuePair<>(pair[0], pair[1]);
                            sharedBuffer.writeContentToBuffer(keyValuePair);
                            logThreadWriteActivity();

                        } else if (line.startsWith(fileToParse.getRecordMarker())) {
                            KeyValuePair keyValuePair = new KeyValuePair<>(fileToParse.getRecordMarker(), "");
                            sharedBuffer.writeContentToBuffer(keyValuePair);
                            logThreadWriteActivity();
                        }
                    }
                    sharedBuffer.setCanWrite(false);

                    return;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        public void readAttributeFile() {
            try {
                if(fileExists()) {
                    bufferedReader = new BufferedReader(new FileReader(file));
                }
            }
            catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        public boolean fileExists() {
            return file.exists();
        }

        public boolean lineToBeSkipped(String line) {
            return (line.startsWith("/") && (line.trim() != fileToParse.getRecordMarker())) ||  line.startsWith(fileToParse.getCommentDelimiter()) || line.isEmpty();
        }
        public void logThreadWriteActivity(){
           List<String>bufferDetails = sharedBuffer.getBufferDetails();
            String bufferWriteTime = bufferDetails.get(0);
            String bufferKey = bufferDetails.get(1);
            String bufferValue = bufferDetails.get(2);
            String currentLog = getClass().getSimpleName()+ bufferWriteTime+")----wrote "+bufferKey+" "+bufferValue+" to buffer";
            //System.out.println(currentLog);
//            try{
//                logBuffer.writeToLogBuffer(currentLog);
//            }
//            catch (InterruptedException interruptedException) {
//                interruptedException.printStackTrace();
//            }
            //System.out.println(currentLog);
            //threadManager.updateLog(currentLog);
        }


}
