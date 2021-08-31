import scenario.Location;
import scenario.ParseException;
import scenario.Scenario;
import scenario.Scene;
import utils.Pair;

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

    public ScriptParser(String text) {
        this.text = text;
        this.sceneDelimiters = getDelimiters("scene_delimiters.txt");
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
        System.out.println("Title: " + scenario.getName());
        System.out.println("Authors: " + scenario.getAuthors());

        List<Pair<Integer, String>> scenesList = new ArrayList<>();
        scenesList = parseScenes(temp);
        parseLocations(scenesList);
        //If you want to check the results, put the break point in next line, and run debug mode.
        System.out.println("Ok");
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

    private List<Pair<Integer, String>> parseScenes(String text) {
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

        scenesList.sort((o1, o2) -> {
            if (o1.getDelPos() < o2.getDelPos()) {
                return -1;
            } else if (o1.getDelPos() == o2.getDelPos()) {
                return 0;
            } else {
                return 1;
            }
        });

        for(int i=0; i <scenesList.size(); i++){
            int EndPos = text.length();
            if(i<scenesList.size()-1){
                EndPos = scenesList.get(i+1).getDelPos();
            }
            else
                EndPos = text.length();

            scenesList.get(i).setRest(text.substring(scenesList.get(i).getDelPos(),EndPos ));
            Scene tmp = new Scene();
            tmp.setId(scenesList.get(i).getL());
            tmp.setTransition(scenesList.get(i).getRest());

            scenes.add(tmp);
        }

        scenario.setScenes(scenes);

        return scenesList;
    }

    private void parseLocations(List<Pair<Integer, String>> rawScenes){
        final String regex = "INT\\..+|EXT\\..+";
        final String deconstruct = "\\.| -- |\\. |-- |-";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        List<String> timeCheck = getDelimiters("time_delimiters.txt");
        int sceneIndex = 0;

        //Iterates over raw text for scenes included in the scenario
        for (Pair<Integer, String> scene : rawScenes) {
            List<Location> locations = new ArrayList<>();
            List<String> rawText = new ArrayList<>();
            Matcher matcher = pattern.matcher(scene.getRest());
            while (matcher.find()){
                rawText.add(matcher.group(0));
            }

            String rawScene[] = scene.getRest().split(regex); //raw text for further processing

            //Iterates over lines designating locations in given scene
            for(int i = 0; i < rawText.size(); i++ ){
                String[] parts = rawText.get(i).split(deconstruct); //splitting of line designating location into separate parts
                String position = parts[0];
                String place = new String();
                String time = new String();
                if (timeCheck.contains(parts[parts.length-1])) {
                    time = parts[parts.length - 1];
                    for (int j = 1; j < parts.length - 1; j++) place = String.join(" ", place, parts[j]);
                }
                else{
                    for (int j = 1; j < parts.length; j++) place = String.join(" ", place, parts[j]);;
                }
                locations.add(new Location(position, place, time));
            }
            scenario.getScene(sceneIndex).setLocations(locations);
            sceneIndex++;
        }
    }

}
