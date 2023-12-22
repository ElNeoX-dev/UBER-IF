package com.malveillance.uberif.formatters;

import com.malveillance.uberif.model.Courier;
import com.malveillance.uberif.model.Intersection;
import com.malveillance.uberif.model.RoadSegment;
import javafx.util.Pair;
import java.text.SimpleDateFormat;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * The PDFRoadMap class is responsible for generating a PDF file containing the road map of the tour of a courier.
 */
public class PDFRoadMap {
    /**
     * Formats the arrival time to a string with only the HH:mm:ss part.
     * @param arrivalTime the arrival time
     * @return the formatted arrival time
     */
    private static String formatArrivalTime(Date arrivalTime) {
        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        // Format the arrivalTime to a string with only the HH:mm:ss part
        return dateFormat.format(arrivalTime);
    }

    /**
     * Generates a PDF file containing the road map of the tour of a courier.
     * @param outputDirectory the output directory
     * @param fileName the file name
     * @param courierTourDatas the list of couriers and their tours
     */
    public static void generatePDF(String outputDirectory, String fileName, List<Pair<Courier, List<Pair<RoadSegment, Date>>>> courierTourDatas) {
        try {

            // Create the output directory if it doesn't exist
            File outputDir = new File(outputDirectory);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            // Construct the output file path
            String outputFilePath = outputDirectory + fileName + ".pdf";
            Path outputPath = Paths.get(outputFilePath);

            PDDocument document = new PDDocument();
            PDPage page = null;
            PDPageContentStream contentStream = null;


            // Parsing of the information of couriers
            for (Pair<Courier, List<Pair<RoadSegment, Date>>> courierTourData : courierTourDatas) {
                int countLines = 0;
                page = new PDPage();
                document.addPage(page);

                contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);

                float pageWidth = page.getMediaBox().getWidth();
                float centerX = pageWidth / 2;

                float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth("Road Map") / 1000 * 20;
                float titleX = centerX - titleWidth / 2;

                contentStream.beginText();
                contentStream.newLineAtOffset(titleX, 700);
                contentStream.showText("Road Map");
                contentStream.newLineAtOffset(-titleX+100, -30);

                Courier courier = courierTourData.getKey();
                List<Pair<RoadSegment, Date>> roadSegments = courierTourData.getValue();

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.showText("Road Map for Courier " + courier.getName());
                contentStream.newLineAtOffset(0, -12);
                contentStream.showText("Tour Starting at the Warehouse at 8 AM");
                contentStream.newLineAtOffset(0, -12);
                contentStream.setFont(PDType1Font.HELVETICA, 11);

                RoadSegment previousRoadSegment = null;
                for (int i = 0; i < roadSegments.size(); i++) {
                    RoadSegment segment = roadSegments.get(i).getKey();
                    Date arrivalTime = roadSegments.get(i).getValue();

                    if (i == 0 || !Objects.equals(previousRoadSegment.getName(), segment.getName()) || arrivalTime != null) {
                        if (i > 0) {
                            RoadSegment previousSegment = roadSegments.get(i - 1).getKey();
                            String turnDirection = segment.getTurnDirection(previousSegment);

                            if (!turnDirection.equals("zero")) {
                                contentStream.newLineAtOffset(0, -12);
                                contentStream.showText("Turn " + turnDirection + " on " + segment.getName());
                                countLines++;
                            }
                        } else {
                            contentStream.newLineAtOffset(0, -12);
                            contentStream.showText("Go on " + segment.getName());
                            countLines++;
                        }

                        if (arrivalTime != null) {
                            contentStream.newLineAtOffset(0, -12);
                            contentStream.showText("You will reach your destination at " + formatArrivalTime(arrivalTime));
                            contentStream.newLineAtOffset(0, -12);
                            countLines += 3;

                        }
                    }
                    if (countLines >= 45) {
                        countLines = 0;
                        contentStream.endText();
                        contentStream.close();

                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        contentStream.setFont(PDType1Font.HELVETICA, 11);
                        contentStream.beginText();
                    }
                    previousRoadSegment = segment;
                }

                contentStream.showText("You're back to the Warehouse. Your tour is finished!");
                countLines += 3;
                if (countLines >= 35){
                    countLines = 0;
                    page = new PDPage();
                    document.addPage(page);

                    contentStream = new PDPageContentStream(document, page);
                }

                // Finish the current page and start a new one
                if (contentStream != null) {
                    contentStream.endText();
                    contentStream.close();
                }
            }

            // Save the document to the specified output path
            document.save(outputPath.toAbsolutePath().toString());
            document.close();

            System.out.println("PDF generated successfully at: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}