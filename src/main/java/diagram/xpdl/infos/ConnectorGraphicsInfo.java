package diagram.xpdl.infos;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;

@Setter
@NoArgsConstructor
public class ConnectorGraphicsInfo extends GraphicsInfo {

    private String style;

    @XmlAttribute(name="Style")
    public String getStyle() {
        return style;
    }
}
