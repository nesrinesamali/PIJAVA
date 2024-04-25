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

    }

    public void SetDon(Dons don) {
    }

    public void setDon(Dons don) {
    }
}
