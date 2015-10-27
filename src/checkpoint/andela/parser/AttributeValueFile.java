package checkpoint.andela.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class AttributeValueFile <key,value>  {

    private String fileAddress;
    private List<String> attributes;
    private List<KeyValuePair<String, String>> keyValues;

    public AttributeValueFile() {

        }

    public AttributeValueFile(String fileAddress, List<String> attributes) {
     this.fileAddress = fileAddress;
     this.attributes = attributes;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public List<KeyValuePair<String, String>> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(List<KeyValuePair<String, String>> keyValues) {
        this.keyValues = keyValues;
    }
}
