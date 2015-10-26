package checkpoint.andela.parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileParser {

    Properties properties;
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

    public void readFile() {
        try {

            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            while(bufferedInputStream.available() > 0){
                System.out.print((char)bufferedInputStream.read());
                //bufferedInputStream.read();
            }
            //properties.load(fileInputStream);
            //System.out.println("Available keys are: "+properties.keySet());
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

        finally {

            try{
                if(bufferedInputStream != null && fileInputStream != null) {
                    fileInputStream.close();
                    bufferedInputStream.close();
                }
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
            }
        }
    }









