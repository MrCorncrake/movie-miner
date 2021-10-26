package diagram;

import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;

@Setter
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
