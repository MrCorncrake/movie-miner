package scenario;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sentence {
    private int shot_id;
    private String character;
    private String notes;
    private String desc;
    private String line;
}
