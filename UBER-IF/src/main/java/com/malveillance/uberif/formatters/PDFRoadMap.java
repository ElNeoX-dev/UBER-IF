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

public class PDFRoadMap {
    private static String formatArrivalTime(Date arrivalTime) {
        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        // Format the arrivalTime to a string with only the HH:mm:ss part
        return dateFormat.format(arrivalTime);
    }

    public static void generatePDF(List<Pair<Intersection, Date>> roadMap, List<Pair<Courier, List<Pair<RoadSegment, Date>>>> courierTourDatas) {
        try {
            // Specify the output directory
            String outputDirectory = "resources/output/";

            // Create the output directory if it doesn't exist
            File outputDir = new File(outputDirectory);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            // Construct the output file path
            String outputFilePath = outputDirectory + "RoadMap.pdf";
            Path outputPath = Paths.get(outputFilePath);

            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);

            // Get the road map infos
            // Calculate the center of the page
            float pageWidth = page.getMediaBox().getWidth();
            float centerX = pageWidth / 2;

            // Calculate the position for the title
            float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth("Road Map") / 1000 * 20;
            float titleX = centerX - titleWidth / 2;

            contentStream.beginText();
            contentStream.newLineAtOffset(titleX, 700);
            contentStream.showText("Road Map");
            contentStream.newLineAtOffset(-titleX+100, -30);

            for (Pair<Courier, List<Pair<RoadSegment, Date>>> courierTourData : courierTourDatas) {
                Courier courier = courierTourData.getKey();
                List<Pair<RoadSegment, Date>> roadSegments = courierTourData.getValue();

                // Process courier information and road segments as needed
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.showText("Road Map for Courier " + courier.getName());
                contentStream.newLineAtOffset(0, -12); // Adjust the vertical offset as needed
                contentStream.showText("Tour Starting at the Warehouse at 8 AM");
                contentStream.newLineAtOffset(0, -12); // Adjust the vertical offset as needed

                contentStream.setFont(PDType1Font.HELVETICA, 11);
                // Keep track of the previous RoadSegment
                RoadSegment previousRoadSegment = null;
                for (int i = 0; i < roadSegments.size(); i++) {
                    RoadSegment segment = roadSegments.get(i).getKey();
                    Date arrivalTime = roadSegments.get(i).getValue();

                    // Check if the current RoadSegment is different from the previous one
                    if (arrivalTime != null || !segment.equals(previousRoadSegment)) {
                        // Add text to the content stream
                        if (i > 0) {
                            RoadSegment previousSegment = roadSegments.get(i - 1).getKey();
                            String turnDirection = segment.getTurnDirection(previousSegment);
                            System.out.println(turnDirection);
                            if (!turnDirection.equals("zero")) {
                                contentStream.newLineAtOffset(0, -12); // Adjust the vertical offset as needed
                                contentStream.showText("Turn " + turnDirection + " on " + segment.getName() + " id: " + segment.getOrigin().getId());
                            }
                        } else {
                            contentStream.newLineAtOffset(0, -12); // Adjust the vertical offset as needed
                            contentStream.showText("Go on " + segment.getName() + " id: " + segment.getOrigin().getId());
                        }

                        if (arrivalTime != null) {
                            contentStream.newLineAtOffset(0, -12); // Adjust the vertical offset as needed
                            contentStream.showText("You will reach your destination at " + formatArrivalTime(arrivalTime));
                            contentStream.newLineAtOffset(0, -12); // Adjust the vertical offset as needed
                        }
                    }
                    else {
                        contentStream.newLineAtOffset(0, -12); // Adjust the vertical offset as needed
                        contentStream.showText("Continue straight");
                    }
                    previousRoadSegment = segment;
                }

                contentStream.newLineAtOffset(0, -30); // Adjust the vertical offset as needed
                contentStream.showText("You're back to the Warehouse. Your tour is finished!");
                contentStream.newLineAtOffset(0, -50); // Adjust the vertical offset as needed
            }

            contentStream.endText();
            contentStream.close();

            // Save the document to the specified output path
            document.save(outputPath.toAbsolutePath().toString());
            document.close();

            System.out.println("PDF generated successfully at: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}