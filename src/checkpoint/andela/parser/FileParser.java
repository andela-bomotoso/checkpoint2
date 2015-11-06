package checkpoint.andela.parser;

import checkpoint.andela.thread.Buffer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.*;

import java.util.*;

public class FileParser {

    private AttributeValueFile fileToParse;
    private File file;
    BufferedReader bufferedReader;
    List<KeyValuePair<String, String>> keyValues = new ArrayList<KeyValuePair<String, String>>();

    public FileParser(AttributeValueFile fileToParse) {

        this.fileToParse = fileToParse;
        file = new File(fileToParse.getFileAddress());
    }

    public AttributeValueFile getFileToParse() {
        return fileToParse;
    }

    public void setFileToParse(AttributeValueFile fileToParse) {
        this.fileToParse = fileToParse;
    }

    public boolean fileExists() {
        return file.exists();
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

    public  List<KeyValuePair<String, String>> writeFileToBuffer() {
        readAttributeFile();
        String line;
        try {

            while ((line = bufferedReader.readLine()) != null) {

                if (!lineToBeSkipped(line)) {

                    String[] pair = line.trim().split(fileToParse.getKeyValueSeparator(), 2);
                    KeyValuePair keyValuePair = new KeyValuePair<>(pair[0], pair[1]);
                    updateBuffer(keyValuePair);
                } else if (line.startsWith(fileToParse.getRecordMarker())) {

                    KeyValuePair keyValuePair = new KeyValuePair<>(fileToParse.getRecordMarker(), "");
                    updateBuffer(keyValuePair);
                }
            }
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }

        fileToParse.setKeyValues(keyValues);

        return keyValues;
    }

    public void updateBuffer(KeyValuePair keyValuePair) {
        keyValues.add(keyValuePair);

    }
    public boolean lineToBeSkipped(String line) {
        return (line.startsWith("/") && (line.trim() != fileToParse.getRecordMarker())) ||  line.startsWith(fileToParse.getCommentDelimiter()) || line.isEmpty();
    }

}









