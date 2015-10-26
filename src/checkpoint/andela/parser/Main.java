package checkpoint.andela.parser;

/**
 * Created by GRACE on 10/24/2015.
 */
public class Main {
    public static void main(String[] args) {
        AttributeValueFile attributeValueFile = new AttributeValueFile();
        attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");
        FileParser fileParser = new FileParser(attributeValueFile);
        fileParser.readFile();
    }
}
