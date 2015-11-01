package checkpoint.andela.parser;

/**
 * Created by GRACE on 10/27/2015.
 */
public class KeyValuePair<Key,Value> {
    public final Key   key;
    public final Value value;


    public KeyValuePair(Key key, Value value) {
        this.key = key;
        this.value =value;
    }

}
