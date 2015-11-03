package checkpoint.andela.thread;

import checkpoint.andela.parser.KeyValuePair;

import java.util.List;

public class Buffer {
    private  KeyValuePair  sharedBuffer;
    private boolean hasContent = false;
    private boolean bufferIsEmpty = true;

    public synchronized  KeyValuePair getContentFromBuffer() {
        while (!hasContent) {
            try {
                wait();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
            hasContent = false;
        bufferIsEmpty = true;
            notifyAll();
        return sharedBuffer;
    }

    public synchronized void writeContentToBuffer( KeyValuePair  currentLine) {
        //System.out.println(sharedBuffer.key+":"+sharedBuffer.value);

        while (hasContent)
        {
            try {
                wait();
            }
            catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

        sharedBuffer = currentLine;
        hasContent = true;
        bufferIsEmpty = false;
        notifyAll();
    }

    public boolean isEmpty() {
        return hasContent;
    }

}
