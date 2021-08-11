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
    private List<Location> locations = new ArrayList<>();
}
