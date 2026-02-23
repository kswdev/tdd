package net.study.tdd.template.segment;

import java.util.Map;

public interface Segment {
    String evaluate(Map<String, String> variables);
}
