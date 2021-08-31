package scenario;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Scenario {
    private String name;
    private List<String> authors = new ArrayList<>();
    private List<String> characters = new ArrayList<>();
    private List<Scene> scenes = new ArrayList<>();

    public Scene getScene(int index){
        return scenes.get(index);
    }
}
