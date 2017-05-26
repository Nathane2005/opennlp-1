import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.*;
import trainer.Trainer;

import java.io.*;

public class OpenNLP {

    public static final String CHAR_ENCODING = "UTF-8";

    public static void main(String[] args) throws IOException {
        String text = "Salut, je voudrais un vélo rouge foncé";

        String[] tokens = tokenize(text);

        Span[] colors = find("color", tokens);
        Span[] products = find("product", tokens);

        for(Span color : colors) {
            System.out.println("color : " + tokens[color.getStart()]);
        }

        for(Span product : products) {
            System.out.println("product : " + tokens[product.getStart()]);
        }
    }

    private static String[] tokenize(String sentence) {
        WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE;
        return whitespaceTokenizer.tokenize(sentence);
    }

    public static Span[] find(String property, String[] tokens) throws IOException {
        String inputTrainFile = "fr-ner-" + property + ".train";
        String outputTrainedFile = "fr-ner-" + property + ".bin";
        Trainer.train(inputTrainFile, outputTrainedFile, property);
        InputStream modelStream = OpenNLP.class.getResourceAsStream("/" + outputTrainedFile);
        TokenNameFinderModel model = new TokenNameFinderModel(modelStream);
        modelStream.close();

        NameFinderME finder = new NameFinderME(model);

        return finder.find(tokens);
    }

    public static Span[] findColor(String[] tokens) throws IOException {
        Trainer.train("fr-ner-color.train", "fr-ner-color.bin", "color");
        InputStream colorModelStream = OpenNLP.class.getResourceAsStream("/fr-ner-color.bin");
        TokenNameFinderModel colorModel = new TokenNameFinderModel(colorModelStream);
        colorModelStream.close();

        NameFinderME colorFinder = new NameFinderME(colorModel);

        return colorFinder.find(tokens);
    }

    public static Span[] findProduct(String[] tokens) throws IOException {
        Trainer.train("fr-ner-product.train", "fr-ner-product.bin", "product");
        InputStream productModelStream = OpenNLP.class.getResourceAsStream("/fr-ner-product.bin");
        TokenNameFinderModel productModel = new TokenNameFinderModel(productModelStream);
        productModelStream.close();

        NameFinderME productFinder = new NameFinderME(productModel);

        return productFinder.find(tokens);
    }
}
