package diagram.xpdl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;

@Setter
@NoArgsConstructor
public class TransitionRestriction {

    private Split split;
    private Join join;

    @XmlElement(name = "Split", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public Split getSplit() {
        return split;
    }

    @XmlElement(name = "Join", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public Join getJoin() {
        return join;
    }

    public void setJoin(String type) {
        if(type == null) this.join = null;
        else this.join = new Join(type);
    }

    public void setSplit(String type, ArrayList<String> transitions) {
        if(type == null) this.split = null;
        else this.split = new Split(type, transitions);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    private static class Join {
        private String type;

        @XmlAttribute(name="Type")
        public String getType() {
            return type;
        }
    }

    @NoArgsConstructor
    @Setter
    private static class Split {

        private String type;
        private ArrayList<TransitionRef> transitionRefsList = new ArrayList<>();

        public Split(String type, ArrayList<String> transitions) {
            this.setType(type);

            for(String transition : transitions) {
                transitionRefsList.add(new TransitionRef(transition));
            }
        }

        @XmlAttribute(name="Type")
        public String getType() {
            return type;
        }

        @XmlElementWrapper(name = "transitionRefs", namespace="http://www.wfmc.org/2008/XPDL2.1")
        @XmlElement(name = "TransitionRef", namespace="http://www.wfmc.org/2008/XPDL2.1")
        public ArrayList<TransitionRef> getTransitionRefsList() {
            return transitionRefsList;
        }

        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        private static class TransitionRef {
            private String Id;

            @XmlAttribute(name="Id")
            public String getId() {
                return Id;
            }
        }
    }
}
