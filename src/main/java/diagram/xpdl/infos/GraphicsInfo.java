package diagram.xpdl.infos;

import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;

@Setter
public abstract class GraphicsInfo {

    private String borderColor;
    private String fillColor;
    private Boolean isVisible;
    private String toolId;

    @XmlAttribute(name="BorderColor")
    public String getBorderColor() {
        return borderColor;
    }

    @XmlAttribute(name="FillColor")
    public String getFillColor() {
        return fillColor;
    }

    @XmlAttribute(name="IsVisible")
    public Boolean getVisible() {
        return isVisible;
    }

    @XmlAttribute(name="ToolId")
    public String getToolId() {
        return toolId;
    }
}
