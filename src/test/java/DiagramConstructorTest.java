import com.fasterxml.jackson.databind.ObjectMapper;
import diagram.xpdl.Package;
import scenario.Scenario;
import utils.DiagramException;
import utils.XPDLNamespaceMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
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

        System.out.println("Give path to file:");
        String file = input.nextLine();

        try {
            Scenario scenario = mapper.readValue(Paths.get(file).toFile(), Scenario.class);
            DiagramConstructor diagramConstructor = new DiagramConstructor(scenario);
            diagramConstructor.buildDiagram();
            Package diagram = diagramConstructor.getDiagram();
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Package.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.wfmc.org/2008/XPDL2.1 http://www.wfmc.org/standards/docs/bpmnxpdl_31.xsd");
                jaxbMarshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new XPDLNamespaceMapper());

                System.out.println("Give name of output file:");
                String out = input.nextLine();
                OutputStream os = new FileOutputStream(out);
                jaxbMarshaller.marshal(diagram, os);
            } catch (JAXBException | FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException | DiagramException e) {
            e.printStackTrace();
        }
    }
}
