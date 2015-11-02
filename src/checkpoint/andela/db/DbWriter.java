package checkpoint.andela.db;

import checkpoint.andela.parser.AttributeValueFile;
import checkpoint.andela.parser.FileParser;
import checkpoint.andela.parser.KeyValuePair;

import java.util.ArrayList;
import java.util.List;

public class DbWriter implements Runnable {

    private String recordMaker;
    List<KeyValuePair<String, String>> bufferedFileContent = new ArrayList<KeyValuePair<String, String>>();
    DatabaseManager databaseManager;
    List<String> tableFields;


    public DbWriter(List<KeyValuePair<String, String>> bufferedFileContent, DatabaseManager databaseManager, List<String> tableFields, String recordMaker) {
        this.bufferedFileContent = bufferedFileContent;
        this.databaseManager = databaseManager;
        this.tableFields = tableFields;
        this.recordMaker = recordMaker;
    }

    public void writeBufferToDatabase(String databaseName, String tableName, List<String> tableFields) {


        List<String> recordKeys = new ArrayList<>();
        List<String> recordValues = new ArrayList<>();

        for (KeyValuePair<String, String> pair : bufferedFileContent) {

            if (!pair.key.equals(recordMaker)) {

                if ((!recordKeys.contains(pair.key)) && tableFields.contains(pair.key)) {

                    recordKeys.add(pair.key);
                    recordValues.add(pair.value);
                } else if (tableFields.contains(pair.key)) {
                    modifyExistingField(recordKeys, recordValues, pair.key, pair.value);
                }
            } else if (!recordKeys.isEmpty()) {
                String sql = "INSERT INTO " + databaseName + "." + tableName + generateInsertStatement(recordKeys, recordValues);
                databaseManager.runQuery(sql);
                recordKeys.clear();
                recordValues.clear();
            }
        }
    }

    public void modifyExistingField(List<String> keys, List<String> values, String currentKey, String currentValue) {
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            index = i;
            if (keys.get(index).equals(currentKey)) {
                break;
            }
        }
        values.set(index, values.get(index) + "," + currentValue);
    }

    public String generateInsertStatement(List<String> recordKeys, List<String> recordValues) {
        String fields = "";
        String values = "";
        String querySubstring = "";

        for (int i = 0; i < recordKeys.size(); i++) {

            fields += "`" + recordKeys.get(i) + "`,";
            values += "\"" + escapeDoubleQuotes(recordValues.get(i)) + "\",";
        }

        querySubstring = "(" + databaseManager.removeLastCharacter(fields) + ") " +
                "VALUES " + "(" + databaseManager.removeLastCharacter(values) + ")";

        return querySubstring;
    }

    public String escapeDoubleQuotes(String str) {
        return str.replace("\"", "\\\"");
    }


    public void run() {

    }
}

