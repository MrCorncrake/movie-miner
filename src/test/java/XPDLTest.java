import diagram.xpdl.Package;
import diagram.builders.DiagramBuilder;
import diagram.builders.LaneBuilder;
import utils.XPDLNamespaceMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class XPDLTest {
    public static void main(String[] args) {
        DiagramBuilder diagramBuilder = new DiagramBuilder("Test", "Test Package", "Movie");

        LaneBuilder laneBuilder = diagramBuilder.addLane("TestParticipant");
        laneBuilder.addStartActivity(0, "Test");
        laneBuilder.addActivity(1, "Scene 1 - TestParticipant");
        laneBuilder.addActivity(2, "Scene 2 - TestParticipant");
        laneBuilder.addActivity(3, "Scene 3 - TestParticipant");
        laneBuilder.addEndActivity(4);

        LaneBuilder laneBuilder2 = diagramBuilder.addLane("TestParticipant2");
        laneBuilder2.addStartActivity(1, "Test");
        laneBuilder2.addActivity(2, "Scene 2 - TestParticipant2");
        laneBuilder2.addActivity(4, "Scene 3 - TestParticipant2");
        laneBuilder2.addEndActivity(5);

        laneBuilder.connectToLaneAt(laneBuilder2, 2);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Package.class);
            //class responsible for the process of
            //serializing Java object into XML data
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //marshalled XML data is formatted with linefeeds and indentation
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //specify the xsi:schemaLocation attribute value
            //to place in the marshalled XML output
            jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.wfmc.org/2008/XPDL2.1 http://www.wfmc.org/standards/docs/bpmnxpdl_31.xsd");
            try {
                //override for custom namespace prefix
                jaxbMarshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new XPDLNamespaceMapper());
            } catch(PropertyException e) {
                e.printStackTrace();
            }

            //send to console
            jaxbMarshaller.marshal(diagramBuilder.getDiagram(), System.out);
            //send to file system
            OutputStream os = new FileOutputStream("test.xpdl" );
            jaxbMarshaller.marshal(diagramBuilder.getDiagram(), os);

        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
