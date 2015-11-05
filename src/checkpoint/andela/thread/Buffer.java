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

    public  synchronized KeyValuePair getContentFromBuffer() throws InterruptedException {

        while (!inUse) {
            wait();
        }
            inUse = false;
            notifyAll();
            return sharedBuffer;
        }

    public  synchronized void writeContentToBuffer( KeyValuePair currentLine) throws InterruptedException{

        while (inUse) {
            wait();
        }
        inUse = true;
        sharedBuffer = currentLine;
        notifyAll();
    }

    public boolean getCanWrite() {
        return canWrite;
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }


}
