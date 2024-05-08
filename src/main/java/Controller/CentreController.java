package Controller;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import models.CentreDon;

import org.apache.pdfbox.pdmodel.PDDocument;
import services.ServiceCentre;


import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CentreController implements Initializable {


    @FXML
    private TableView<CentreDon> CentreTable;

    @FXML
    private TableColumn<CentreDon, String> idCol, nomCol, houvCol, hfermCol, lieuCol, gouvCol, emailCol, numCol, actionCol;
    @FXML
    private ImageView pieChartButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button excelButton;
    private Timeline autoRefreshTimeline;
    private ObservableList<CentreDon> CentreList = FXCollections.observableArrayList();
    private ServiceCentre cap = new ServiceCentre();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDate();
        startAutoRefreshTimeline(); // Démarrez le rafraîchissement automatique
        RefreshTable();
    }

    // Ajoutez cette méthode pour démarrer le rafraîchissement automatique
    private void startAutoRefreshTimeline() {
        autoRefreshTimeline = new Timeline(new KeyFrame(Duration.seconds(30), event -> RefreshTable())); // Rafraîchissez toutes les 30 secondes
        autoRefreshTimeline.setCycleCount(Timeline.INDEFINITE);
        autoRefreshTimeline.play();
    }

    // Ajoutez cette méthode pour arrêter le rafraîchissement automatique (si nécessaire)
    private void stopAutoRefreshTimeline() {
        if (autoRefreshTimeline != null) {
            autoRefreshTimeline.stop();
        }
    }

/*
    private void redirectToQRCodePage(CentreDon product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/QrCodeGenerator.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            QRCodeGenerator qrCodeGeneratorController = loader.getController();

            // Generate QR code containing product details
            qrCodeGeneratorController.generateQRCode(product);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CentreDon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */

    @FXML
    private void Excel( ) {
        Writer writer = null;
        try {
            List<CentreDon> list = cap.selectAll(); // Récupérez tous les objets CentreDon depuis la base de données

            // Chemin du fichier CSV
            File file = new File(System.getProperty("user.home") + "/Desktop/Centre.csv");

            writer = new BufferedWriter(new FileWriter(file));
            for (CentreDon centreDon : list) {
                // Format des données au format CSV
                String text = centreDon.getNom() + "," + centreDon.getHeureouv() + "," +
                        centreDon.getHeureferm() + "," + centreDon.getGouvernorat() + "," +
                        centreDon.getLieu() + "," + centreDon.getEmail() + "," + centreDon.getNum() + "\n";
                writer.write(text);
            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Afficher une alerte après l'exportation du fichier
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export Excel");
            alert.setHeaderText(null);
            alert.setContentText("Excel exporté avec succès !");
            alert.showAndWait();
        }
    }

    @FXML
    private void searchAction(KeyEvent event) {
        String keyword = searchTextField.getText().toLowerCase();
        List<CentreDon> filteredList = CentreList.stream()
                .filter(centre -> centre.getNom().toLowerCase().contains(keyword)
                        || centre.getGouvernorat().toLowerCase().contains(keyword)
                        || centre.getLieu().toLowerCase().contains(keyword))
                .toList();
        CentreTable.setItems(FXCollections.observableArrayList(filteredList));
    }
   // Assuming CentreDon is the type of your TableView items

    @FXML
    private void PrintPdf() {
        String filePath = "C:/Users/Lenovo/Desktop/output.pdf";
        String imagePath = "C:/Users/Lenovo/Desktop/java/PIJAVA/src/main/resources/assets/icons8-blood-sample.gif";
        PDDocument document = new PDDocument(); // Create a new PDDocument instance
       PDFExport.exportTableViewToPDF(CentreTable, imagePath, filePath, document);
        try {
            document.close(); // Close the document after exporting
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handlePieChartButtonClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PieChartWindow.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(stat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    void RefreshTable() {
        try {
            List<CentreDon> dons = cap.selectAll();
            CentreList.setAll(dons);
        } catch (SQLException ex) {
            Logger.getLogger(CentreController.class.getName()).log(Level.SEVERE, "Error refreshing table", ex);
            // Handle exception
        }
    }

    @FXML
    private void getAddCentre(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutCentre.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(AjouterCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadDate() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        houvCol.setCellValueFactory(new PropertyValueFactory<>("heureouv"));
        hfermCol.setCellValueFactory(new PropertyValueFactory<>("heureferm"));
        gouvCol.setCellValueFactory(new PropertyValueFactory<>("gouvernorat"));
        lieuCol.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        numCol.setCellValueFactory(new PropertyValueFactory<>("num"));
        actionCol.setCellFactory(cellFactory());
        CentreTable.setItems(CentreList);
        RefreshTable();
    }

    private Callback<TableColumn<CentreDon, String>, TableCell<CentreDon, String>> cellFactory() {
        return param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    FontAwesomeIconView qrCodeIcon = new FontAwesomeIconView(FontAwesomeIcon.QRCODE); // FontAwesome icon for QR code
                    CentreDon centreDon = getTableView().getItems().get(getIndex());
                    FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                    FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                    FontAwesomeIconView btn = new FontAwesomeIconView(FontAwesomeIcon.MAP_MARKER);
                    qrCodeIcon.setStyle("-fx-cursor: hand ; -glyph-size:28px; -fx-fill:#00E676;"); // Styling for the QR code icon
                    deleteIcon.setStyle("-fx-cursor: hand; -glyph-size: 28px; -fx-fill: red;");
                    editIcon.setStyle("-fx-cursor: hand ; -glyph-size:28px; -fx-fill:#00E676;");
                    btn.setStyle("-fx-cursor: hand ; -glyph-size:28px; -fx-fill:#00E676;");

                    deleteIcon.setOnMouseClicked(event -> {
                        try {
                            if (cap.deleteOne(centreDon)) {
                                RefreshTable();
                            } else {
                                // Handle deletion failure
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(CentreController.class.getName()).log(Level.SEVERE, null, ex);
                            // Handle deletion error
                        }
                    });

                    editIcon.setOnMouseClicked(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCentre.fxml"));
                            Parent parent = loader.load();
                            ModiferCentre modifierCentreController = loader.getController();
                            modifierCentreController.setParentFXMLLoader(this);
                            modifierCentreController.setCentreData(centreDon);

                            Scene scene = new Scene(parent);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(CentreController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    qrCodeIcon.setOnMouseClicked(event -> {
                        // Call method to redirect to QR code page
                        //redirectToQRCodePage(centreDon);
                    });


                    HBox managebtn = new HBox(deleteIcon, editIcon, btn);
                    managebtn.setStyle("-fx-alignment:center");
                    HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                    HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                    HBox.setMargin(btn, new Insets(2, 3, 0, 2));
                    setGraphic(managebtn);
                    setText(null);
                }
            }
        };
    }

    @FXML
    private void goToCentreView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherDon.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle si nécessaire
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

}
    public void setParentFXMLLoader(DonController donController) {

    }
}
