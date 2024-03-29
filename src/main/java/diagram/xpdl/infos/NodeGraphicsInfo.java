package diagram.xpdl.infos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Setter
@NoArgsConstructor
public class NodeGraphicsInfo extends GraphicsInfo {

    private String laneId;
    private Integer height;
    private Integer width;

    @Setter(AccessLevel.NONE)
    private Coordinates coordinates;

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

    public void setCoordinates(Integer xCoordinate,  Integer yCoordinate) {
        this.coordinates = new Coordinates(xCoordinate, yCoordinate);
    }

    public void offsetCoordinates(Integer xOffset,  Integer yOffset) {
        coordinates.setXCoordinate(coordinates.getXCoordinate() + xOffset);
        coordinates.setYCoordinate(coordinates.getYCoordinate() + yOffset);
    }

    @Setter
    @AllArgsConstructor
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
