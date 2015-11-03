package checkpoint.andela.parser;

import checkpoint.andela.thread.Buffer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.*;

import java.util.*;

public class FileParser implements Runnable {

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

    private void readAttributeFile() {
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
                    keyValues.add(new KeyValuePair<>(pair[0], pair[1]));
                }

                else if (line.startsWith(fileToParse.getRecordMarker())) {
                    keyValues.add(new KeyValuePair<>(fileToParse.getRecordMarker(), ""));
                }
            }
        }

        catch (IOException ioException) {
            ioException.printStackTrace();
        }

        fileToParse.setKeyValues(keyValues);

        return keyValues;
    }

    public boolean lineToBeSkipped(String line) {
        return (line.startsWith("/") && (line.trim() != fileToParse.getRecordMarker())) ||  line.startsWith(fileToParse.getCommentDelimiter()) || line.isEmpty();
    }

    public void run() {

    }


   /* public void logBufferWriteActivity(KeyValuePair keyValuePair) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        dateTimeFormatter.print(DateTime.now());

        String str = threadName+"("+dateTimeFormatter.print(DateTime.now())+")---- wrote "+keyValuePair.key+" "+keyValuePair.value +" to buffer";
        System.out.println(str);
    }*/

    }









