package checkpoint.andela.thread;

import java.util.concurrent.ArrayBlockingQueue;

public class LogBuffer {

    private final ArrayBlockingQueue<String>logBuffer;
    public boolean inUse = false;

    public LogBuffer()
    {
        logBuffer = new ArrayBlockingQueue<String>(1);
    }
    public void writeToLogBuffer( String currentLine) {
        try {

            logBuffer.put(currentLine);
            inUse = true;
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
    public String getLogBuffer() {
        String currentLog = "";
        try {
            currentLog = logBuffer.take();
            inUse = false;
        }
        catch(InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        return currentLog;
    }
}
