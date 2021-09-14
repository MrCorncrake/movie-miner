import scenario.*;
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
    private final List<String> transitions;
    private final List<String> timeCheck;
    private final Set<String> characterSet = new HashSet<>();

    public ScriptParser(String text) {
        this.text = text;
        this.transitions = getDelimiters("transitions.txt");
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

    private String adjustText(String text) {
        // Moves the entire text to the left and standardizes all indentations
        List<String> lines = new ArrayList<>(Arrays.asList(text.split("\r\n")));
        lines = moveLinesToLeft(lines);
        // Clearing spaces from transitions
        lines = moveTransitionsToLeft(lines);
        // Standardizing indentations
        lines = standardizeIndentations(lines);
        // Put the text together
        StringBuilder adjustedText = new StringBuilder();
        for (String line : lines) adjustedText.append(line).append("\r\n");
        return adjustedText.toString();
    }

    private List<String> moveLinesToLeft(List<String> lines) {
        int spaces = 0;
        boolean done = false;
        while(!done) {
            for(String line : lines) {
                if (!line.substring(spaces).startsWith(" ")) {
                    done = true;
                    break;
                }
            }
            spaces++;
        }
        List<String> linesAdjusted = new ArrayList<>();
        for (String line : lines) linesAdjusted.add(line.substring(spaces - 1));
        return linesAdjusted;
    }

    private List<String> moveTransitionsToLeft(List<String> lines) {
        List<String> linesAdjusted = new ArrayList<>();
        for (String line : lines) {
            String newLine = line;
            if (containsTransition(newLine)) while (newLine.startsWith(" ")) newLine = newLine.substring(1);
            linesAdjusted.add(newLine);
        }
        return linesAdjusted;
    }

    private boolean containsTransition(String line) {
        for (String transition : transitions) if (line.contains(transition)) return true;
        return false;
    }

    private List<String> standardizeIndentations(List<String> lines) {
        SortedSet<Integer> indentations = new TreeSet<>();
        for (String line : lines) {
            if (line.startsWith(" ")) {
                int i = 1;
                while(line.substring(i).startsWith(" ")) i++;
                indentations.add(i);
            }
        }
        int i = 1;
        for (Integer indentation : indentations) {
            List<String> linesAdjusted = new ArrayList<>();
            StringBuilder indent = new StringBuilder();
            for (int j = 0; j < indentation; j++) indent.append(" ");
            for (String line : lines) {
                if (line.length() > indentation && line.startsWith(indent.toString()) && line.charAt(indentation) != ' ') {
                    linesAdjusted.add(line.substring(indentation - i));
                }
                else linesAdjusted.add(line);
            }
            lines = linesAdjusted;
            i++;
        }
        return lines;
    }

    public Scenario parse() throws ParseException {
        String temp;
        temp = adjustText(text);
        temp = parseTitleAndAuthors(temp);
        parseScenes(temp);
        scenario.setCharacters(new ArrayList<>(characterSet));
        return scenario;
    }

    private String parseTitleAndAuthors(String text) {
        int splitAt = text.indexOf("\r\n");
        // Extract title
        String title = text.substring(0, splitAt);
        title = title.replaceAll(ParserRegex.CLEAR_TITLE, ""); // remove \n, " and long space/dash lines
        // Extract authors
        String reminder = text.substring(splitAt).split("by", 2)[1];
        String authors = reminder.split("\r\n\\S", 2)[0];
        List<String> authorsList = Arrays.asList(authors.split(ParserRegex.AUTHOR_SPLIT));
        Set<String> authorsSet = authorsList
                .stream().map(author -> author.replaceAll(ParserRegex.CLEAR_AUTHOR, ""))  // remove spaces and new lines
                .collect(Collectors.toSet());
        // Clear potential artefacts
        authorsSet.remove("");
        // Save title and authors in scenario object
        scenario.setAuthors(new ArrayList<>(authorsSet));
        scenario.setName(title);
        // Return remaining text
        return reminder.substring(authors.length());
    }

    private void parseScenes(String text) throws ParseException {
        List<Scene> scenes = new ArrayList<>();
        List<String> lines = new ArrayList<>(Arrays.asList(text.split("\r\n")));
        lines.remove(0); // Remove ""

        int sceneNumber = 1;
        List<String> sceneLines = new ArrayList<>();
        String transit = "";
        for (String line : lines) {
            if (containsTransition(line)) {
                if (!sceneLines.isEmpty()) {
                    scenes.add(parseScene(sceneNumber, transit, sceneLines));
                    sceneNumber++;
                    sceneLines = new ArrayList<>();
                }
                transit = line;
            } else {
                if (line.contains("INT.") || line.contains("EXT.")) {
                    if (!sceneLines.isEmpty()) {
                        scenes.add(parseScene(sceneNumber, transit, sceneLines));
                        sceneNumber++;
                        sceneLines = new ArrayList<>();
                        transit = "";
                    }
                }
                sceneLines.add(line);
            }
        }
        scenario.setScenes(scenes);
    }

    private Scene parseScene(int id, String transition, List<String> lines)  throws ParseException  {
        // ID and transition
        Scene scene = new Scene(id, transition);
        // Location: position, place and time
        parseLocation(scene, lines.remove(0));
        // Shots
        StringBuilder shotsString = new StringBuilder();
        for (String line : lines) shotsString.append(line).append("\r\n");
        scene.setShots(parseShots(shotsString.toString()));
        return scene;
    }

    private void parseLocation(Scene scene, String locationString) {
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
        scene.setPosition(position);
        scene.setPlace(place.replaceAll(" {2}", ""));
        scene.setTime(time);
    }

    private List<Shot> parseShots(String text) throws ParseException {
        // List of "on" shots
        List<String> onList = extractDelimiters(text, ParserRegex.ON_SPLIT);
        onList.add(0, "LOCATION"); // For default shot

        String[] rawShots = text.split(ParserRegex.ON_SPLIT); // Raw shots text (contains default shot text as well as text for every "on" shot)

        List<Shot> shots = new ArrayList<>();
        for (int i = 0; i < rawShots.length; i++) shots.add(parseShot(onList.get(i), rawShots[i], i));
        return shots;
    }

    private Shot parseShot(String on, String text, int shotId) throws ParseException {
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
        try {
            for (int i = 0; i < characters.size(); i++)
                sentences.add(parseSentence(characters.get(i), rawSentences.get(i), shotId));
        } catch (IndexOutOfBoundsException ex) {
            throw new ParseException("Unknown transition:\n" + text);
        }
        // Setting sentences
        shot.setSentences(sentences);

        return shot;
    }

    private Sentence parseSentence(String character, String text, int shotId) {
        Sentence sentence = new Sentence(shotId);
        sentence.setCharacter(character.replaceAll(ParserRegex.CLEAR_CHARACTER, ""));
        characterSet.add(sentence.getCharacter());
        String line = text.split(ParserRegex.LINE_SPLIT)[0];
        String followup = clearString(text.substring(line.length()));
        sentence.setLine(clearString(line));
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
        String cleared = text.replaceAll("\r\n", " ");
        cleared = cleared.replaceAll(" {2}", " ");
        while (cleared.startsWith(" ")) cleared = cleared.substring(1);
        return cleared;
    }
}
