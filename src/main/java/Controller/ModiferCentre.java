package Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.CentreDon;
import services.ServiceCentre;
import services.ServiceDon;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ModiferCentre implements Initializable {
    @FXML
    private TextField nomFLd;
    @FXML
    private ComboBox<String> govFLd;
    @FXML
    private TextField emailFLd;
    @FXML
    private TextField lieuFLd;
    @FXML
    private TextField numFLd;
    @FXML
    private DatePicker houvFLd;
    @FXML
    private DatePicker hfermFLd;

    private CentreDon selectCentre; // Pour stocker les données du don sélectionné

    private ServiceCentre cap = new ServiceCentre();
    private CentreController parentController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<String> gouvernorats = Arrays.asList("Ariana", "Beja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kebili", "Le Kef", "Mahdia", "Manouba", "Medenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan");

        // Ajouter la liste des gouvernorats à la zone de sélection
        govFLd.setItems(FXCollections.observableArrayList(gouvernorats));

    }


    public void setCentreData(CentreDon centreDon) {
        selectCentre = centreDon;
        nomFLd.setText(centreDon.getNom());
        govFLd.setValue(centreDon.getGouvernorat());
        emailFLd.setText(centreDon.getEmail());
        lieuFLd.setText(centreDon.getLieu());
        numFLd.setText(String.valueOf(centreDon.getNum()));
        houvFLd.setValue(LocalDate.parse(centreDon.getHeureouv()));
        hfermFLd.setValue(LocalDate.parse(centreDon.getHeureferm()));
    }

    public void setParentController(CentreController parentController) {
        this.parentController = parentController;
    }
    @FXML
    private void close(MouseEvent event) {
        FontAwesomeIconView closeIcon = (FontAwesomeIconView) event.getSource();
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void update(MouseEvent event) {
        // Pour CinFld
        if (nomFLd.getText().length() == 0) {
            nomFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            nomFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        // Pour CinFld
        if (nomFLd.getText().length() == 0) {
            nomFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            nomFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour GenreFLd
        if (govFLd.getValue() == null || govFLd.getValue().isEmpty()) {
            govFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            govFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour dateproFLd
        if (houvFLd.getValue() == null) {
            houvFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            houvFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour datederFLd
        if (hfermFLd.getValue() == null) {
            hfermFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            hfermFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

        if (lieuFLd.getText().length() == 0) {
            lieuFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            lieuFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        //
        if (numFLd.getText().length() == 0) {
            numFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            numFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        //
        if (emailFLd.getText().length() == 0) {
            emailFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            emailFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }
        //
        // Retrieve updated data from the form fields
        String nom = nomFLd.getText().trim();
        String email = emailFLd.getText().trim();
        String lieu = lieuFLd.getText().trim();
        Integer num = Integer.valueOf(numFLd.getText().trim());
        String gouv = govFLd.getSelectionModel().getSelectedItem();
        LocalDate houv = houvFLd.getValue();
        System.out.println(houv.getClass());
        LocalDate hferm = hfermFLd.getValue();
        System.out.println(hferm);

        // Check if any of the required fields are empty
        if (nom.isEmpty() || email.isEmpty() || lieu.isEmpty() || num == null || gouv == null || houv == null || hferm == null) {
            System.err.println("Please fill in all required fields.");
            return; // Exit the method
        }
        System.out.println(selectCentre);
        // Create a new CentreDon object with the updated data
        CentreDon updatedCentre = new CentreDon();
        updatedCentre.setId(selectCentre.getId());
        updatedCentre.setNom(nom);
        updatedCentre.setEmail(email);
        updatedCentre.setLieu(lieu);
        updatedCentre.setNum(num);
        updatedCentre.setGouvernorat(gouv);
        String houvStr= houv.toString() ;
        updatedCentre.setHeureouv(houvStr);
        updatedCentre.setHeureferm(hferm.toString());
        System.out.println(updatedCentre);
        // Update the record in the database
        try {
            cap.updateOne(updatedCentre);
            System.out.println("Centre updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating centre: " + e.getMessage());
        }
    }


    private Integer parseInteger(String value) {
        // Méthode parseInteger() inchangée
        return 0;
    }

    @FXML
    private void reset() {
        // Reset text fields
        nomFLd.setText("");
        emailFLd.setText("");
        lieuFLd.setText("");


        // Reset combo boxes
        govFLd.getSelectionModel().clearSelection();
        // Reset date pickers
        houvFLd.setValue(null);
        hfermFLd.setValue(null);
    }


    public static void setTextField(int id, String nom, String genre, String dateDernierDon, String dateProchainDon, String typeDon, String groupeSanguin, String etatmarital) {
        // Méthode setTextField() inchangée
    }

    public void setParentFXMLLoader(TableCell<CentreDon, String> tableCell) {
    }
}
