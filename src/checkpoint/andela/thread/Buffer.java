package checkpoint.andela.thread;

import checkpoint.andela.parser.KeyValuePair;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    private KeyValuePair sharedBuffer;
    private Lock accessLock = new ReentrantLock();
    private boolean hasContent = false;
    private boolean canReadFromBuffer = false;
    private Condition canWrite = accessLock.newCondition();
    private Condition canRead =  accessLock.newCondition();


    public KeyValuePair getContentFromBuffer() {
        accessLock.lock();
        try {
            while (!hasContent) {
               canRead.await();
            }
            hasContent = false;
            canReadFromBuffer = false;
           canWrite.signal();


        }catch (Exception interruptedException) {
                interruptedException.printStackTrace();
            }
        finally {
            accessLock.unlock();
        }
        return sharedBuffer;
    }

    public void writeContentToBuffer( KeyValuePair  currentLine) {
        accessLock.lock();
        try {
            while (hasContent) {
                canWrite.await();
            }
            sharedBuffer = currentLine;
            hasContent = true;
            canReadFromBuffer = true;
            canRead.signal();
        }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        finally {
            accessLock.unlock();
        }
    }

    public boolean isEmpty() {
        return hasContent;
    }

    public boolean readStatus() {
        return canReadFromBuffer;
    }

}
