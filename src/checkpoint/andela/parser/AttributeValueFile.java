package checkpoint.andela.parser;

import java.util.List;

public class AttributeValueFile  {

    private String fileAddress;
    private String keyValueSeparator;
    private String recordMarker;
    private String comment;
    private List<KeyValuePair<String, String>> keyValues;


    public AttributeValueFile(String keyValueSeparator, String recordMarker) {
            this.keyValueSeparator = keyValueSeparator;
            this.recordMarker = recordMarker;
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

    public String getRecordMarker() {
        return recordMarker;
    }

    public void setRecordMarker(String recordMarker){
        this.recordMarker = recordMarker;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public List<KeyValuePair<String, String>> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(List<KeyValuePair<String, String>> keyValues) {
        this.keyValues = keyValues;
    }
}
