import com.fasterxml.jackson.databind.ObjectMapper;
import diagram.xpdl.Package;
import scenario.Scenario;
import utils.XPDLNamespaceMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Scanner;

public class DiagramConstructorTest {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        Scanner input = new Scanner(System.in);

        //System.out.println("Give path to file:");
        //String file = input.nextLine();
        String file = "scenarios/amadeus.json";

        try {
            Scenario scenario = mapper.readValue(Paths.get(file).toFile(), Scenario.class);
            DiagramConstructor diagramConstructor = new DiagramConstructor(scenario);
            diagramConstructor.buildDiagram();
            Package diagram = diagramConstructor.getDiagram();
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

                //send to file system
                OutputStream os = new FileOutputStream("test.xpdl" );
                jaxbMarshaller.marshal(diagram, os);

            } catch (JAXBException | FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
