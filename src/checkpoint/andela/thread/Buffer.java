package checkpoint.andela.thread;

import checkpoint.andela.parser.KeyValuePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    private KeyValuePair sharedBuffer;
    boolean inUse = false;
    boolean canWrite = true;
    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");



    public  synchronized KeyValuePair getContentFromBuffer() throws InterruptedException {
        while (!inUse) {
            wait();
        }
            inUse = false;
            notifyAll();
        System.out.println("dbwriter thread"+getBufferDetails().get(0)+" "+getBufferDetails().get(1) + " "+getBufferDetails().get(2));
            return sharedBuffer;
        }


    public  synchronized void writeContentToBuffer( KeyValuePair currentLine) throws InterruptedException{
        while (inUse) {
            wait();
        }
        inUse = true;
        sharedBuffer = currentLine;
        System.out.println("fileparser thread"+getBufferDetails().get(0)+" "+getBufferDetails().get(1) + " "+getBufferDetails().get(2));

        notifyAll();
    }

    public List<String> getBufferDetails() {

        String timeHappened = dateTimeFormatter.print(DateTime.now());
        String key = sharedBuffer.key.toString();
        String value = sharedBuffer.value.toString();
        List<String>bufferContent = new ArrayList<String>(Arrays.asList(timeHappened.toString(),key,value));

        return bufferContent;
    }

    public boolean getCanWrite() {
        return canWrite;
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }


}
