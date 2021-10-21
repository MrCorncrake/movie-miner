package diagram.events;

import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;

@Setter
public class EndEvent {
    private String result;

    public EndEvent() {
        result = "None";
    }

    @XmlAttribute(name="Result")
    public String getResult() {
        return result;
    }
}
