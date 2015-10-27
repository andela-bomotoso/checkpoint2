package checkpoint.andela.parser;

public class Main {
    public static void main(String[] args) {
        AttributeValueFile attributeValueFile = new AttributeValueFile();
        attributeValueFile.setFileAddress("C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT");
        FileParser fileParser = new FileParser(attributeValueFile);
        fileParser.readAttributeFile();
        //fileParser.readFile();

    }
}
