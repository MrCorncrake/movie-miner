import scenario.Scenario;

public class ScriptParser {

    private final String text;
    private final Scenario scenario = new Scenario();

    public ScriptParser(String text) {
        this.text = text;
    }

    public Scenario parse() {
        System.out.println(text);
        return scenario;
    }
}
