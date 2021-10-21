package diagram;

import diagram.events.Event;
import diagram.implementations.Implementation;
import diagram.infos.NodeGraphicsInfo;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;

@Setter
@NoArgsConstructor
public class Activity {

    private String Id;
    private String name;
    private Event event;
    private Implementation implementation;
    private ArrayList<String> performersList;
    private ArrayList<NodeGraphicsInfo> nodeGraphicsInfosList;
    private ArrayList<TransitionRestriction> transitionRestrictionsList;

    @XmlAttribute(name="Id")
    public String getId() {
        return Id;
    }

    @XmlAttribute(name="Name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "Event", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public Event getEvent() {
        return event;
    }

    @XmlElement(name = "Implementation", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public Implementation getImplementation() {
        return implementation;
    }

    public ArrayList<String> getPerformersList() {
        return performersList;
    }

    @XmlElementWrapper(name = "Performers", namespace="http://www.wfmc.org/2008/XPDL2.1")

    @XmlElement(name = "Performer", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public void setPerformersList(ArrayList<String> performersList) {
        this.performersList = performersList;
    }

    public ArrayList<TransitionRestriction> getTransitionRestrictionsList() {
        return transitionRestrictionsList;
    }

    @XmlElementWrapper(name = "TransitionRestrictions", namespace="http://www.wfmc.org/2008/XPDL2.1")

    @XmlElement(name = "TransitionRestriction", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public void setTransitionRestrictionsList(ArrayList<TransitionRestriction> transitionRestrictionsList) {
        this.transitionRestrictionsList = transitionRestrictionsList;
    }

    public ArrayList<NodeGraphicsInfo> getNodeGraphicsInfosList() {
        return nodeGraphicsInfosList;
    }

    @XmlElementWrapper(name = "NodeGraphicsInfos", namespace="http://www.wfmc.org/2008/XPDL2.1")

    @XmlElement(name = "NodeGraphicsInfo", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public void setNodeGraphicsInfosList(ArrayList<NodeGraphicsInfo> nodeGraphicsInfosList) {
        this.nodeGraphicsInfosList = nodeGraphicsInfosList;
    }
}
