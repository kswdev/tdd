package net.study.tdd.template.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateParse {

    public List<String> parse(String template) {
        List<String> segments = new ArrayList<>();
        int index = collectSegments(segments, template);
        addTail(segments, template, index);
        addEmptyStringIfTemplateWasEmpty(segments);
        return segments;
    }

    private int collectSegments(List<String> segments, String src) {
        Pattern pattern = Pattern.compile("\\$\\{[^}]*\\}");
        Matcher matcher = pattern.matcher(src);
        int index = 0;
        while (matcher.find()) {
            addPrecedingPlainText(segments, src, matcher, index);
            addVariable(segments, src, matcher);
            index = matcher.end();
        }
        return index;
    }

    private void addTail(List<String> segments, String template, int index) {
        if (index < template.length()) {
            segments.add(template.substring(index));
        }
    }

    private void addVariable(List<String> segments, String src,Matcher m) {
        segments.add(src.substring(m.start(), m.end()));
    }

    private void addPrecedingPlainText(List<String> segments, String src,
                                       Matcher m, int index) {
        if (index != m.start()) {
            segments.add(src.substring(index, m.start()));
        }
    }
    private void addEmptyStringIfTemplateWasEmpty(List<String> segments) {
        if (segments.isEmpty()) {
            segments.add("");
        }
    }
}
