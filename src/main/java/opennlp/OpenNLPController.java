package opennlp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pojo.Detection;

import java.io.IOException;
import java.util.List;

@RestController
public class OpenNLPController {

    @RequestMapping("/color")
    public List<Detection> color(@RequestParam(value="text") String text) throws IOException {
        List<Detection> colors = OpenNLP.get("color", text);
        return colors;
    }

    @RequestMapping("/product")
    public List<Detection> product(@RequestParam(value="text") String text) throws IOException {
        List<Detection> products = OpenNLP.get("product", text);
        return products;
    }
}
