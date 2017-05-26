package trainer;

import opennlp.tools.namefind.*;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class Trainer {
    public static final String ENCODING = "UTF-8";

    public static void train(final String inputTrainFile, String outputTrainedFile, String property)
            throws IOException {
        Charset charset = Charset.forName(ENCODING);
        InputStreamFactory trainingInputStream = new InputStreamFactory() {
            public InputStream createInputStream() throws IOException {
                return Trainer.class.getResourceAsStream("/" + inputTrainFile);
            }
        };
        ObjectStream<String> trainingLineStream = new PlainTextByLineStream(trainingInputStream, charset);
        ObjectStream<NameSample> trainingSampleStream = new NameSampleDataStream(trainingLineStream);
        TokenNameFinderModel model = null;
        try {
            model = NameFinderME.train("fr", property, trainingSampleStream,
                    new TrainingParameters(), new TokenNameFinderFactory());
        } finally {
            trainingSampleStream.close();
        }
        BufferedOutputStream modelOut = null;
        try {
            modelOut = new BufferedOutputStream(
                    new FileOutputStream("src/main/resources/" + outputTrainedFile));
            model.serialize(modelOut);
        } finally {
            if (modelOut != null) {
                modelOut.close();
            }
        }
    }

}
