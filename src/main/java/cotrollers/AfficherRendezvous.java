package cotrollers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Rendezvous;
import services.RendezvousService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AfficherRendezvous implements Initializable {

        @FXML
        private TableColumn<Rendezvous, ?> action;

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TableColumn<Rendezvous, Date> date;

        @FXML
        private TableColumn<Rendezvous, String> heure;

        @FXML
        private TableColumn<Rendezvous, String> nommedecin;

        @FXML
        private TableColumn<Rendezvous, String> nompatient;

        @FXML
        private TableColumn<Rendezvous, Boolean> etat;

        @FXML
        private TableView<Rendezvous> tv;

        RendezvousService rs = new RendezvousService();
        private ObservableList<Rendezvous> observableList = FXCollections.observableArrayList();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                try {
                        List<Rendezvous> rendezvousList = rs.read();
                        observableList = FXCollections.observableList(rendezvousList);
                        tv.setItems(observableList);
                        nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
                        nommedecin.setCellValueFactory(new PropertyValueFactory<>("nommedecin"));
                        date.setCellValueFactory(new PropertyValueFactory<>("date"));
                        heure.setCellValueFactory(new PropertyValueFactory<>("heure"));
                        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));

                        // Add action buttons column
                        TableColumn<Rendezvous, Void> actionsColumn = new TableColumn<>("Actions");
                        actionsColumn.setCellFactory(param -> new TableCell<>() {
                                private final FontAwesomeIconView deleteButton = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                                private final FontAwesomeIconView updateButton = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                                private final FontAwesomeIconView payIcon = new FontAwesomeIconView(FontAwesomeIcon.MONEY);
                                private final FontAwesomeIconView showIcon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
                                private final Button pdfButton = new Button("PDF");

                                {
                                        deleteButton.setOnMouseClicked(event -> {
                                                Rendezvous rendezvous = getTableView().getItems().get(getIndex());
                                                try {
                                                        rs.delete(rendezvous.getId());
                                                        getTableView().getItems().remove(rendezvous);
                                                        refreshTable(); // Rafraîchir le TableView après la suppression
                                                } catch (SQLException e) {
                                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                                        alert.setTitle("Error");
                                                        alert.setContentText("Error when deleting item");
                                                        alert.showAndWait();
                                                }
                                        });

                                        updateButton.setOnMouseClicked(event -> {
                                                Rendezvous selectedRendezvous = getTableView().getItems().get(getIndex());
                                                if (selectedRendezvous != null) {
                                                        try {
                                                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierRendezvous.fxml"));
                                                                Parent parent = loader.load();
                                                                ModifierRendezvous modifierRendezvous = loader.getController();
                                                                modifierRendezvous.setRendezvousData(selectedRendezvous);
                                                                Scene scene = new Scene(parent);
                                                                Stage stage = new Stage();
                                                                stage.setScene(scene);
                                                                stage.initStyle(StageStyle.UTILITY);
                                                                stage.showAndWait(); // Attendre que la fenêtre soit fermée avant de rafraîchir
                                                                refreshTable(); // Rafraîchir le TableView après la modification
                                                        } catch (IOException ex) {
                                                                Logger.getLogger(AfficherRendezvous.class.getName()).log(Level.SEVERE, null, ex);
                                                        }
                                                } else {
                                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                                        alert.setTitle("No Selection");
                                                        alert.setHeaderText(null);
                                                        alert.setContentText("Please select a rendezvous to update.");
                                                        alert.showAndWait();
                                                }
                                        });

                                        payIcon.setOnMouseClicked(event -> {
                                                // Action pour le paiement
                                                try {
                                                        Parent root = FXMLLoader.load(getClass().getResource("/GuiPaiememnt.fxml"));
                                                        Stage stage = new Stage();
                                                        stage.setScene(new Scene(root));
                                                        stage.show();
                                                } catch (IOException e) {
                                                        e.printStackTrace();
                                                }
                                        });

                                        showIcon.setOnMouseClicked(event -> {
                                                Rendezvous rendezvous = getTableView().getItems().get(getIndex());
                                                showRendezvousDetails(rendezvous);
                                        });

                                        pdfButton.setOnAction(event -> {
                                                try {
                                                        generatePDF();
                                                } catch (IOException | DocumentException e) {
                                                        e.printStackTrace();
                                                }
                                        });
                                }

                                @Override
                                protected void updateItem(Void item, boolean empty) {
                                        super.updateItem(item, empty);
                                        if (empty) {
                                                setGraphic(null);
                                        } else {
                                                HBox buttons = new HBox(deleteButton, updateButton, payIcon, showIcon,pdfButton);
                                                buttons.setSpacing(5); // Ajoute un espacement entre les boutons
                                                setGraphic(buttons);

                                                // Styling
                                                updateButton.setStyle("-fx-cursor: hand ; -glyph-size:28px; -fx-fill:#ff1744;");
                                                deleteButton.setStyle("-fx-cursor: hand ; -glyph-size:28px; -fx-fill:#00E676;");
                                                payIcon.setStyle("-fx-cursor: hand ; -glyph-size:28px; -fx-fill:#2196F3;");
                                                showIcon.setStyle("-fx-cursor: hand ; -glyph-size:28px; -fx-fill:#9C27B0;");
                                        }
                                }
                        });

                        // Ajouter la colonne des actions au TableView
                        action.getColumns().add(actionsColumn);

                } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Error when retrieving data from database");
                        alert.showAndWait();
                }
        }

        void refreshTable() {
                try {
                        // Récupérer à nouveau les données de la base de données
                        List<Rendezvous> rendezvousList = rs.read();
                        // Mettre à jour la liste observable avec les nouvelles données
                        observableList.clear();
                        observableList.addAll(rendezvousList);
                        // Mettre à jour le TableView
                        tv.setItems(observableList);
                } catch (SQLException e) {
                        // Gérer les exceptions
                        e.printStackTrace();
                }
        }

        ImageView generateQRCode(String qrText) {
                int width = 300;
                int height = 300;
                String imageFormat = "png";

                Map<com.google.zxing.EncodeHintType, Object> hintMap = new HashMap<>();
                hintMap.put(com.google.zxing.EncodeHintType.MARGIN, 1);
                hintMap.put(com.google.zxing.EncodeHintType.ERROR_CORRECTION, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.L);

                com.google.zxing.qrcode.QRCodeWriter qrCodeWriter = new com.google.zxing.qrcode.QRCodeWriter();
                com.google.zxing.common.BitMatrix bitMatrix;
                try {
                        bitMatrix = qrCodeWriter.encode(qrText, com.google.zxing.BarcodeFormat.QR_CODE, width, height, hintMap);
                } catch (com.google.zxing.WriterException e) {
                        e.printStackTrace();
                        return null;
                }

                BufferedImage bufferedImage = com.google.zxing.client.j2se.MatrixToImageWriter.toBufferedImage(bitMatrix);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                        javax.imageio.ImageIO.write(bufferedImage, imageFormat, byteArrayOutputStream);
                } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                }

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                javafx.scene.image.Image qrImage = new javafx.scene.image.Image(byteArrayInputStream);

                ImageView qrImageView = new ImageView(qrImage);
                qrImageView.setFitWidth(width);
                qrImageView.setFitHeight(height);

                return qrImageView;
        }
        void generatePDF() throws IOException, DocumentException {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("rendezvous.pdf"));
                document.open();
                document.setPageSize(PageSize.A4);

                PdfPTable table = new PdfPTable(5); // 5 colonnes pour les données de rendezvous

                // Ajouter les en-têtes de colonne
                table.addCell("Nom Patient");
                table.addCell("Nom Médecin");
                table.addCell("Date");
                table.addCell("Heure");
                table.addCell("Etat");

                // Ajouter le contenu du TableView au PDF
                for (Rendezvous rendezvous : tv.getItems()) {
                        table.addCell(rendezvous.getNompatient());
                        table.addCell(rendezvous.getNommedecin());
                        table.addCell(rendezvous.getDate().toString());
                        table.addCell(rendezvous.getHeure());
                        table.addCell(rendezvous.getEtat() ? "Oui" : "Non");
                }

                document.add(table);
                document.close();

                // Ouvrir le PDF généré
                File file = new File("rendezvous.pdf");
                Desktop.getDesktop().open(file);
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
}
