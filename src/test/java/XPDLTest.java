import diagram.ConformanceClass;
import diagram.Package;
import diagram.Participant;
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
        ArrayList<Participant> participants = new ArrayList<>();
        participants.add(new Participant("Test Participant"));
        p.setParticipantsList(participants);

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
