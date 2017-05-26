package opennlp;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.*;
import pojo.Detection;
import trainer.Trainer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OpenNLP {

    public static List<Detection> get(String property, String text) throws IOException {
        String[] tokens = tokenize(text);
        Span[] results = find(property, tokens);
        List<Detection> detections = new ArrayList<>();
        for (Span result : results) {
            String label = "";
            for (int i = result.getStart(); i < result.getEnd(); i++) {
                label += tokens[i];
                if (i < result.getEnd()-1) {
                    label += " ";
                }
            }
            Detection detection = new Detection(label, result);
            detections.add(detection);
        }
        return detections;
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

}
