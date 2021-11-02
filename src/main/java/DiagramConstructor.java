import diagram.builders.DiagramBuilder;
import diagram.builders.LaneBuilder;
import diagram.xpdl.Package;
import lombok.Getter;
import scenario.Scenario;
import scenario.Scene;
import scenario.Shot;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class DiagramConstructor {

    private final DiagramBuilder diagram;
    private final Scenario scenario;
    private final HashMap<String, LaneBuilder> lanes = new HashMap<>();
    private final HashMap<String, Integer> lanesOrder = new HashMap<>();

    private Integer laneCounter = 0;

    public DiagramConstructor(Scenario scenario) {
        this.scenario = scenario;
        this.diagram = new DiagramBuilder(scenario.getName().replace(" ", "_"), scenario.getName() + " Package", scenario.getName());
    }

    public Package getDiagram() {
        return diagram.getDiagram();
    }

    public void buildDiagram() {
        for (Scene scene : scenario.getScenes()) {
            for (Shot shot : scene.getShots()) {
                List<String> characters = shot.getKey_words();
                characters.removeIf((String character) -> !scenario.getCharacters().contains(character));
                addActivities(scene.getId(), characters);
                connectCharactersAt(scene.getId(), characters);
            }
        }
        endLines();
    }

    private void addActivities(int sceneId, List<String> characters) {
        for (String character : characters) {
            if (!lanes.containsKey(character)) {
                LaneBuilder lane = diagram.addLane(character);
                lanes.put(character, lane);
                lanesOrder.put(character, ++laneCounter);
                lane.addStartActivity(sceneId - 1, character);
            }
            LaneBuilder lane = lanes.get(character);
            lane.addActivity(sceneId, "Scene " + sceneId + " - " + character);
        }
    }

    private void connectCharactersAt(int sceneId, List<String> characters) {
        characters.sort(Comparator.comparing(lanesOrder::get));
        for (int i = 1; i < characters.size(); i++) {
            LaneBuilder lane1 = lanes.get(characters.get(i - 1));
            LaneBuilder lane2 = lanes.get(characters.get(i));
            lane1.connectToLaneAt(lane2, sceneId);
        }
    }

    private void endLines() {
        for (String character : lanes.keySet()) {
            LaneBuilder lane = lanes.get(character);
            int pos = lane.getLastActivity().getPosition();
            lane.addEndActivity(pos + 1);
        }
    }
}
