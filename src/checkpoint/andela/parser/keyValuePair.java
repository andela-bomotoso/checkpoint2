package checkpoint.andela.parser;

public class KeyValuePair<Key,Value> {
    public final Key   key;
    public final Value value;

    public KeyValuePair(Key key, Value value) {
        this.key = key;
        this.value =value;
    }

}
