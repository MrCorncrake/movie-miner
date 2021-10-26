package diagram;

import diagram.infos.ConnectorGraphicsInfo;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;

@NoArgsConstructor
@Setter
public class Transition {

    private String id;
    private String from;
    private String to;
    private ArrayList<ConnectorGraphicsInfo> ConnectorGraphicsInfosList = new ArrayList<>();

    @XmlAttribute(name="Id")
    public String getId() {
        return id;
    }

    @XmlAttribute(name="From")
    public String getFrom() {
        return from;
    }

    @XmlAttribute(name="To")
    public String getTo() {
        return to;
    }

    public ArrayList<ConnectorGraphicsInfo> getConnectorGraphicsInfosList() {
        return ConnectorGraphicsInfosList;
    }

    @XmlElementWrapper(name = "ConnectorGraphicsInfos", namespace="http://www.wfmc.org/2008/XPDL2.1")

    @XmlElement(name = "ConnectorGraphicsInfo", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public void setConnectorGraphicsInfosList(ArrayList<ConnectorGraphicsInfo> connectorGraphicsInfosList) {
        ConnectorGraphicsInfosList = connectorGraphicsInfosList;
    }
}
