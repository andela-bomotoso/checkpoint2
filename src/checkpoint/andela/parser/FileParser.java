package checkpoint.andela.parser;

import java.io.*;
import java.nio.Buffer;
import java.util.*;

public class FileParser implements Runnable {

    private AttributeValueFile fileToParse;
    private File file;
    //private Buffer buffer;
    BufferedReader bufferedReader;
    String line;
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


                /*while ((line = bufferedReader.readLine()) != null) {
                    if (!lineToBeSkipped(line)) {
                        String[] pair = line.trim().split(fileToParse.getKeyValueSeparator(), 2);
                        keyValues.add(new KeyValuePair<>(pair[0], pair[1]));
                    }

                    else if (line.startsWith(fileToParse.getRecordMarker())) {
                        keyValues.add(new KeyValuePair<>(fileToParse.getRecordMarker(), ""));
                    }
                }*/


            }
        }
        catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
            //return keyValues;
    }

    public boolean lineToBeSkipped(String line) {
        return (line.startsWith("/") && (line.trim() != fileToParse.getRecordMarker())) ||  line.startsWith(fileToParse.getCommentDelimiter()) || line.isEmpty();
    }

    public void run() {
        readAttributeFile();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (!lineToBeSkipped(line)) {
                    String[] pair = line.trim().split(fileToParse.getKeyValueSeparator(), 2);
                    keyValues.add(new KeyValuePair<>(pair[0], pair[1]));
                } else if (line.startsWith(fileToParse.getRecordMarker())) {
                    keyValues.add(new KeyValuePair<>(fileToParse.getRecordMarker(), ""));
                }
            }
            fileToParse.setKeyValues(keyValues);
            /*for(KeyValuePair keyVal:keyValues)
            {
                System.out.println(keyVal.key+":"+keyVal.value);
            }*/

        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}









