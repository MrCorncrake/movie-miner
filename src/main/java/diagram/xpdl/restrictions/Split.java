package diagram.xpdl.restrictions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;

@NoArgsConstructor
@Setter
public class Split extends Restriction {

    private ArrayList<TransitionRef> transitionRefsList = new ArrayList<>();

    public Split(String type) {
        this.setType(type);
    }

    @XmlElementWrapper(name = "transitionRefs", namespace="http://www.wfmc.org/2008/XPDL2.1")

    @XmlElement(name = "TransitionRef", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public ArrayList<TransitionRef> getTransitionRefsList() {
        return transitionRefsList;
    }

    public void addTransitionRef(String transitionRef) {
        transitionRefsList.add(new TransitionRef(transitionRef));
    }

    public void removeTransitionRef(String transitionRef) {
        for(TransitionRef tr : transitionRefsList) {
            if(tr.getId().equals(transitionRef)) {
                transitionRefsList.remove(tr);
                break;
            }
        }
    }

    @Setter
    @AllArgsConstructor
    private static class TransitionRef {
        private String Id;

        @XmlAttribute(name="Id")
        public String getId() {
            return Id;
        }
    }
}
