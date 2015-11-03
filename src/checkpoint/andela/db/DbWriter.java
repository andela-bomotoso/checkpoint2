package checkpoint.andela.db;

import checkpoint.andela.parser.AttributeValueFile;
import checkpoint.andela.parser.FileParser;
import checkpoint.andela.parser.KeyValuePair;
import checkpoint.andela.thread.Buffer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DbWriter  {

    List<KeyValuePair<String, String>> bufferedFileContent = new ArrayList<KeyValuePair<String, String>>();
    DatabaseManager databaseManager;

    public DbWriter( DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public List<KeyValuePair<String, String>> getBufferedFileContent() {
        return bufferedFileContent;
    }

    public void setBufferedFileContent(List<KeyValuePair<String, String>> bufferedFileContent) {
        this.bufferedFileContent = bufferedFileContent;
    }

    public void writeBufferToDatabase(List<KeyValuePair<String,String>> bufferedFileContent,String databaseName, String tableName, List<String> tableFields,String recordMaker) {
        databaseManager.establishConnection();
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

}

