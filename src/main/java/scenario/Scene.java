package scenario;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Scene {
    private Integer id;
    private String transition;
    private String position;
    private String place;
    private String time;
    private List<Shot> shots = new ArrayList<>();

    public Scene(Integer id, String transition) {
        this.id = id;
        this.transition = transition;
    }
}
