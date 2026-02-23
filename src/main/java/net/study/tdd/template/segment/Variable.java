package net.study.tdd.template.segment;

import net.study.tdd.template.error.MissingValueException;

import java.util.Map;

public record Variable(String name) implements Segment {

    @Override
    public String evaluate(Map<String, String> variables) {
        if (!variables.containsKey(name)) {
            throw new MissingValueException("No value for " + name);
        }
        return variables.get(name);
    }
}
