package utils;

public abstract class ParserRegex {
    public final static String TITLE_AUTHOR_SPLIT = " *-*\\n*\\W\\sby";
    public final static String AND_BY_SPLIT = "and|\\w+ by";
    public final static String LOCATION = "INT\\..+|EXT\\..+";
    public final static String DECONSTRUCT = "\\.| -- |\\. |-- |-";
}
