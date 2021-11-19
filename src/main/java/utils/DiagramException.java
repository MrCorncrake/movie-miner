package utils;

public class DiagramException extends Exception {
    public DiagramException(String message) {
        super("Could not create diagram. The following error occurred: " + message);
    }
}
