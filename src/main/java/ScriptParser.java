import scenario.Location;
import scenario.ParseException;
import scenario.Scenario;
import scenario.Scene;
import utils.Pair;
import utils.ParserRegex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptParser {

    private final String text;
    private final Scenario scenario = new Scenario();
    private final List<String> sceneDelimiters;
    private final List<String> timeCheck;

    public ScriptParser(String text) {
        this.text = text;
        this.sceneDelimiters = getDelimiters("scene_delimiters.txt");
        this.timeCheck = getDelimiters("time_delimiters.txt");
    }

    private List<String> getDelimiters(String filename) {
        try {
            return Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Scenario parse() throws ParseException {
        String temp = parseTitleAndAuthors(text);
        parseScenes(temp);
        return scenario;
    }

    private String parseTitleAndAuthors(String text) throws ParseException {
        int splitAt = text.length();
        for (String delimiter : sceneDelimiters) {
            int i = text.indexOf(delimiter);
            splitAt = Math.min(i, splitAt);
        }
        if(splitAt == text.length()) throw new ParseException("No first scene!");
        System.out.println(splitAt);
        String titleAndAuthors = text.substring(0, splitAt);
        System.out.println(titleAndAuthors);
        // Extract title
        String title = titleAndAuthors.split(ParserRegex.TITLE_AUTHOR_SPLIT)[0];
        title = title.replaceAll("\r\n|  +|--+", ""); // remove \n and long space/dash lines
        // Extract authors
        String authors = titleAndAuthors.split(ParserRegex.TITLE_AUTHOR_SPLIT)[1];
        List<String> authorsList = Arrays.asList(authors.split(ParserRegex.AND_BY_SPLIT));
        Set<String> authorsSet = authorsList
                .stream().map(author -> author.replaceAll("  +|\r\n |\r\n", "")) // remove spaces and new lines
                .collect(Collectors.toSet());

        // Save title and authors in scenario object
        scenario.setAuthors(new ArrayList<>(authorsSet));
        scenario.setName(title);
        // Return remaining text
        return text.substring(splitAt);
    }

    private void parseScenes(String text) throws ParseException {
        List<Pair<Integer, String>> scenesList = new ArrayList<>();
        List<Scene> scenes = new ArrayList<>();

        int count = 0;
        for (String delimiter : sceneDelimiters) {
            for (int i = -1; (i = text.indexOf(delimiter, i + 1)) != -1; i++) {
                count++;
                Pair<Integer, String> tmp = new Pair<>(count, delimiter);
                tmp.setDelPos(i);
                scenesList.add(tmp);
            }
        }
        scenesList.sort(Comparator.comparingInt(Pair::getDelPos));

        for (int i=0; i <scenesList.size(); i++){
            int EndPos;
            if (i<scenesList.size()-1) {
                EndPos = scenesList.get(i+1).getDelPos();
            } else
                EndPos = text.length();

            // Remaining text containing locations
            String locationsString = text.substring(scenesList.get(i).getDelPos(),EndPos );

            // Extracting transition string
            String transition = null;
            for (String delimiter: sceneDelimiters) {
                if (locationsString.startsWith(delimiter)) {
                    transition = delimiter;
                    break;
                }
            }
            if (transition == null) throw new ParseException("Unknown scene delimiter! Scene no.: " + i);

            Scene tmp = new Scene();
            tmp.setId(scenesList.get(i).getL());
            tmp.setTransition(transition);
            tmp.setLocations(parseLocations(locationsString)); // Further parsing

            scenes.add(tmp);
        }

        scenario.setScenes(scenes);
    }

    private List<Location> parseLocations(String locationsString) {
        final Pattern pattern = Pattern.compile(ParserRegex.LOCATION, Pattern.MULTILINE);
        List<Location> locations = new ArrayList<>();
        List<String> rawText = new ArrayList<>();
        Matcher matcher = pattern.matcher(locationsString);
        while (matcher.find()){
            rawText.add(matcher.group(0));
        }

        String rawScene[] = locationsString.split(ParserRegex.LOCATION); //raw text for further processing

        //Iterates over lines designating locations in given scene
        for (String s : rawText) {
            locations.add(parseLocation(s));
        }

        return locations;
    }

    private Location parseLocation(String locationString) {
        String[] parts = locationString.split(ParserRegex.DECONSTRUCT); //splitting of line designating location into separate parts
        String position = parts[0];
        String place = "";
        String time = "";
        if (timeCheck.contains(parts[parts.length - 1])) {
            time = parts[parts.length - 1];
            for (int j = 1; j < parts.length - 1; j++) place = String.join(" ", place, parts[j]);
        } else {
            for (int j = 1; j < parts.length; j++) place = String.join(" ", place, parts[j]);
        }
        return new Location(position, place, time);
    }

}
