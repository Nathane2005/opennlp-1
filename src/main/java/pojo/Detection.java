package pojo;

import opennlp.tools.util.Span;

public class Detection {
    private String label;
    private Span details;

    public Detection() {}

    public Detection(String label, Span details) {
        this.label = label;
        this.details = details;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Span getDetails() {
        return details;
    }

    public void setDetails(Span details) {
        this.details = details;
    }
}
