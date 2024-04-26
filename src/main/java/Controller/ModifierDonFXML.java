package Controller;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.CentreDon;
import models.Dons;
import services.ServiceCentre;
import services.ServiceDon;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ModifierDonFXML implements Initializable{
    @FXML
    private TextField CinFLd;

    @FXML
    private ComboBox<String> GenreFLd;

    @FXML
    private DatePicker dateproFLd;

    @FXML
    private DatePicker datederFLd;

    @FXML
    private ComboBox<String> groupeFLd;

    @FXML
    private ComboBox<String> typeFLd;

    @FXML
    private ComboBox<String> etatFLd;
    @FXML
    private ComboBox<CentreDon> centreDonComboBox;

    private Dons selectedDon; // Pour stocker les données du don sélectionné
    ServiceCentre sapcentre = new ServiceCentre();
    private ServiceDon sap = new ServiceDon();
    private DonController parentFXMLLoader;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser les ComboBoxes
        typeFLd.setItems(FXCollections.observableArrayList("Sang", "Plasma", "Organnes"));
        GenreFLd.setItems(FXCollections.observableArrayList("Homme", "Femme"));
        groupeFLd.setItems(FXCollections.observableArrayList("O+", "O-", "A+", "A-", "AB+", "AB-", "B+", "B-"));
        etatFLd.setItems(FXCollections.observableArrayList("Celibataire", "Marié"));
        loadCentreDonData();
    }

    private void loadCentreDonData() {
        try {
            List<CentreDon> centreDons = sapcentre.selectAll();
            centreDonComboBox.setItems(FXCollections.observableArrayList(centreDons));
        } catch (SQLException ex) {
            Logger.getLogger(AjouterDonFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setDonData(Dons don) {
        // Stocker les données du don sélectionné
        selectedDon = don;
        // Remplir les champs avec les données du don
        CinFLd.setText(don.getCin());
        GenreFLd.setValue(don.getGenre());
        dateproFLd.setValue(LocalDate.parse(don.getDatePro()));
        datederFLd.setValue(LocalDate.parse(don.getDateDer()));
        groupeFLd.setValue(don.getGroupeSanguin());
        typeFLd.setValue(don.getTypeDeDon());
        etatFLd.setValue(don.getEtatMarital());
    }

    public void setParentFXMLLoader(DonController parentFXMLLoader) {
        this.parentFXMLLoader = parentFXMLLoader;
    }

    @FXML
    private void close(MouseEvent event) {
        FontAwesomeIconView closeIcon = (FontAwesomeIconView) event.getSource();
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();

    }


    public void update(MouseEvent mouseEvent) {
        // Pour CinFld
        if (CinFLd.getText().length() == 0) {
            CinFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            CinFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour GenreFLd
        if (GenreFLd.getValue() == null || GenreFLd.getValue().isEmpty()) {
            GenreFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            GenreFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour dateproFLd
        if (dateproFLd.getValue() == null) {
            dateproFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            dateproFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour datederFLd
        if (datederFLd.getValue() == null) {
            datederFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            datederFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour groupeFLd
        if (groupeFLd.getValue() == null || groupeFLd.getValue().isEmpty()) {
            groupeFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            groupeFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour typeFLd
        if (typeFLd.getValue() == null || typeFLd.getValue().isEmpty()) {
            typeFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            typeFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour etatFLd
        if (etatFLd.getValue() == null || etatFLd.getValue().isEmpty()) {
            etatFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            etatFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour centreDonComboBox
        if (centreDonComboBox.getValue() == null) {
            centreDonComboBox.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            centreDonComboBox.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

        String cin = CinFLd.getText().trim();
        String selectedGenre = GenreFLd.getSelectionModel().getSelectedItem();
        String selectedGroupe = groupeFLd.getSelectionModel().getSelectedItem();
        String selectedType = typeFLd.getSelectionModel().getSelectedItem();
        String selectedEtat = etatFLd.getSelectionModel().getSelectedItem();
        LocalDate datePro = dateproFLd.getValue();
        LocalDate dateDer = datederFLd.getValue();

        CentreDon selectedCentre = centreDonComboBox.getSelectionModel().getSelectedItem();
        if (selectedCentre == null) {
            System.out.println("Please select a center.");
            return;
        }




        // Check if any of the required fields are empty
        if (cin.isEmpty() || selectedGenre == null || selectedGroupe == null || selectedType == null || selectedEtat == null || datePro == null || dateDer == null) {
            System.err.println("Please fill in all required fields.");
            return; // Exit the method
        }

        // Create an instance of Dons with the retrieved data
        Dons don = new Dons();
        don.setId(selectedDon.getId()); // Assuming selectedDon is the donation data you want to update
        don.setCin(cin);
        don.setGenre(selectedGenre);
        don.setGroupeSanguin(selectedGroupe);
        don.setTypeDeDon(selectedType);
        don.setEtatMarital(selectedEtat);
        don.setDatePro(datePro.toString());
        don.setDateDer(dateDer.toString());
        don.setCentreDon(selectedCentre);
        // Update the donation
        try {
            sap.updateOne(don);
            System.out.println("Donation updated successfully!");
        } catch (Exception e) {
            System.out.println(don);
            e.getMessage();
        }
    }

    private Integer parseInteger(String value) {
        // Méthode parseInteger() inchangée
        return 0;
    }

    @FXML
    private void reset() {
        // Reset text fields
        CinFLd.setText("");

        // Reset combo boxes
        GenreFLd.getSelectionModel().clearSelection();
        groupeFLd.getSelectionModel().clearSelection();
        typeFLd.getSelectionModel().clearSelection();
        etatFLd.getSelectionModel().clearSelection();

        // Reset date pickers
        dateproFLd.setValue(null);
        datederFLd.setValue(null);
    }


    public static void setTextField(int id, String cin, String genre, String dateDernierDon, String dateProchainDon, String typeDon, String groupeSanguin, String etatmarital) {
        // Méthode setTextField() inchangée
    }

    public void setCentreData(Dons centreDon) {
    }
}

