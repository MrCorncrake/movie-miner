package scenario;

public class ParseException extends Exception {
    public ParseException(String message) {
        super("The script doesn't follow required structure: " + message);
    }
}
