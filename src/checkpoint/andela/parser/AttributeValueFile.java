package checkpoint.andela.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class AttributeValueFile  {

    private String fileAddress;
    private List<String> attributes;
    private List<List<String>> keyValues = new ArrayList<List<String>>();

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

    public void setValues(List<List<String>> keyValues) {
        this.keyValues = keyValues;
    }

    public List<List<String>> getKeyValues() {
        return keyValues;
    }

}
