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
}
