package net.study.tdd.template;

public class Template {

    private String variable;
    private String templateText;

    public Template(String templateText) {
        this.templateText = templateText;
    }

    public void set(String variable, String value) {
        this.variable = value;
    }

    public String evaluate() {
        return templateText.replaceAll("\\$\\{name\\}", variable);
    }
}
