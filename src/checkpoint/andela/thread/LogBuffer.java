package checkpoint.andela.thread;

import checkpoint.andela.parser.KeyValuePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GRACE on 11/4/2015.
 */
public class LogBuffer {

    private String sharedLog;
    boolean inUse = false;
    boolean canWrite = true;


    public  synchronized String getLogBuffer() throws InterruptedException {
        while (!inUse) {
            wait();
        }
        inUse = false;
        notifyAll();
        //System.out.println(sharedLog);
        return sharedLog;
    }


    public  synchronized void writeToLogBuffer( String currentLine) throws InterruptedException{
        while (inUse) {
            wait();
        }
        inUse = true;
        sharedLog = currentLine;
        //System.out.println(sharedLog);
        notifyAll();
    }

    public boolean getCanWrite() {
        return canWrite;
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }
}
