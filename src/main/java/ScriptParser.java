import scenario.ParseException;
import scenario.Scenario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ScriptParser {

    private final String text;
    private final Scenario scenario = new Scenario();
    private final List<String> sceneDelimiters;

    public ScriptParser(String text) {
        this.text = text;
        this.sceneDelimiters = getSceneDelimiters();
    }

    private List<String> getSceneDelimiters() {
        try {
            return Files.readAllLines(Paths.get("scene_delimiters.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Scenario parse() throws ParseException {
        String temp = parseTitleAndAuthors(text);
        System.out.println("Title: " + scenario.getName());
        System.out.println("Authors: " + scenario.getAuthors());
        System.out.println("Remaining text:\n" + temp);
        return scenario;
    }

    private String parseTitleAndAuthors(String text) throws ParseException {
        int splitAt = text.length();
        for (String delimiter : sceneDelimiters) {
            int i = text.indexOf(delimiter);
            splitAt = Math.min(i, splitAt);
        }
        if(splitAt == text.length()) throw new ParseException("No first scene!");

        String titleAndAuthors = text.substring(0, splitAt);
        // Extract title
        String title = titleAndAuthors.split(" *-*\\n*\\W\\sby")[0];
        title = title.replaceAll("\r\n|  +|--+", ""); // remove \n and long space/dash lines
        // Extract authors
        String authors = titleAndAuthors.split(" *-*\\n*\\W\\sby")[1];
        List<String> authorsList = Arrays.asList(authors.split("and|\\w+ by"));
        Set<String> authorsSet = authorsList
                .stream().map(author -> author.replaceAll("  +|\r\n |\r\n", "")) // remove spaces and new lines
                .collect(Collectors.toSet());

        // Save title and authors in scenario object
        scenario.setAuthors(new ArrayList<>(authorsSet));
        scenario.setName(title);
        // Return remaining text
        return text.substring(splitAt);
    }
}
