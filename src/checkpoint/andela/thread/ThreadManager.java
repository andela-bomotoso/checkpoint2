package checkpoint.andela.thread;

import checkpoint.andela.parser.KeyValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GRACE on 11/4/2015.
 */
public class ThreadManager  {

    private Buffer sharedBuffer;
    private List<String > bufferLog = new ArrayList<>();

    public  void updateLog(String log) {
        bufferLog.add(log);
        System.out.println(log);
    }

    public void writeThreadLogToFile() {
        /*for(String log:bufferLog) {
            System.out.println(log);
        }*/

    }


}




