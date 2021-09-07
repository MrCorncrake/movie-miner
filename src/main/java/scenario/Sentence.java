package scenario;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sentence {
    private int shot_id;
    private String character;
    private String line;
    private String followup;

    public Sentence(int shot_id) {
        this.shot_id = shot_id;
    }
}
