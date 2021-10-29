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
public class Pool {

    private Boolean boundaryVisible;
    private String id;
    private Boolean mainPool;
    private String name;
    private String orientation;
    private String process;
    private ArrayList<Lane> lanesList = new ArrayList<>();
    private ArrayList<NodeGraphicsInfo> nodeGraphicsInfosList = new ArrayList<>();

    public Pool(String id, String name, Boolean boundaryVisible, Boolean mainPool, String orientation, String process) {
        this.id = id;
        this.name = name;
        this.boundaryVisible = boundaryVisible;
        this.mainPool = mainPool;
        this.orientation = orientation;
        this.process = process;
    }

    @XmlAttribute(name="BoundaryVisible")
    public Boolean getBoundaryVisible() {
        return boundaryVisible;
    }

    @XmlAttribute(name="Id")
    public String getId() {
        return id;
    }

    @XmlAttribute(name="MainPool")
    public Boolean getMainPool() {
        return mainPool;
    }

    @XmlAttribute(name="Name")
    public String getName() {
        return name;
    }

    @XmlAttribute(name="Orientation")
    public String getOrientation() {
        return orientation;
    }

    @XmlAttribute(name="Process")
    public String getProcess() {
        return process;
    }

    public ArrayList<Lane> getLanesList() {
        return lanesList;
    }

    @XmlElementWrapper(name = "Lanes", namespace="http://www.wfmc.org/2008/XPDL2.1")

    @XmlElement(name = "Lane", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public void setLanesList(ArrayList<Lane> lanesList) {
        this.lanesList = lanesList;
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
