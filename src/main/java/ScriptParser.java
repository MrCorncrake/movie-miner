import scenario.*;
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
        String titleAndAuthors = text.substring(0, splitAt);
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
        List<Location> locations = new ArrayList<>();
        List<String> rawText = extractDelimiters(locationsString, ParserRegex.LOCATION);
        List<String> rawScene = new ArrayList<>(Arrays.asList(locationsString.split(ParserRegex.LOCATION))); //raw text for further processing
        rawScene.remove(0);

        //Iterates over lines designating locations in given scene
        for (int i = 0; i < rawText.size(); i++) {
            locations.add(parseLocation(rawText.get(i), rawScene.get(i)));
        }

        return locations;
    }

    private Location parseLocation(String locationString, String shotsString) {
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

        List<Shot> shots = parseShots(shotsString);
        return new Location(position, place.replaceAll(" {2}", ""), time, shots);
    }

    private List<Shot> parseShots(String text) {
        // List of "on" shots
        List<String> onList = extractDelimiters(text, ParserRegex.ON_SPLIT);
        onList.add(0, "LOCATION"); // For default shot

        String[] rawShots = text.split(ParserRegex.ON_SPLIT); // Raw shots text (contains default shot text as well as text for every "on" shot)

        List<Shot> shots = new ArrayList<>();
        for (int i = 0; i < rawShots.length; i++) shots.add(parseShot(onList.get(i), rawShots[i], i));
        return shots;
    }

    private Shot parseShot(String on, String text, int shotId) {
        Shot shot = new Shot(shotId);

        // Setting "on"
        shot.setOn(clearString(on));
        // Setting list of keywords (removing duplicates)
        shot.setKey_words(new ArrayList<>(new HashSet<>(extractDelimiters(text, ParserRegex.KEYWORD))));
        // Creating list containing characters that speak sentences
        List<String> characters = extractDelimiters(text, ParserRegex.SENTENCE_SPLIT);
        // Creating list containing shot desc on index 0 and sentences to be parsed
        List<String> rawSentences = new ArrayList<>(Arrays.asList(text.split(ParserRegex.SENTENCE_SPLIT)));
        // Setting shot desc
        shot.setDesc(clearString(rawSentences.remove(0)));
        // Parsing sentences
        List<Sentence> sentences = new ArrayList<>();
        for (int i = 0; i < characters.size() ; i++) sentences.add(parseSentence(characters.get(i), rawSentences.get(i), shotId));
        // Setting sentences
        shot.setSentences(sentences);

        return shot;
    }

    private Sentence parseSentence(String character, String text, int shotId) {
        Sentence sentence = new Sentence(shotId);
        sentence.setCharacter(character.replaceAll(ParserRegex.CLEAR_CHARACTER, ""));
        String line = text.split(" \r\n [A-Z]")[0];
        String followup = clearString(text.substring(line.length()));
        sentence.setLine(clearString(text));
        sentence.setFollowup(followup);
        return sentence;
    }

    private List<String> extractDelimiters(String text, String regex) {
        Matcher matcher = Pattern.compile(regex, Pattern.MULTILINE).matcher(text);
        List<String> delimiters = new ArrayList<>();
        while(matcher.find()) delimiters.add(matcher.group(0));
        return delimiters;
    }

    private String clearString(String text) {
        String cleared = text.replaceAll("\r\n ", " ");
        cleared = cleared.replaceAll(" {2}", " ");
        while (cleared.startsWith(" ")) cleared = cleared.substring(1);
        return cleared;
    }
}
