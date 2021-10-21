package diagram.events;

import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;

@Setter
public class StartEvent {
    private String trigger;

    public StartEvent() {
        trigger = "None";
    }

    @XmlAttribute(name="Trigger")
    public String getTrigger() {
        return trigger;
    }
}
