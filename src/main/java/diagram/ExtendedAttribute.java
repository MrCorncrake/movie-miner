package diagram;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;

@Setter
@Getter
@NoArgsConstructor
public class ExtendedAttribute {
    private String name;
    private String value;

    @XmlAttribute(name="Name")
    public String getName() {
        return name;
    }

    @XmlAttribute(name="Value")
    public String getValue() {
        return value;
    }

    public ExtendedAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
