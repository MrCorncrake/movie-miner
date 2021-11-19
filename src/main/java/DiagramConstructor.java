import diagram.builders.DiagramBuilder;
import diagram.builders.LaneBuilder;
import diagram.xpdl.Package;
import scenario.Scenario;
import scenario.Scene;
import scenario.Shot;
import utils.DiagramException;

import java.util.*;

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

    public void buildDiagram() throws DiagramException {
        for (Scene scene : scenario.getScenes()) {
            Set<String> characters = new HashSet<>();
            for (Shot shot : scene.getShots()) {
                characters.addAll(shot.getKey_words());
                characters.removeIf((String character) -> !scenario.getCharacters().contains(character));
            }
            addActivities(scene.getId(), new ArrayList<>(characters));
            connectCharactersAt(scene.getId(), new ArrayList<>(characters));
        }
        endLines();
    }

    private void addActivities(int sceneId, List<String> characters) throws DiagramException {
        for (String character : characters) {
            if (!lanes.containsKey(character)) {
                LaneBuilder lane = diagram.addLane(character);
                lanes.put(character, lane);
                lanesOrder.put(character, ++laneCounter);
                if (!lane.addStartActivity(sceneId - 1, character)) throw new DiagramException("Could not create start activity at " + sceneId);
            }
            LaneBuilder lane = lanes.get(character);
            if (!lane.addActivity(sceneId, "Scene " + sceneId + " - " + character)) throw new DiagramException("Could not create activity at " + sceneId);
        }
    }

    private void connectCharactersAt(int sceneId, List<String> characters) throws DiagramException {
        characters.sort(Comparator.comparing(lanesOrder::get));
        for (int i = 1; i < characters.size(); i++) {
            LaneBuilder lane1 = lanes.get(characters.get(i - 1));
            LaneBuilder lane2 = lanes.get(characters.get(i));
            if (!lane1.connectToLaneAt(lane2, sceneId)) throw new DiagramException("Could not connect lanes at " + sceneId);
        }
    }

    private void endLines() throws DiagramException {
        for (String character : lanes.keySet()) {
            LaneBuilder lane = lanes.get(character);
            int pos = lane.getLastActivity().getPosition();
            if(!lane.addEndActivity(pos + 1)) throw new DiagramException("Could not create end activity at " + pos + 1);
        }
    }
}
