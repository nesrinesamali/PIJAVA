package Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.CentreDon;
import services.ServiceCentre;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterCentre implements Initializable {
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



    int CentreId ;
    CentreController parentFXMLLoader ;
    ServiceCentre cap = new ServiceCentre();
    private Object selectedType;
    @FXML
    private void close(MouseEvent event) {
        FontAwesomeIconView closeIcon = (FontAwesomeIconView) event.getSource();
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void reset() {
        // Reset text fields
        nomFLd.setText("");
        lieuFLd.setText("");
        govFLd.getSelectionModel().clearSelection();
        numFLd.setText("");
        emailFLd.setText("");

        // Reset date pickers
        houvFLd.setValue(null);
        hfermFLd.setValue(null);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Liste des gouvernorats de Tunisie
        List<String> gouvernorats = Arrays.asList("Ariana", "Beja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kebili", "Le Kef", "Mahdia", "Manouba", "Medenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan");

        // Ajouter la liste des gouvernorats à la zone de sélection
        govFLd.setItems(FXCollections.observableArrayList(gouvernorats));
    }

    @FXML
    private void save(MouseEvent event) {
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


        String nom = nomFLd.getText();
        String gouv = govFLd.getSelectionModel().getSelectedItem();
        String num = numFLd.getText();
        String email = emailFLd.getText();
        String lieu = lieuFLd.getText();

        LocalDate selectedDate = houvFLd.getValue();
        LocalDate selectedDate2 = hfermFLd.getValue();

        // Vérification des champs obligatoires
        if (nom.isEmpty() || gouv == null || num.isEmpty() || email.isEmpty() || lieu.isEmpty() || selectedDate == null || selectedDate2 == null) {
            System.out.println("Veuillez remplir tous les champs.");
            return;
        }

        try {
            // Création de l'objet CentreDon avec les valeurs récupérées
            CentreDon centreDon = new CentreDon();
            centreDon.setNom(nom);
            centreDon.setGouvernorat(gouv);
            centreDon.setNum(Integer.parseInt(num));
            centreDon.setEmail(email);
            centreDon.setLieu(lieu);
            centreDon.setHeureouv(selectedDate.toString());
            centreDon.setHeureferm(selectedDate2.toString());

            // Insertion du centre dans la base de données
            cap.insertOne(centreDon);
            System.out.println("Centre ajouté avec succès !");
        } catch (NumberFormatException e) {
            System.out.println("Erreur de format pour le numéro du centre.");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout du centre : " + ex.getMessage());
        }
    }



    public void setParentFXMLLoader(CentreController centreController) {
    }

}



