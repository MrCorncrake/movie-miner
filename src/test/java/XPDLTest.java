import diagram.*;
import diagram.Package;
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
import java.util.ArrayList;

public class XPDLTest {
    public static void main(String[] args) {
        Package p = new Package("Test1", "Test Package");
        p.setConformanceClass(new ConformanceClass());

        // Participants
        ArrayList<Participant> participants = new ArrayList<>();
        participants.add(new Participant("TestParticipant"));   //Space not allowed in id!
        p.setParticipantsList(participants);

        // Pools
        ArrayList<Pool> pools = new ArrayList<>();
        Pool pool = new Pool("Test_pool1", "Rush Hour", true, true, "HORIZONTAL", "Title");
        pools.add(pool);
        p.setPoolsList(pools);

        ArrayList<NodeGraphicsInfo> nodeGraphicsInfos1 = new ArrayList<>();
        NodeGraphicsInfo ngi1 = new NodeGraphicsInfo();
        ngi1.setBorderColor("0,0,0");
        ngi1.setFillColor("255,255,215");
        ngi1.setIsVisible(true);
        ngi1.setToolId("JaWE");
        nodeGraphicsInfos1.add(ngi1);
        pool.setNodeGraphicsInfosList(nodeGraphicsInfos1);

        ArrayList<Lane> lanes = new ArrayList<>();
        Lane lane = new Lane("Test_pool1_lan1", "Test_par1");
        lanes.add(lane);
        pool.setLanesList(lanes);

        ArrayList<NodeGraphicsInfo> nodeGraphicsInfos2 = new ArrayList<>();
        NodeGraphicsInfo ngi2 = new NodeGraphicsInfo();
        ngi2.setBorderColor("0,0,0");
        ngi2.setFillColor("220,220,220");
        ngi2.setIsVisible(true);
        ngi2.setToolId("JaWE");
        nodeGraphicsInfos2.add(ngi2);
        lane.setNodeGraphicsInfosList(nodeGraphicsInfos2);

        ArrayList<String> performers = new ArrayList<>();
        performers.add("TestParticipant");
        lane.setPerformersList(performers);

        // Workflow processes
        ArrayList<WorkflowProcess> workflowProcesses = new ArrayList<>();
        WorkflowProcess wp = new WorkflowProcess();
        wp.setId("Title");
        wp.setName("Rush Hour");
        workflowProcesses.add(wp);
        p.setWorkflowProcessesList(workflowProcesses);

        ArrayList<Activity> activities = new ArrayList<>();
        Activity activity = new Activity();
        Event event = new Event();
        event.setStartEvent(new StartEvent());
        activity.setEvent(event);
        activity.setId("1");
        activities.add(activity);
        wp.setActivitiesList(activities);

        ArrayList<NodeGraphicsInfo> nodeGraphicsInfos3 = new ArrayList<>();
        NodeGraphicsInfo ngi3 = new NodeGraphicsInfo();
        ngi3.setBorderColor("0,0,0");
        ngi3.setFillColor("102,204,51");
        ngi3.setHeight(31);
        ngi3.setWidth(31);
        ngi3.setIsVisible(true);
        ngi3.setLaneId("Test_pool1_lan1");
        ngi3.setToolId("JaWE");
        ngi3.setCoordinates(70,72);
        nodeGraphicsInfos3.add(ngi3);
        activity.setNodeGraphicsInfosList(nodeGraphicsInfos3);

        // Other
        ArrayList<ExtendedAttribute> extendedAttributes = new ArrayList<>();
        extendedAttributes.add(new ExtendedAttribute("EDITING_TOOL", "Movie-Miner"));
        extendedAttributes.add(new ExtendedAttribute("EDITING_TOOL_VERSION", "1.0-Snapshot"));
        extendedAttributes.add(new ExtendedAttribute("JaWE_CONFIGURATION", "default"));
        p.setExtendedAttributesList(extendedAttributes);

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
            jaxbMarshaller.marshal(p, System.out);
            //send to file system
            OutputStream os = new FileOutputStream("test.xpdl" );
            jaxbMarshaller.marshal(p, os);

        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
