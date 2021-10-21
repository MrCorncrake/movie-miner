package diagram.restrictions;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;

@NoArgsConstructor
public class Split {

    private ArrayList<TransitionRef> transitionRefsList;

    @XmlElementWrapper(name = "transitionRefs", namespace="http://www.wfmc.org/2008/XPDL2.1")

    @XmlElement(name = "TransitionRef", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public ArrayList<TransitionRef> getTransitionRefsList() {
        return transitionRefsList;
    }

    @Setter
    private static class TransitionRef {
        private String Id;

        @XmlAttribute(name="Id")
        public String getId() {
            return Id;
        }
    }
}
