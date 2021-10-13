package diagram;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;

@NoArgsConstructor
@Setter
@XmlRootElement(name="Package", namespace="http://www.wfmc.org/2008/XPDL2.1")
public class Package {

    private String id;
    private String name;

    private PackageHeader packageHeader;
    private ConformanceClass conformanceClass;
    private ArrayList<Participant> participantsList;

    public Package(String id, String name) {
        this.id = id;
        this.name = name;
        this.packageHeader = new PackageHeader();
    }

    @XmlAttribute(name="Name")
    public String getName() {
        return name;
    }

    @XmlAttribute(name="Id")
    public String getId() {
        return id;
    }

    @XmlElement(name = "PackageHeader", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public PackageHeader getPackageHeader() {
        return packageHeader;
    }

    @XmlElement(name = "ConformanceClass", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public ConformanceClass getConformanceClass() {
        return conformanceClass;
    }

    public ArrayList<Participant> getParticipantsList() {
        return participantsList;
    }

    @XmlElementWrapper(name = "Participants", namespace="http://www.wfmc.org/2008/XPDL2.1")

    @XmlElement(name = "Participant", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public void setParticipantsList(ArrayList<Participant> participantsList) {
        this.participantsList = participantsList;
    }

    @Setter
    private static class PackageHeader {

        Double XPDLVersion;
        String vendor;
        Date created;

        public PackageHeader() {
            XPDLVersion = 2.1;
            vendor = "Gdansk University of Technology";
            created = new Date(System.currentTimeMillis());
        }

        @XmlElement(name = "XPDLVersion", namespace="http://www.wfmc.org/2008/XPDL2.1")
        public Double getXPDLVersion() {
            return XPDLVersion;
        }

        @XmlElement(name = "Vendor", namespace="http://www.wfmc.org/2008/XPDL2.1")
        public String getVendor() {
            return vendor;
        }

        @XmlElement(name = "Created", namespace="http://www.wfmc.org/2008/XPDL2.1")
        public Date getCreated() {
            return created;
        }
    }
}



