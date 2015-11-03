package checkpoint.andela.parser;

public class KeyValuePair<Key,Value> {
    public Key   key;
    public Value value;

    public KeyValuePair(Key key, Value value) {
        this.key = key;
        this.value =value;
    }

}
