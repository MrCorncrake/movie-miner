package utils;

public abstract class ParserRegex {
    public final static String KEYWORD = "[A-Z][A-Z0-9']+( [A-Z][A-Z0-9'-]+|)+";
    public final static String CLEAR_TITLE = "\r\n|  +|--+|\"";
    public final static String CLEAR_AUTHOR = "  +|\r\n |\r\n";
    public final static String CLEAR_CHARACTER = "\r\n(\t|  +)| \r\n|\r\n| \\([^)]+\\)";
    public final static String AUTHOR_SPLIT = "[ \r\n]and| *& *|\\w+ by|\r\n";
    public final static String LOCATION_SPLIT = " *-+ +";
    public final static String ON_SPLIT = "\r\nON [A-Z][A-Z0-9']+( [A-Z][A-Z0-9'-]+|)+";
    public final static String SENTENCE_SPLIT = "\r\n(  +)[A-Z][A-Z0-9'-]+( .*|)+(\r\n| \r\n)";
    public final static String LINE_SPLIT = " *\r\n[A-Z]";
}
