import scenario.ParseException;
import scenario.Scenario;
import scenario.Scene;

import utils.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ScenesExtractor {

    private final String text;
    private final Scenario scenario;
    private final Scene scene = new Scene();
    private final List<String> sceneDelimiters;

    public ScenesExtractor(String text, Scenario scenario) {
        this.text = text;
        this.scenario = scenario;
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

    public Scene parse() throws ParseException {
        String temp_2 = parseScenes(text);
        System.out.println("Id: " + scene.getId());
        System.out.println("transition: " + scene.getId());
        System.out.println("Remaining text:\n" + temp_2);
        return scene;
    }

    private String parseScenes(String text) {
        List<Pair<Integer, String>> scenesList = new ArrayList<>();
        List<Scene> scenes = new ArrayList<>();
        int count = 0;
        for (String delimiter : sceneDelimiters) {
            for (int i = -1; (i = text.indexOf(delimiter, i + 1)) != -1; i++) {
                count++;
                Pair<Integer, String> tmp = new Pair<>(count, delimiter);
                scenesList.add(tmp);
            }
        }

        scenesList.sort((o1, o2) -> {
            if (o1.getL() < o2.getL()) {
                return -1;
            } else if (o1.getL().equals(o2.getL())) {
                return 0;
            } else {
                return 1;
            }
        });

        for(Pair<Integer, String>a:scenesList){
            Scene tmp = new Scene();
            tmp.setId(a.getL());
            tmp.setTransition(a.getR());
            scenes.add(tmp);
        }

        scenario.setScenes(scenes);

        return "a";
    }
}
