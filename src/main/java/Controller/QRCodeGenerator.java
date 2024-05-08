/*package Controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.CentreDon;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class QRCodeGenerator {
    @FXML
    private ImageView QrCode;

    public void generateQRCode(CentreDon centreDon) {
        // Extract centre details
        int centreId = centreDon.getId();
        String centreName = centreDon.getNom();
        String centreHeureouv = centreDon.getHeureouv();
        String centreHeureferm = centreDon.getHeureferm();
        String centreLieu = centreDon.getLieu();
        Integer centreNum = centreDon.getNum();
        String centreGouvernorat = centreDon.getGouvernorat();
        String centreEmail = centreDon.getEmail();


        // Concatenate centre details into a single string
        String centreDetails = String.format("ID: %d\nName: %s\nHeure ouverture: %s\nHeure fermeture: %s\nLieu: %s\nNuméro: %d\nGouvernorat: %s\nEmail:",
                centreId, centreName, centreHeureouv, centreHeureferm, centreLieu, centreNum, centreGouvernorat, centreEmail);

        // Generate QR code containing centre details
        ByteArrayOutputStream out = QRCode.from(centreDetails).withSize(200, 200).stream();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        try {
            // Convert the QR code stream to an Image
            Image image = new Image(in);
            // Set the image to the ImageView
            QrCode.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/