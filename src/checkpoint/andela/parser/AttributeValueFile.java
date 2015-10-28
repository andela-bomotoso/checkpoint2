package checkpoint.andela.parser;

import java.util.List;

public class AttributeValueFile  {

    private String fileAddress;
    private String keyValueSeparator;
    private List<KeyValuePair<String, String>> keyValues;


    public AttributeValueFile() {

        }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getKeyValueSeparator() {
        return keyValueSeparator;
    }

    public void setKeyValueSeparator(String keyValueSeparator) {
        this.keyValueSeparator = keyValueSeparator;
    }

    public List<KeyValuePair<String, String>> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(List<KeyValuePair<String, String>> keyValues) {
        this.keyValues = keyValues;
    }
}
