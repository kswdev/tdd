package net.study.tdd.template.parse;

import net.study.tdd.template.segment.PlainText;
import net.study.tdd.template.segment.Segment;
import net.study.tdd.template.segment.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateParse {

    public List<Segment> parseSegments(String template) {
        return parse(template);
    }

    private List<Segment> parse(String template) {
        List<Segment> segments = new ArrayList<>();
        int index = collectSegments(segments, template);
        addTail(segments, template, index);
        addEmptyStringIfTemplateWasEmpty(segments);
        return segments;
    }

    private int collectSegments(List<Segment> segments, String src) {
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

    private void addTail(List<Segment> segments, String template, int index) {
        if (index < template.length()) {
            segments.add(new PlainText(template.substring(index)));
        }
    }

    private void addVariable(List<Segment> segments, String src,Matcher m) {
        String segment = src.substring(m.start(), m.end());
        String name = segment.substring(2, segment.length() - 1);
        segments.add(new Variable(name));
    }

    private void addPrecedingPlainText(List<Segment> segments, String src,
                                       Matcher m, int index) {
        if (index != m.start()) {
            segments.add(new PlainText(src.substring(index, m.start())));
        }
    }

    private void addEmptyStringIfTemplateWasEmpty(List<Segment> segments) {
        if (segments.isEmpty()) {
            segments.add(new PlainText(""));
        }
    }
}
