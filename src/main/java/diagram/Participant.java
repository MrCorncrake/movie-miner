package diagram;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;

@Setter
@NoArgsConstructor
public class Participant {

    private String id;
    private ParticipantType participantType;
    private ArrayList<ExtendedAttribute> extendedAttributesList;

    public Participant(String id) {
        this.id = id;
        participantType = new ParticipantType();
        extendedAttributesList = new ArrayList<>();
        extendedAttributesList.add(new ExtendedAttribute("JaWE_TYPE", "LANE_DEFAULT"));
    }

    @XmlAttribute(name="Id")
    public String getId() {
        return id;
    }

    @XmlElement(name="ParticipantType", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public ParticipantType getParticipantType() {
        return participantType;
    }

    public ArrayList<ExtendedAttribute> getExtendedAttributesList() {
        return extendedAttributesList;
    }

    @XmlElementWrapper(name = "ExtendedAttributes")

    @XmlElement(name = "ExtendedAttribute")
    public void setExtendedAttributesList(ArrayList<ExtendedAttribute> extendedAttributesList) {
        this.extendedAttributesList = extendedAttributesList;
    }

    private static class ParticipantType {
        @XmlAttribute(name="Type")
        public String getType() {
            return "ROLE";
        }
    }
}
