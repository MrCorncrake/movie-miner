import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import scenario.ParseException;
import scenario.Scenario;
import scenario.Scene;

public class MovieMiner {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        Scanner input = new Scanner(System.in);

//        System.out.println("Give path to file:");
//        String file = input.nextLine();

        System.out.println("Does script contain header and footer? (true/false)");
        boolean hasFooterAndHeader = Boolean.parseBoolean(input.nextLine());

        try {
//            PDDocument document = PDDocument.load(new File(file));
            PDDocument document = PDDocument.load(new File("rush_hour.pdf"));
            if (!document.isEncrypted()) {
                String text;
                if (hasFooterAndHeader) {
                    // Region representing page without header and footer
                    Rectangle2D region = new Rectangle2D.Double(0, 100, 550, 600);
                    String regionName = "page";

                    PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                    // adding region to stripper
                    stripper.addRegion(regionName, region);

                    // Creating string with the whole script without header of footer
                    StringBuilder textBuilder = new StringBuilder();
                    for (PDPage page : document.getPages()) {
                        stripper.extractRegions(page);
                        textBuilder.append(stripper.getTextForRegion(regionName));
                    }
                    text = textBuilder.toString();
                }
                else {
                    // Creating string with the whole script
                    PDFTextStripper stripper = new PDFTextStripper();
                    text = stripper.getText(document);
                }

                // Adding script text to parser
                ScriptParser scriptParser = new ScriptParser(text);
                // Creating scenario object
                Scenario scenario = null;
                try {
                    scenario = scriptParser.parse();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String temp = scriptParser.getRemainingText();
                // Adding script text to parser
                ScenesExtractor scenesParser = new ScenesExtractor(temp, scenario);
                // Creating scenario object
                Scene scene = null;
                try {
                    scene = scenesParser.parse();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Choosing name of output json file
                System.out.println("Give name of output json file:");
                String json = input.nextLine();
                // Writing scenario object to json file
                Files.write(Paths.get(json), Collections.singleton(mapper.writeValueAsString(scenario)));
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
