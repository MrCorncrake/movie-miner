package diagram.infos;

import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;

@Setter
public abstract class GraphicsInfo {

    private String borderColor;
    private String fillColor;
    private Boolean isVisible;
    private String style;
    private String toolId;
    private String laneId;
    private Integer height;
    private Integer width;

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

    @XmlAttribute(name="Style")
    public String getStyle() {
        return style;
    }

    @XmlAttribute(name="ToolId")
    public String getToolId() {
        return toolId;
    }

    @XmlAttribute(name="LaneId")
    public String getLaneId() {
        return laneId;
    }

    @XmlAttribute(name="Height")
    public Integer getHeight() {
        return height;
    }

    @XmlAttribute(name="Width")
    public Integer getWidth() {
        return width;
    }
}
