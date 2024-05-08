package cotrollers;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.javafx.menu.MenuItemBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.awt.*;
import java.io.*;
import java.sql.SQLException;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Rendezvous;
import models.Reponse;
import services.RendezvousService;
import services.ReponseService;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.TilePane;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.ImageView;

public class Front  {

    @FXML
    private TilePane cardsContainer;
    @FXML
    private Hyperlink addRendezvous;

    ReponseService reponseService = new ReponseService();
    private RendezvousService rendezvousService = new RendezvousService();
    private Rendezvous rendezvous;
    private MenuItemBase detailsButton;
    @FXML
    private Button chatbotButton;


    @FXML
    public void initialize() {
        setupAddDonLink();
        loadrv();

    }
    private void refreshTable() {
        loadrv(); // Recharger les rendez-vous et mettre à jour l'affichage
    }
    private void setupAddDonLink() {
        addRendezvous.setOnMouseClicked(e -> showAddRendezvousForm());
    }

    private void showAddRendezvousForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutRendezvous.fxml"));
            Parent parent = loader.load();
            AjoutRendezvous ajoutRendezvousController = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait(); // Attendre que la fenêtre soit fermée avant de rafraîchir l'affichage
            // Rafraîchir l'affichage après la fermeture de la fenêtre d'ajout si nécessaire
            refreshTable();
            // Appeler la méthode de rafraîchissement
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void loadrv() {
        cardsContainer.getChildren().clear(); // Clear existing cards before loading new ones

        // Create an instance of RendezvousService
        RendezvousService rendezvousService = new RendezvousService();

        try {
            // Call the read method on the rendezvousService instance
            List<Rendezvous> rendezvousList = rendezvousService.read();

            // Iterate through the list of rendezvous and create cards
            for (Rendezvous rendezvous : rendezvousList) {
                VBox card = createDonCard(rendezvous);
                cardsContainer.getChildren().add(card);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle the exception appropriately
        }
    }
    private void generatePDF() throws DocumentException, IOException {
        // Appeler la fonction generatePDF() du contrôleur approprié
        AfficherRendezvous controller = new AfficherRendezvous(); // Assurez-vous de créer une instance appropriée du contrôleur
        controller.generatePDF();
    }

    private void generatePDF(Rendezvous rendezvous) throws DocumentException, IOException {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("rendezvous.pdf"));
            document.open();
            document.add(new Paragraph("Nom du Médecin: " + rendezvous.getNommedecin()));
            document.add(new Paragraph("Date du rendez-vous: " + (rendezvous.getDate() != null ? rendezvous.getDate().toString() : "N/A")));
            document.add(new Paragraph("Heure du rendez-vous: " + (rendezvous.getHeure() != null ? rendezvous.getHeure() : "N/A")));
            // Ouvrir le PDF généré
            File file = new File("rendezvous.pdf");
            Desktop.getDesktop().open(file);
        } finally {
            document.close();
        }
    }

    private VBox createDonCard(Rendezvous rendezvous) {
        VBox card = new VBox(5);
        card.getStyleClass().add("card-pane");
        card.setPadding(new Insets(10));
        card.setMinWidth(200); // Définir une largeur minimale fixe pour chaque carte
        card.setMinHeight(200); // Définir une hauteur minimale fixe pour chaque carte

        Label patient = new Label("Nom du patient: " + rendezvous.getNompatient());
        patient.setStyle("-fx-font-weight: bold; -fx-font-family: Arial;");

        Label medecinLabel = new Label("Nom du Médecin: " + rendezvous.getNommedecin());
        medecinLabel.setStyle("-fx-font-weight: bold; -fx-font-family: Arial;");

        Label dateLabel = new Label("Date du rendez-vous: " + (rendezvous.getDate() != null ? rendezvous.getDate().toString() : "N/A"));
        dateLabel.setStyle("-fx-font-family: Arial;");

        Label heureLabel = new Label("Heure du rendez-vous: " + (rendezvous.getHeure() != null ? rendezvous.getHeure() : "N/A"));
        heureLabel.setStyle("-fx-font-family: Arial;");

        HBox buttonsBox = new HBox(5);
        buttonsBox.setAlignment(Pos.CENTER); // Aligner les boutons au centre
        buttonsBox.setPadding(new Insets(5, 0, 0, 0));

        // Create FontAwesomeIconViews for icons
        FontAwesomeIconView detailsIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
        detailsIcon.setSize("2em");
        detailsIcon.setStyleClass("button-icon");
        detailsIcon.setOnMouseClicked(event -> showDetails(rendezvous));

        FontAwesomeIconView generatePDFIcon = new FontAwesomeIconView(FontAwesomeIcon.FILE_PDF_ALT);
        generatePDFIcon.setSize("2em");
        generatePDFIcon.setStyleClass("button-icon");
        generatePDFIcon.setOnMouseClicked(event -> {
            try {
                generatePDF(rendezvous);
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
                // Handle exception
            }
        });

        FontAwesomeIconView generateQRIcon = new FontAwesomeIconView(FontAwesomeIcon.QRCODE);
        generateQRIcon.setSize("2em");
        generateQRIcon.setStyleClass("button-icon");
        generateQRIcon.setOnMouseClicked(event -> showRendezvousDetails(rendezvous));

        FontAwesomeIconView paymentIcon = new FontAwesomeIconView(FontAwesomeIcon.CREDIT_CARD);
        paymentIcon.setSize("2em");
        paymentIcon.setStyleClass("button-icon");
        paymentIcon.setOnMouseClicked(event -> {
            try {
                makePaymentForRendezvous(rendezvous);
            } catch (Exception e) {
                e.printStackTrace();
                // Handle exception
            }
        });

        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        deleteIcon.setSize("2em");
        deleteIcon.setStyleClass("button-icon");
        deleteIcon.setOnMouseClicked(event -> {
            try {
                deleteRendezvous(rendezvous);
            } catch (Exception e) {
                e.printStackTrace();
                // Handle exception
            }
        });

        buttonsBox.getChildren().addAll(detailsIcon, generatePDFIcon, generateQRIcon, paymentIcon, deleteIcon);

        card.getChildren().addAll(patient,medecinLabel, dateLabel, heureLabel, buttonsBox);
        card.setAlignment(Pos.CENTER_LEFT);
        return card;
    }


    private void deleteRendezvous(Rendezvous rendezvous) {
        try {
            // Supprimez le rendez-vous en utilisant le service approprié
            rendezvousService.delete(rendezvous.getId());

            // Rafraîchissez l'affichage après la suppression en rechargeant les rendez-vous
            loadrv();

            // Affichez un message de confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Rendezvous deleted successfully!");
            alert.showAndWait();
        } catch (SQLException ex) {
            ex.printStackTrace(); // Gérez l'exception de manière appropriée
            // Affichez un message d'erreur en cas d'échec de la suppression
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to delete rendezvous!");
            alert.showAndWait();
        }
    }


    private void makePaymentForRendezvous(Rendezvous rendezvous) throws IOException {
        System.out.println("test");
        GuiPaiementController.rendezvous=rendezvous;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GuiPaiememnt.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
    }

    ImageView generateQRCode(String qrText) {
        int width = 300;
        int height = 300;
        String imageFormat = "png";

        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.MARGIN, 1);
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, width, height, hintMap);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        Image qrImage = new Image(byteArrayInputStream);

        ImageView qrImageView = new ImageView(qrImage);
        qrImageView.setFitWidth(width);
        qrImageView.setFitHeight(height);

        return qrImageView;
    }
    // Méthode pour afficher les détails dans l'interface utilisateur de mise à jour
    private void showDetails(Rendezvous rendezvous) {
        if (rendezvous != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierRendezvous.fxml"));
                Parent parent = loader.load();
                ModifierRendezvous modifierRendezvous = loader.getController();
                // Transférer les données du rendez-vous à l'interface utilisateur de mise à jour
                modifierRendezvous.setRendezvousData(rendezvous);
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.showAndWait(); // Attendre que la fenêtre soit fermée avant de rafraîchir
                refreshTable();
            } catch (IOException ex) {
                Logger.getLogger(Front.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a rendezvous to update.");
            alert.showAndWait();
        }
    }

    void showRendezvousDetails(Rendezvous rendezvous) {
        // Créer une boîte VBox pour contenir les détails et le code QR
        VBox detailsBox = new VBox(10);
        detailsBox.setPadding(new Insets(10));

        // Afficher les détails du rendez-vous
        Label nomPatientLabel = new Label("Nom Patient: " + rendezvous.getNompatient());
        Label nomMedecinLabel = new Label("Nom Médecin: " + rendezvous.getNommedecin());
        Label dateLabel = new Label("Date: " + rendezvous.getDate());
        Label heureLabel = new Label("Heure: " + rendezvous.getHeure());
        detailsBox.getChildren().addAll(nomPatientLabel, nomMedecinLabel, dateLabel, heureLabel);

        // Générer et ajouter le code QR
        ImageView qrImageView = generateQRCode(rendezvous.toString()); // Vous pouvez changer cela pour utiliser les détails que vous voulez
        detailsBox.getChildren().add(qrImageView);

        // Afficher les détails dans une boîte de dialogue
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rendezvous Details");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(detailsBox);
        alert.showAndWait();
    }

    @FXML
    private void chatbot(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChatBot.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

