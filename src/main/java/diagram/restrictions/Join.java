package diagram.restrictions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Join extends Restriction {
    public Join(String type) {
        this.setType(type);
    }
}
