import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import utils.ReaderException;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class PdfReader {

    private final String filename;

    public PdfReader(String filename) {
        this.filename = filename;
    }

    public String getText() throws ReaderException {
        try {
            PDDocument document = PDDocument.load(new File(filename));
            String text;
            if (!document.isEncrypted()) {
                Rectangle2D region = new Rectangle2D.Double(0, 40, 550, 725);
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
            } else throw new ReaderException("Encrypted document");
            document.close();
            return text;
        }
        catch (IOException ex) {
            throw new ReaderException("IOException: " + ex.getMessage());
        }
    }
}
