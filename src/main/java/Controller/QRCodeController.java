package Controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import models.Dons;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QRCodeController {

    @FXML
    private TextField textField;

    @FXML
    private ImageView qrCodeImageView;
    private Dons don;

    @FXML
    private void initialize() {
        generateQRCode();
    }

    private BufferedImage generateQRCodeImage(String text) throws WriterException {
        Writer qrWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return qrImage;
    }

    private String createQRCodeText(Dons don) {
        return "Name: " + don.getId() + "\nID: " + don.getCin();
    }

    public void generateQRCode() {
        if (don != null) {
            String text = createQRCodeText(don);
            if (!text.isEmpty()) {
                try {
                    // Create QR Code
                    BufferedImage qrImage = generateQRCodeImage(text);

                    // Convert BufferedImage to JavaFX Image
                    Image image = convertToJavaFXImage(qrImage);

                    // Display QR Code in ImageView
                    qrCodeImageView.setImage(image);
                } catch (WriterException ex) {
                    Logger.getLogger(QRCodeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("No 'don' object selected.");
        }
    }

    // Method to set the 'don' object
    public void setDon(Dons don) {
        this.don = don;
    }

    private Image convertToJavaFXImage(BufferedImage image) {
        WritableImage writableImage = new WritableImage(image.getWidth(), image.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int argb = image.getRGB(x, y);
                pixelWriter.setArgb(x, y, argb);
            }
        }
        return writableImage;
    }
}
