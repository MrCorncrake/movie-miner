package diagram;

import javax.xml.bind.annotation.XmlAttribute;

public class ConformanceClass {
    private final String graphConformance;

    @XmlAttribute(name="GraphConformance")
    public String getGraphConformance() {
        return graphConformance;
    }

    public ConformanceClass() {
        graphConformance = "NON_BLOCKED";
    }
}
