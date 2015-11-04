package checkpoint.andela.thread;

import java.util.List;

/**
 * Created by GRACE on 11/4/2015.
 */
public class ThreadManager extends Thread {

    private Buffer sharedBuffer;
    public static List<String > logBuffer;

    public static void updateLog(String log) {
        logBuffer.add(log);
    }
}




