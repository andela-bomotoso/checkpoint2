package checkpoint.andela.thread;

import checkpoint.andela.parser.KeyValuePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    private KeyValuePair sharedBuffer;
    private String threadName;
    boolean inUse = false;
    boolean canWrite = true;
    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");



    public  synchronized KeyValuePair getContentFromBuffer() throws InterruptedException {
        while (!inUse) {
            wait();
        }
            inUse = false;
            notifyAll();
            LogReadActivity("DbWriter");
            return sharedBuffer;
        }


    public  synchronized void writeContentToBuffer( KeyValuePair currentLine) throws InterruptedException{
        while (inUse) {
            wait();
        }
        inUse = true;
        sharedBuffer = currentLine;
        LogWriteActivity("FileParser");
        //ThreadManager.updateLog();
        notifyAll();
    }

    public String LogWriteActivity(String ThreadName) {

        String str =  ThreadName+"(" + dateTimeFormatter.print(DateTime.now()) + ")---- wrote " + sharedBuffer.key + " " + sharedBuffer.value + " to buffer";
        return str;
    }

    public void LogReadActivity(String ThreadName) {
        String str = ThreadName+ "(" + dateTimeFormatter.print(DateTime.now()) + ")---- collected " + sharedBuffer.key + " " + sharedBuffer.value + " to buffer";
        System.out.println(str);
    }

    public boolean getCanWrite() {
        return canWrite;
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }


}
