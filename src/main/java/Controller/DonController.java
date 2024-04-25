package Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.SimpleStringProperty;
import models.Dons;

import java.io.IOException;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
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
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AjouterDonFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void getRefrashable(){
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
    private void print(MouseEvent event) {
    }

    private void loadDate() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        cinCol.setCellValueFactory(new PropertyValueFactory<>("cin"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        dateproCol.setCellValueFactory(new PropertyValueFactory<>("datePro"));
        datederCol.setCellValueFactory(new PropertyValueFactory<>("dateDer"));
        groupesangCol.setCellValueFactory(new PropertyValueFactory<>("groupeSanguin"));
        typedonCol.setCellValueFactory(new PropertyValueFactory<>("typeDeDon"));
        centreDonCol.setCellValueFactory(new PropertyValueFactory<>("id_centre"));
        etatmarCol.setCellValueFactory(new PropertyValueFactory<>("etatMarital"));



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


                        deleteIcon.setStyle("-fx-cursor: hand ; -glyph-size:28px; -fx-fill:#ff1744;");
                        editIcon.setStyle("-fx-cursor: hand ; -glyph-size:28px; -fx-fill:#00E676;");
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                Dons dons = DonTable.getSelectionModel().getSelectedItem();
                                sap.deleteOne(dons);
                                RefreshTable();
                            } catch (SQLException ex) {
                                Logger.getLogger(DonController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            Dons don = DonTable.getSelectionModel().getSelectedItem();
                            if (don != null) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierDon.fxml"));
                                    Parent parent = loader.load();
                                    ModifierDonFXML modifierDonController = loader.getController();

                                    // Appeler setDonData avec les données du don sélectionné
                                    modifierDonController.setDonData(don); // Ici, on passe le don sélectionné à setDonData

                                    Scene scene = new Scene(parent);
                                    Stage stage = new Stage();
                                    stage.setScene(scene);
                                    stage.initStyle(StageStyle.UTILITY);
                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(Controller.DonController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                // Gérer le cas où aucun don n'est sélectionné
                                // Vous pouvez afficher une alerte ou un message pour informer l'utilisateur de sélectionner un don
                            }
                        });


                        HBox managebtn = new HBox(editIcon, deleteIcon);
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
}
