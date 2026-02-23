package net.study.tdd.template.segment;

import java.util.Map;

public record Variable(String name) implements Segment {

    @Override
    public String evaluate(Map<String, String> variables) {
        return variables.get(name);
    }
}
