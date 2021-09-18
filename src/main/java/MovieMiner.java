import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import utils.ParseException;
import scenario.Scenario;
import utils.ReaderException;

public class MovieMiner {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        Scanner input = new Scanner(System.in);

        System.out.println("Give path to file:");
        String file = input.nextLine();

        try {
            String text = new PdfReader(file).getText();
            // Adding script text to parser
            ScriptParser scriptParser = new ScriptParser(text);
            // Creating scenario object
            Scenario scenario = scriptParser.parse();
            // Choosing name of output json file
            System.out.println("Give name of output json file:");
            String json = input.nextLine();
            // Writing scenario object to json file
            Files.write(Paths.get(json), Collections.singleton(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scenario)));
        } catch (ReaderException | IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }
}
