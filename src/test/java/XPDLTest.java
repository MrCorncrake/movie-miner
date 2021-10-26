import diagram.*;
import diagram.Package;
import diagram.builders.DiagramBuilder;
import diagram.events.Event;
import diagram.events.StartEvent;
import diagram.infos.NodeGraphicsInfo;
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
        DiagramBuilder diagramBuilder = new DiagramBuilder("Test", "Test Package", "Movie", false);

        // Participants
        diagramBuilder.getDiagram().getParticipantsList().add(new Participant("TestParticipant")); //Space not allowed in id!

        NodeGraphicsInfo ngi1 = new NodeGraphicsInfo();
        ngi1.setBorderColor("0,0,0");
        ngi1.setFillColor("255,255,215");
        ngi1.setIsVisible(true);
        ngi1.setToolId("JaWE");
        diagramBuilder.getPool().getNodeGraphicsInfosList().add(ngi1);

        Lane lane = new Lane("Test_pool1_lan1", "Test_par1");
        diagramBuilder.getPool().getLanesList().add(lane);

        NodeGraphicsInfo ngi2 = new NodeGraphicsInfo();
        ngi2.setBorderColor("0,0,0");
        ngi2.setFillColor("220,220,220");
        ngi2.setIsVisible(true);
        ngi2.setToolId("JaWE");
        lane.getNodeGraphicsInfosList().add(ngi2);

        lane.getPerformersList().add("TestParticipant");

        Activity activity = new Activity();
        Event event = new Event();
        event.setStartEvent(new StartEvent());
        activity.setEvent(event);
        activity.setId("1");
        diagramBuilder.getProcess().getActivitiesList().add(activity);

        NodeGraphicsInfo ngi3 = new NodeGraphicsInfo();
        ngi3.setBorderColor("0,0,0");
        ngi3.setFillColor("102,204,51");
        ngi3.setHeight(31);
        ngi3.setWidth(31);
        ngi3.setIsVisible(true);
        ngi3.setLaneId("Test_pool1_lan1");
        ngi3.setToolId("JaWE");
        ngi3.setCoordinates(70,72);
        activity.getNodeGraphicsInfosList().add(ngi3);

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
