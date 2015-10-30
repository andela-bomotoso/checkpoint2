package checkpoint.andela.db;

import checkpoint.andela.parser.AttributeValueFile;
import checkpoint.andela.parser.DatabaseManager;
import checkpoint.andela.parser.FileParser;
import checkpoint.andela.parser.KeyValuePair;

import java.util.ArrayList;
import java.util.List;

public class DbWriter {

    private String recordMaker;
    List<KeyValuePair<String, String>> bufferedFileContent = new ArrayList<KeyValuePair<String, String>>();
    DatabaseManager databaseManager;
    List<String>tableFields;


    public DbWriter(List<KeyValuePair<String,String>>bufferedFileContent, String recordMaker, DatabaseManager databaseManager,List<String>tableFields ) {
        this.bufferedFileContent = bufferedFileContent;
        this.databaseManager = databaseManager;
        this.recordMaker = recordMaker;
        this.tableFields = tableFields;
    }

    public String getEndofRecordDelimiter() {
        return recordMaker;
    }

    public void setEndofRecordDelimiter(String endofRecordDelimiter) {
        this.recordMaker = endofRecordDelimiter;
    }

    public void writeBufferToDatabase(String DatabaseName, String TableName, List<String>tablefields) {
        databaseManager.establishConnection();

        List<String>recordKeys = new ArrayList<>();
        List<String>recordValues = new ArrayList<>();

        for(KeyValuePair<String,String> pair : bufferedFileContent) {

            if(!pair.key .equals(getEndofRecordDelimiter())) {

                if(!recordKeys.contains(pair.key) && tablefields.contains(pair.key)) {

                    recordKeys.add(pair.key);
                    recordValues.add(pair.value);
                }
                else
                {
                    if(tablefields.contains(pair.key)) {
                        modifyExistingField(recordKeys, recordValues, pair.value);
                    }
                }
            }
            else {
                if(!recordKeys.isEmpty()) {
                    String sql = "INSERT INTO " + DatabaseName + "." + TableName + generateInsertStatement(recordKeys, recordValues);
                    databaseManager.runQuery(sql);
                    recordKeys.clear();
                    recordValues.clear();
                }
            }
        }
    }

    public void modifyExistingField(List<String>keys,List<String>values, String currentKey) {
        int index = 0;
        for(int i = 0; i < keys.size(); i++) {
            index = i;
            if(keys.get(index).equals( currentKey)) {
                break;
            }
        }
        values.set(index, values.get(index) + "," + currentKey);
    }

    public String generateInsertStatement(List<String>recordKeys,List<String>recordValues) {
        String fields = "";
        String values = "";
        String querySubstring="";

        for (int i = 0; i < recordKeys.size(); i++) {

            fields += "`" + recordKeys.get(i) + "`,";
            values +=  "\"" + escapeDoubleQuotes(recordValues.get(i)) + "\",";
        }

        querySubstring = "(" + databaseManager.removeLastCharacter(fields) + ")" +
                "VALUES " + "(" + databaseManager.removeLastCharacter(values) + ")";

        return querySubstring;
    }

    public String escapeDoubleQuotes(String str) {
        return str.replace("\"", "\\\"");
        }
    }

