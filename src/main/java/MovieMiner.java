import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;
import diagram.xpdl.Package;
import utils.ParseException;
import scenario.Scenario;
import utils.ReaderException;
import utils.XPDLNamespaceMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class MovieMiner {
    public static void main(String[] args) {
        if (args[0].toLowerCase(Locale.ROOT).contains("help")) {
            System.out.println("Usages:");
            System.out.println(" java -jar [MovieMiner jar] [MODE] [INPUT FILE PATH] [OUTPUT FILE PATH]");
            System.out.println(" java -jar [MovieMiner jar] help");
            System.out.println(" java -jar [MovieMiner jar] version");
            System.out.println("Modes: ");
            System.out.println(" PDF-JSON - Parses movie scenario provided in PDF format and downloaded from https://www.dailyscript.com/index.html to json");
            System.out.println(" JSON-XPDL - Creates XPDL diagram from json file obtained by parsing scenario");
            System.out.println(" PDF-XPDL - Combines PDF-JSON and JSON-XPDL modes into one");
        }
        else if (args[0].toLowerCase(Locale.ROOT).contains("version")) {
            System.out.println("MovieMiner 1.0");
            System.out.println("Authors: Maciej Derkacz, Karol Lempkowski, Hang Liu");
            System.out.println("Gdansk University of Technology 2021");
        }
        else if (args.length > 2) {
            switch (args[0]) {
                case "PDF-JSON":
                    parseScenario(args[1], args[2]);
                    break;
                case "JSON-XPDL":
                    buildDiagram(args[1], args[2]);
                    break;
                case "PDF-XPDL":
                    parseScenario(args[1], "tmp.json");
                    buildDiagram("tmp.json", args[2]);
                    boolean done = Paths.get("tmp.json").toFile().delete();
                    break;
                default:
                    break;
            }
        }
    }

    private static void parseScenario(String inputFile, String outputFile) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        try {
            String text = new PdfReader(inputFile).getText();
            // Adding script text to parser
            ScriptParser scriptParser = new ScriptParser(text);
            // Creating scenario object
            Scenario scenario = scriptParser.parse();
            // Writing scenario object to json file
            Files.write(Paths.get(outputFile), Collections.singleton(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scenario)));
        } catch (ReaderException | IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }

    private static void buildDiagram(String inputFile, String outputFile) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        try {
            // Reading scenario
            Scenario scenario = mapper.readValue(Paths.get(inputFile).toFile(), Scenario.class);
            DiagramConstructor diagramConstructor = new DiagramConstructor(scenario);
            diagramConstructor.buildDiagram();
            Package diagram = diagramConstructor.getDiagram();
            // Setting up marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(Package.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.wfmc.org/2008/XPDL2.1 http://www.wfmc.org/standards/docs/bpmnxpdl_31.xsd");
            jaxbMarshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new XPDLNamespaceMapper());
            // Preparing output file
            OutputStream os = new FileOutputStream(outputFile);
            jaxbMarshaller.marshal(diagram, os);

        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }
}
