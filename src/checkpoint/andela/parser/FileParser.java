package checkpoint.andela.parser;

import com.intellij.dvcs.branch.DvcsSyncSettings;

import java.io.*;
import java.util.*;

public class FileParser implements Runnable {

    private AttributeValueFile fileToParse;
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

            while ((line = bfr.readLine()) != null) {
                if ( !lineToBeSkipped(line)) {
                    String[] pair = line.trim().split(" - ");
                    keyValues.add(new KeyValuePair<>(pair[0],pair[1]));
                }
            }

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
        return keyValues;
    }

    public boolean lineToBeSkipped(String line) {
        return  line.startsWith("/") || line.startsWith("#") || line.isEmpty();
    }

    public void run() {

    }
}









