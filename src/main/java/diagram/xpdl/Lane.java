package diagram.xpdl;

import diagram.xpdl.infos.NodeGraphicsInfo;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;

@Setter
@NoArgsConstructor
public class Lane {

    private String id;
    private String name;
    private ArrayList<NodeGraphicsInfo> nodeGraphicsInfosList = new ArrayList<>();
    private ArrayList<String> performersList = new ArrayList<>();

    public Lane(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @XmlAttribute(name="Id")
    public String getId() {
        return id;
    }

    @XmlAttribute(name="Name")
    public String getName() {
        return name;
    }

    public ArrayList<NodeGraphicsInfo> getNodeGraphicsInfosList() {
        return nodeGraphicsInfosList;
    }

    @XmlElementWrapper(name = "NodeGraphicsInfos", namespace="http://www.wfmc.org/2008/XPDL2.1")
    @XmlElement(name = "NodeGraphicsInfo", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public void setNodeGraphicsInfosList(ArrayList<NodeGraphicsInfo> nodeGraphicsInfosList) {
        this.nodeGraphicsInfosList = nodeGraphicsInfosList;
    }

    public ArrayList<String> getPerformersList() {
        return performersList;
    }

    @XmlElementWrapper(name = "Performers", namespace="http://www.wfmc.org/2008/XPDL2.1")
    @XmlElement(name = "Performer", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public void setPerformersList(ArrayList<String> performersList) {
        this.performersList = performersList;
    }
}
