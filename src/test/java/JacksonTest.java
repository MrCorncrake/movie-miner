import com.fasterxml.jackson.databind.ObjectMapper;
import scenario.Scenario;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class JacksonTest {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        Scanner input = new Scanner(System.in);

        System.out.println("Give path to file:");
        String file = input.nextLine();

        try {
            Scenario scenario = mapper.readValue(Paths.get(file).toFile(), Scenario.class);
            System.out.println(scenario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
