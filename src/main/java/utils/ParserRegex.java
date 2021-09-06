package utils;

public abstract class ParserRegex {
    public final static String TITLE_AUTHOR_SPLIT = " *-*\\n*\\W\\sby";
    public final static String AND_BY_SPLIT = "and|\\w+ by";
    public final static String LOCATION = "INT\\..+|EXT\\..+";
    public final static String DECONSTRUCT = "\\.| -- |\\. |-- |-";
    public final static String KEYWORD = "[A-Z][A-Z0-9']+( [A-Z][A-Z0-9']+|)+";
    public final static String ON_SPLIT = " ON [A-Z][A-Z0-9']+( [A-Z][A-Z0-9']+|)+";
    public final static String SENTENCE_SPLIT = "\r\n(\t|    )[A-Z][A-Z0-9']+( [A-Z][A-Z0-9']+|)+(\r\n| \r\n)";
}
