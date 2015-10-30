package checkpoint.andela.parser;

import java.io.*;
import java.util.*;

public class FileParser implements Runnable {

    private AttributeValueFile fileToParse;
    private String endofRecordDelimiter;
    private File file;
    FileInputStream fileInputStream;
    BufferedInputStream bufferedInputStream;

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

    public String getEndofRecordDelimiter() {
        return fileToParse.getRecordMarker();
    }

    public void setEndofRecordDelimiter(String endofRecordDelimiter) {
        fileToParse.setRecordMarker(endofRecordDelimiter);
    }

    public boolean fileExists() {
        return file.exists();
    }

    public List<KeyValuePair<String, String>> readAttributeFile() {
        List<KeyValuePair<String, String>> keyValues = new ArrayList<KeyValuePair<String, String>>();

        final int key = 0;
        final int value = 1;;

        try {
            BufferedReader bfr = new BufferedReader(new FileReader(file));
            String line;
            int index = 0;

            while ((line = bfr.readLine()) != null) {
                if ( !lineToBeSkipped(line) && !line.startsWith(fileToParse.getRecordMarker())) {
                    String[] pair = line.trim().split(fileToParse.getKeyValueSeparator(),2);
                    keyValues.add(new KeyValuePair<>(pair[0],pair[1]));
                }
                else if (line.startsWith(fileToParse.getRecordMarker())){
                    keyValues.add(new KeyValuePair<>(fileToParse.getRecordMarker(), ""));
                    index ++;
                }
            }
            //System.out.println(index);

            fileToParse.setKeyValues(keyValues);

        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());

        } finally {

            try {

                if (bufferedInputStream != null && fileInputStream != null) {
                    fileInputStream.close();
                    bufferedInputStream.close();
                }

            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }

//        for(KeyValuePair<String, String> pair : keyValues) {
//            System.out.println(pair.key + " " + pair.value+ index);

            return keyValues;
    }

    public boolean lineToBeSkipped(String line) {
        return (line.startsWith("/") && (line.trim() !=fileToParse.getRecordMarker())) ||  line.startsWith(fileToParse.getComment()) || line.isEmpty();
    }

    public void run() {

    }
}









