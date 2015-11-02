package checkpoint.andela.parser;

import java.io.*;
import java.util.*;

public class FileParser implements Runnable {

    private AttributeValueFile fileToParse;
    private File file;

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

    public List<KeyValuePair<String, String>> readAttributeFile() {
        List<KeyValuePair<String, String>> keyValues = new ArrayList<KeyValuePair<String, String>>();

        try {
                if(fileExists()) {
                    BufferedReader bfr = new BufferedReader(new FileReader(file));

                    String line;

                while ((line = bfr.readLine()) != null) {
                    if (!lineToBeSkipped(line)) {
                        String[] pair = line.trim().split(fileToParse.getKeyValueSeparator(), 2);
                        keyValues.add(new KeyValuePair<>(pair[0], pair[1]));
                    }

                    else if (line.startsWith(fileToParse.getRecordMarker())) {
                        keyValues.add(new KeyValuePair<>(fileToParse.getRecordMarker(), ""));
                    }
                }

                fileToParse.setKeyValues(keyValues);
            }
        }
        catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
            return keyValues;
    }

    public boolean lineToBeSkipped(String line) {
        return (line.startsWith("/") && (line.trim() != fileToParse.getRecordMarker())) ||  line.startsWith(fileToParse.getCommentDelimiter()) || line.isEmpty();
    }

    public void run() {

        readAttributeFile();
    }
}









