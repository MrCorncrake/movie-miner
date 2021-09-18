package scenario;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Shot {
    private int id;
    private String on;
    private String desc;
    private List<Sentence> sentences = new ArrayList<>();
    private List<String> key_words = new ArrayList<>();

    public Shot(int id) {
        this.id = id;
    }
}
