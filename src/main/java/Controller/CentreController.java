package Controller;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import models.CentreDon;

import services.ServiceCentre;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CentreController implements Initializable {

    @FXML
    TableView<CentreDon> CentreTable;

    @FXML
    TableColumn<CentreDon, String> idCol;

    @FXML
    TableColumn<CentreDon, String> nomCol;

    @FXML
    TableColumn<CentreDon, String> houvCol;

    @FXML
    TableColumn<CentreDon, String> hfermCol;

    @FXML
    TableColumn<CentreDon, String> lieuCol;
    @FXML
    TableColumn<CentreDon, String> gouvCol;

    @FXML
    TableColumn<CentreDon, String> emailCol;

    @FXML
    TableColumn<CentreDon, String> numCol;

    @FXML
    TableColumn<CentreDon, String> actionCol;

    ObservableList<CentreDon> CentreList = FXCollections.observableArrayList();
    ServiceCentre cap = new ServiceCentre();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDate();
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    void RefreshTable() {
        try {
            CentreList.clear();
            List<CentreDon> dons = cap.selectAll();
            CentreList.addAll(dons);
            CentreTable.setItems(CentreList);
        } catch (SQLException ex) {
            Logger.getLogger(CentreController.class.getName()).log(Level.SEVERE, "Error refreshing table", ex);
            // You might want to display an error message to the user or handle the exception differently
        }
    }


    @FXML
    private void getAddCentre(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutCentre.fxml"));
            Parent parent;
            parent = loader.load();
            AjouterCentre ajouterCentre = loader.getController();
            // Si nécessaire, vous pouvez appeler des méthodes de AjouterCentre ici
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AjouterCentre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

@FXML
    public void getRefrashable() {
        try {
            CentreList.clear();
            List<CentreDon> centre = cap.selectAll();
            CentreList.addAll(centre); // Add data retrieved from the database to CentreList
            CentreTable.setItems(CentreList);
        } catch (SQLException ex) {
            Logger.getLogger(CentreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void print(MouseEvent event) {
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
         getRefrashable();
        Callback<TableColumn<CentreDon, String>, TableCell<CentreDon, String>> cellFoctory = (TableColumn<CentreDon, String> param) -> {
            final TableCell<CentreDon, String> cell = new TableCell<CentreDon, String>() {
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
                            CentreDon centreDon = CentreTable.getSelectionModel().getSelectedItem();
                            if (centreDon != null) {
                                try {
                                    if (cap.deleteOne(centreDon)) {
                                        RefreshTable();
                                    } else {
                                        // Gérer le cas où la suppression a échoué
                                        // Vous pouvez afficher une alerte ou un message pour informer l'utilisateur que la suppression a échoué
                                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(CentreController.class.getName()).log(Level.SEVERE, null, ex);
                                    // Gérer l'erreur de suppression ici
                                    // Vous pouvez afficher une alerte ou un message pour informer l'utilisateur de l'échec de la suppression
                                }
                            } else {
                                // Gérer le cas où aucun centre n'est sélectionné
                                // Vous pouvez afficher une alerte ou un message pour informer l'utilisateur de sélectionner un centre
                            }
                        });

                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            CentreDon centreDon = CentreTable.getSelectionModel().getSelectedItem();

                            if (centreDon != null) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCentre.fxml"));
                                    Parent parent = loader.load();
                                    ModiferCentre modifierCentreController = loader.getController();
                                    modifierCentreController.setParentFXMLLoader(this);

                                    // Appeler setDonData avec les données du don sélectionné
                                    modifierCentreController.setCentreData(centreDon); // Passer les données du centre sélectionné à setCentreData

                                    Scene scene = new Scene(parent);
                                    Stage stage = new Stage();
                                    stage.setScene(scene);
                                    stage.initStyle(StageStyle.UTILITY);
                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(CentreController.class.getName()).log(Level.SEVERE, null, ex);
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
        CentreTable.setItems(CentreList);
    }
}
