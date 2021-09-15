package utils;

public class ReaderException extends Exception {
    public ReaderException(String message) {
        super("Error with PDF file: " + message);
    }
}
