package scenario;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Location {
    private String position;
    private String place;
    private String time;
    private List<Shot> shots = new ArrayList<>();

    public Location(String position, String place, String time, List<Shot> shots){
        this.position = position;
        this.place = place;
        this.time = time;
        this.shots = shots;
    }
}
