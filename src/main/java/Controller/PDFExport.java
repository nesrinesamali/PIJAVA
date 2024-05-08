package Controller;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.CentreDon;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PDFExport {
    private static final String FONT_FILE_PATH = "C:/Users/Lenovo/Desktop/font/ARIAL.ttf"; // Update with your actual file path

    private static PDType0Font arialFont;

    public static PDType0Font loadArialFont(PDDocument document) throws IOException {
        if (arialFont == null) {
            try (InputStream inputStream = new FileInputStream(FONT_FILE_PATH)) {
                arialFont = PDType0Font.load(document, inputStream);
            }
        }
        return arialFont;
    }

    public static void exportTableViewToPDF(TableView<CentreDon> tableView, String imagePath, String filePath, PDDocument document) {
        try {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(loadArialFont(document), 12);

                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;

                // Draw image at the top
                drawImage(contentStream, imagePath, margin, yStart - 50, 100, 100, document);

                // Calculate position for table data
                float tableYStart = yStart - 150; // Adjust this value as needed

                // Draw table headers
                float headerEndY = drawTableHeaders(contentStream, margin, tableYStart, 20, 5, tableView.getColumns());

                // Draw table data
                drawTableData(contentStream, margin, headerEndY - 5, 20, 5, 5, tableView.getColumns(), tableView.getItems());
            }

            document.save(filePath);
            System.out.println("TableView exported to PDF: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void drawImage(PDPageContentStream contentStream, String imagePath, float x, float y, float width, float height, PDDocument document) throws IOException {
        try {
            File file = new File(imagePath);
            if (file.exists()) {
                PDImageXObject imageXObject = PDImageXObject.createFromFile(imagePath, document);
                contentStream.drawImage(imageXObject, x, y, width, height);
            } else {
                System.err.println("Image file not found: " + imagePath);
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static float drawTableHeaders(PDPageContentStream contentStream, float xStart, float yStart, float rowHeight, float cellMargin, ObservableList<TableColumn<CentreDon, ?>> columns) throws IOException {
        float nextX = xStart + cellMargin;
        float nextY = yStart - rowHeight - cellMargin;

        // Draw table headers
        for (TableColumn<CentreDon, ?> column : columns) {
            contentStream.setNonStrokingColor(255, 0, 0); // Red
            contentStream.beginText();
            contentStream.setFont(loadArialFont(null), 12);
            contentStream.newLineAtOffset(nextX, nextY + rowHeight - cellMargin); // Move text up slightly for centering
            contentStream.showText(column.getText());
            contentStream.endText();
            nextX += column.getWidth() + cellMargin;
        }

        // Return the Y position after drawing headers
        return nextY;
    }

    private static void drawTableData(PDPageContentStream contentStream, float xStart, float yStart, float rowHeight, float cellMargin, float rowSpacing, ObservableList<TableColumn<CentreDon, ?>> columns, ObservableList<CentreDon> items) throws IOException {
        float nextY = yStart - rowHeight - cellMargin;

        // Draw table data
        for (CentreDon centreDon : items) {
            float nextX = xStart + cellMargin;

            contentStream.setNonStrokingColor(0, 0, 0); // Black
            for (TableColumn<CentreDon, ?> column : columns) {
                Object cellData = column.getCellData(centreDon); // Use centreDon instead of CentreDon
                String cellValue = (cellData != null) ? cellData.toString() : "";
                contentStream.beginText();
                contentStream.setFont(loadArialFont(null), 12);
                contentStream.newLineAtOffset(nextX, nextY); // Align to top-left corner of cell
                contentStream.showText(cellValue);
                contentStream.endText();
                nextX += column.getWidth() + cellMargin;
            }

            nextY -= (rowHeight + rowSpacing); // Add spacing between rows
        }
    }
}
