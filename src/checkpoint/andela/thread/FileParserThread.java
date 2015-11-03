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

    public class FileParserThread implements Runnable {

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        private AttributeValueFile fileToParse;
        Buffer sharedBuffer = new Buffer();
        private File file;
        BufferedReader bufferedReader;

        public FileParserThread(AttributeValueFile fileToParse, Buffer sharedBuffer) {
        this.fileToParse = fileToParse;
        file = new File(fileToParse.getFileAddress());
        this.sharedBuffer = sharedBuffer;
        }

        public void run() {
            readAttributeFile();

            String line;
            try {
                Thread.sleep(1000);
                while ((line = bufferedReader.readLine()) != null) {

                    if (!lineToBeSkipped(line)) {

                     String[] pair = line.trim().split(fileToParse.getKeyValueSeparator(), 2);
                     KeyValuePair   keyValuePair = new KeyValuePair<>(pair[0], pair[1]);
                     sharedBuffer.writeContentToBuffer(keyValuePair);
                     logBufferWriteActivity(keyValuePair);


                    } else if (line.startsWith(fileToParse.getRecordMarker())) {
                      KeyValuePair  keyValuePair = new KeyValuePair<>(fileToParse.getRecordMarker(), "");
                      sharedBuffer.writeContentToBuffer(keyValuePair);
                      logBufferWriteActivity(keyValuePair);
                    }
                }
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

        public void logBufferWriteActivity(KeyValuePair keyValuePair) {

            dateTimeFormatter.print(DateTime.now());

            String str = getClass().getSimpleName() + "(" + dateTimeFormatter.print(DateTime.now()) + ")---- wrote " + keyValuePair.key + " " + keyValuePair.value + " to buffer";
            System.out.println(str);

        }
}
