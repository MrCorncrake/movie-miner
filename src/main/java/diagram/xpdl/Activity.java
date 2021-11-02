package diagram.xpdl;

import diagram.xpdl.infos.NodeGraphicsInfo;
import lombok.AllArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;

@Setter
public class Activity {

    private String id;
    private String name;
    private NoImplementation implementation = null;
    private EventWrapper event = null;
    private ArrayList<String> performersList = new ArrayList<>();
    private ArrayList<NodeGraphicsInfo> nodeGraphicsInfosList = new ArrayList<>();
    private ArrayList<TransitionRestriction> transitionRestrictionsList = new ArrayList<>();

    public Activity(String id, String name) {
        this.id = id;
        this.name = name;
        setType(Type.NO_IMPLEMENTATION);
    }

    public void setType(Type type) {
        switch (type){
            case NO_IMPLEMENTATION:
                implementation = new NoImplementation();
                event = null;
                break;
            case START_EVENT:
                implementation = null;
                event = new EventWrapper(new StartEvent(), null);
                break;
            case END_EVENT:
                implementation = null;
                event = new EventWrapper(null, new EndEvent());
                break;
        }
    }

    @XmlAttribute(name="Id")
    public String getId() {
        return id;
    }

    @XmlAttribute(name="Name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "Event", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public EventWrapper getEvent() {
        return event;
    }

    @XmlElement(name = "Implementation", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public NoImplementation getImplementation() {
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

    @Setter
    private static class NoImplementation {
        private No no;

        public NoImplementation() {
            no = new No();
        }

        @XmlElement(name = "No", namespace="http://www.wfmc.org/2008/XPDL2.1")
        public No getNo() {
            return no;
        }

        private static class No {
        }
    }

    @Setter
    private static class StartEvent {
        private String trigger;

        public StartEvent() {
            trigger = "None";
        }

        @XmlAttribute(name="Trigger")
        public String getTrigger() {
            return trigger;
        }
    }

    @Setter
    private static class EndEvent {
        private String result;

        public EndEvent() {
            result = "None";
        }

        @XmlAttribute(name="Result")
        public String getResult() {
            return result;
        }
    }

    @AllArgsConstructor
    @Setter
    private static class EventWrapper {
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

    public enum Type {
        NO_IMPLEMENTATION,
        START_EVENT,
        END_EVENT
    }
}
