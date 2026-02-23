package net.study.tdd.template.segment;

import java.util.Map;

public record PlainText(String text) implements Segment {

    @Override
    public String evaluate(Map<String, String> variables) {
        return text;
    }
}
