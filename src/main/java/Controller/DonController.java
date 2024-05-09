package Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import models.CentreDon;
import models.Dons;

import java.awt.event.ActionEvent;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.apache.pdfbox.pdmodel.PDDocument;
import services.ServiceDon;


public class DonController implements Initializable {
    @FXML
    TableView<Dons> DonTable;

    @FXML
    TableColumn<Dons, String> idCol;

    @FXML
    TableColumn<Dons, String> cinCol;

    @FXML
    TableColumn<Dons, String> genreCol;

    @FXML
    TableColumn<Dons, String> dateproCol;

    @FXML
    TableColumn<Dons, String> datederCol;

    @FXML
    TableColumn<Dons, String> groupesangCol;

    @FXML
    TableColumn<Dons, String> typedonCol;
    @FXML
    TableColumn<Dons, String> actionCol;

    @FXML
    TableColumn<Dons, String> etatmarCol;
    @FXML
    TableColumn<Dons, String> centreDonCol;
    @FXML
    TextField searchTextField;
    ObservableList<Dons> DonList = FXCollections.observableArrayList();
    ServiceDon sap = new ServiceDon();


    @FXML
    private void goToCentreFXML(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCentre.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDate();
        getRefrashable();
        System.out.println("vbn,;");

    }

    @FXML
    private void goToCentreView(MouseEvent event) {
        // Vous pouvez mettre ici le code pour naviguer vers la vue Centre
        // Par exemple, si vous avez une autre vue nommée "CentreView.fxml", vous pouvez l'ouvrir ainsi :

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCentre.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle si nécessaire
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void searchAction(KeyEvent event) {
        String keyword = searchTextField.getText().toLowerCase();
        List<Dons> filteredList = DonList.stream()
                .filter(don ->
                        don.getCin().toLowerCase().contains(keyword) ||
                                don.getGenre().toLowerCase().contains(keyword) ||
                                don.getDatePro().toLowerCase().contains(keyword) ||
                                don.getDateDer().toLowerCase().contains(keyword) ||
                                don.getGroupeSanguin().toLowerCase().contains(keyword) ||
                                don.getTypeDeDon().toLowerCase().contains(keyword) ||
                                don.getEtatMarital().toLowerCase().contains(keyword)
                )
                .toList();
        DonTable.setItems(FXCollections.observableArrayList(filteredList));
    }


     private void loadDate() {
        // Configurer les cellules des colonnes pour afficher les données appropriées
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        cinCol.setCellValueFactory(new PropertyValueFactory<>("cin"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        dateproCol.setCellValueFactory(new PropertyValueFactory<>("datePro"));
        datederCol.setCellValueFactory(new PropertyValueFactory<>("dateDer"));
        groupesangCol.setCellValueFactory(new PropertyValueFactory<>("groupeSanguin"));
        typedonCol.setCellValueFactory(new PropertyValueFactory<>("typeDeDon"));
        etatmarCol.setCellValueFactory(new PropertyValueFactory<>("etatMarital"));
         centreDonCol.setCellValueFactory(new PropertyValueFactory<>("centreDon"));

        Callback<TableColumn<Dons, String>, TableCell<Dons, String>> cellFoctory = (TableColumn<Dons, String> param) -> {
            final TableCell<Dons, String> cell = new TableCell<Dons, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        FontAwesomeIconView infoIcon = new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE);

                        deleteIcon.setStyle("-fx-cursor: hand; -glyph-size: 28px; -fx-fill: red;");
                        editIcon.setStyle("-fx-cursor: hand ; -glyph-size:28px; -fx-fill:#00E676;");

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Dons dons = DonTable.getSelectionModel().getSelectedItem();
                            if (dons != null) {
                                try {
                                    if (sap.deleteOne(dons)) {
                                        RefreshTable();
                                    } else {
                                        // Gérer le cas où la suppression a échoué
                                        // Vous pouvez afficher une alerte ou un message pour informer l'utilisateur que la suppression a échoué
                                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(DonController.class.getName()).log(Level.SEVERE, null, ex);
                                    // Gérer l'erreur de suppression ici
                                    // Vous pouvez afficher une alerte ou un message pour informer l'utilisateur de l'échec de la suppression
                                }
                            } else {
                                // Gérer le cas où aucun centre n'est sélectionné
                                // Vous pouvez afficher une alerte ou un message pour informer l'utilisateur de sélectionner un centre
                            }
                        });

                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            Dons dons = DonTable.getSelectionModel().getSelectedItem();

                            if (dons != null) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierDon.fxml"));
                                    Parent parent = loader.load();
                                    ModifierDonFXML modifierDonFXML = loader.getController();
                                    modifierDonFXML.setParentFXMLLoader(DonController.this);

                                    // Appeler setDonData avec les données du don sélectionné
                                    modifierDonFXML.setDonData(dons); // Passer les données du centre sélectionné à setCentreData

                                    Scene scene = new Scene(parent);
                                    Stage stage = new Stage();
                                    stage.setScene(scene);
                                    stage.initStyle(StageStyle.UTILITY);
                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(DonController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                // Gérer le cas où aucun centre n'est sélectionné
                                // Vous pouvez afficher une alerte ou un message pour informer l'utilisateur de sélectionner un centre
                            }
                        });

                        HBox managebtn = new HBox(deleteIcon, editIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        actionCol.setCellFactory(cellFoctory);
        DonTable.setItems(DonList);
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void RefreshTable() {
        try {
            DonList.clear();
            List<Dons> dons = sap.selectAll();
            DonList.addAll(dons);
            DonTable.setItems(DonList);
        } catch (SQLException ex) {
            Logger.getLogger(DonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void handlePieChartButtonClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StatDon.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Statdon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void Excel() {
        Writer writer = null;
        try {
            List<Dons> list = sap.selectAll(); // Récupérez tous les objets Dons depuis la base de données

            // Chemin du fichier CSV
            File file = new File(System.getProperty("user.home") + "/Desktop/Dons.xlsx");

            writer = new BufferedWriter(new FileWriter(file));
            for (Dons don : list) {
                // Format des données au format CSV
                String text = don.getId() + "," + don.getCin() + "," +
                        don.getGenre() + "," + don.getDatePro() + "," +
                        don.getDateDer() + "," + don.getGroupeSanguin() + "," +
                        don.getTypeDeDon() + "," + don.getEtatMarital() + "\n";
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
    private void PrintPdf() {
        String filePath = "C:/Users/Lenovo/Desktop/output.pdf";
        String imagePath = "C:/Users/Lenovo/Desktop/java/PIJAVA/src/main/resources/assets/icons8-blood-sample.gif";
        PDDocument document = new PDDocument(); // Create a new PDDocument instance
        PDFEXPORTDON.exportTableViewToPDF(DonTable, imagePath, filePath, document);
        try {
            document.close(); // Close the document after exporting
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void getAddView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterDon.fxml"));
            Parent parent = loader.load();
            AjouterDonFXML ajoutercentre = loader.getController();
            ajoutercentre.setParentFXMLLoader(this);

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait(); // Attendre que la fenêtre soit fermée avant de rafraîchir les données
            // Appeler la méthode pour rafraîchir les données après la fermeture de la fenêtre d'ajout
        } catch (IOException ex) {
            Logger.getLogger(AjouterDonFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public void getRefrashable() {
        try {
            List<Dons> dons = sap.selectAll();
            DonList.setAll(dons);
            DonTable.refresh(); // Rafraîchir la table pour refléter les nouveaux éléments
        } catch (SQLException ex) {
            Logger.getLogger(DonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @FXML
    private void print(MouseEvent event) {
    }

    public void SetDon(Dons don) {
    }

    public void setDon(Dons don) {
    }



    ///handle
    @FXML
    private void goToreponseView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hh.fxml"));
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
}