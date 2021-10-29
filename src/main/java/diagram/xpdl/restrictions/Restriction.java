package diagram.xpdl.restrictions;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;

@NoArgsConstructor
@Setter
public abstract class Restriction {
    private String type;

    @XmlAttribute(name="Type")
    public String getType() {
        return type;
    }
}
