package diagram;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Setter
@NoArgsConstructor
public class NodeGraphicsInfo {

    private String borderColor;
    private String fillColor;
    private Boolean isVisible;
    private String toolId;
    private String laneId;
    private Integer height;
    private Integer width;

    private Coordinates coordinates;

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

    @XmlElement(name = "Coordinates", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Setter
    @NoArgsConstructor
    private static class Coordinates {
        private Integer xCoordinate;
        private Integer yCoordinate;

        @XmlAttribute(name="XCoordinate")
        public Integer getXCoordinate() {
            return xCoordinate;
        }

        @XmlAttribute(name="YCoordinate")
        public Integer getYCoordinate() {
            return yCoordinate;
        }
    }
}
