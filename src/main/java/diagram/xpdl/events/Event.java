package diagram.xpdl.events;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;

@Setter
@NoArgsConstructor
public class Event {
    private StartEvent startEvent;
    private EndEvent endEvent;

    @XmlElement(name = "StartEvent", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public StartEvent getStartEvent() {
        return startEvent;
    }

    @XmlElement(name = "EndEvent", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public EndEvent getEndEvent() {
        return endEvent;
    }
}
