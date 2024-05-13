package Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.CentreDon;
import services.ServiceCentre;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private Spinner<Integer> houvHourSpinner;
    @FXML
    private Spinner<Integer> houvMinuteSpinner;
    @FXML
    private Spinner<Integer> hfermHourSpinner;
    @FXML
    private Spinner<Integer> hfermMinuteSpinner;
    private ObservableList<CentreDon> CentreList = FXCollections.observableArrayList();
    private CentreDon selectCentre; // Pour stocker les données du don sélectionné
    CentreController parentFXMLLoader;
    private ServiceCentre cap = new ServiceCentre();
    private CentreController parentController;
    private Timeline autoRefreshTimeline;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<String> gouvernorats = Arrays.asList("Ariana", "Beja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kebili", "Le Kef", "Mahdia", "Manouba", "Medenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan");

        // Ajouter la liste des gouvernorats à la zone de sélection
        govFLd.setItems(FXCollections.observableArrayList(gouvernorats));
configureSpinners();

        startAutoRefreshTimeline(); // Démarrez le rafraîchissement automatique
    }

    // Ajoutez cette méthode pour démarrer le rafraîchissement automatique
    private void startAutoRefreshTimeline() {
        autoRefreshTimeline = new Timeline(new KeyFrame(Duration.seconds(0), event -> RefreshTable())); // Rafraîchissez toutes les 30 secondes
        autoRefreshTimeline.setCycleCount(Timeline.INDEFINITE);
        autoRefreshTimeline.play();
    } void RefreshTable() {
        try {
            List<CentreDon> dons = cap.selectAll();
            CentreList.setAll(dons);
        } catch (SQLException ex) {
            Logger.getLogger(CentreController.class.getName()).log(Level.SEVERE, "Error refreshing table", ex);
            // Handle exception
        }
    }


    // Ajoutez cette méthode pour arrêter le rafraîchissement automatique (si nécessaire)
    private void stopAutoRefreshTimeline() {
        if (autoRefreshTimeline != null) {
            autoRefreshTimeline.stop();
        }
    }
    private void configureSpinners() {
        // Configuration des spinners pour les heures
        houvHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        hfermHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));

        // Configuration des spinners pour les minutes
        houvMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        hfermMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
    }
    public void setCentreData(CentreDon centreDon) {
        selectCentre = centreDon;
        nomFLd.setText(centreDon.getNom());
        govFLd.setValue(centreDon.getGouvernorat());
        emailFLd.setText(centreDon.getEmail());
        lieuFLd.setText(centreDon.getLieu());
        numFLd.setText(String.valueOf(centreDon.getNum()));

        // Parse heure d'ouverture et de fermeture
        String houvStr = centreDon.getHeureouv();
        String hfermStr = centreDon.getHeureferm();
        String[] houvParts = houvStr.split(":");
        String[] hfermParts = hfermStr.split(":");
        int houvHour = Integer.parseInt(houvParts[0]);
        int houvMinute = Integer.parseInt(houvParts[1]);
        int hfermHour = Integer.parseInt(hfermParts[0]);
        int hfermMinute = Integer.parseInt(hfermParts[1]);

        // Set spinner values
        houvHourSpinner.getValueFactory().setValue(houvHour);
        houvMinuteSpinner.getValueFactory().setValue(houvMinute);
        hfermHourSpinner.getValueFactory().setValue(hfermHour);
        hfermMinuteSpinner.getValueFactory().setValue(hfermMinute);
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
// Pour le spinner houvHour
        if (houvHourSpinner.getValue() == null) {
            houvHourSpinner.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            houvHourSpinner.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour le spinner houvMinute
        if (houvMinuteSpinner.getValue() == null) {
            houvMinuteSpinner.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            houvMinuteSpinner.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour le spinner hfermHour
        if (hfermHourSpinner.getValue() == null) {
            hfermHourSpinner.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            hfermHourSpinner.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour le spinner hfermMinute
        if (hfermMinuteSpinner.getValue() == null) {
            hfermMinuteSpinner.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            hfermMinuteSpinner.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
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
        // Retrieve updated data from the form fields
        String nom = nomFLd.getText().trim();
        String email = emailFLd.getText().trim();
        String lieu = lieuFLd.getText().trim();
        Integer num = Integer.valueOf(numFLd.getText().trim());
        String gouv = govFLd.getSelectionModel().getSelectedItem();
        Integer houvHour = houvHourSpinner.getValue();
        Integer houvMinute = houvMinuteSpinner.getValue();
        Integer hfermHour = hfermHourSpinner.getValue();
        Integer hfermMinute = hfermMinuteSpinner.getValue();

// Check if any of the required fields are empty
        if (nom.isEmpty() || email.isEmpty() || lieu.isEmpty() || num == null || gouv == null || houvHour == null || houvMinute == null || hfermHour == null || hfermMinute == null) {
            System.err.println("Please fill in all required fields.");
            return; // Exit the method
        }

// Create a new CentreDon object with the updated data
        CentreDon updatedCentre = new CentreDon();
        updatedCentre.setId(selectCentre.getId());
        updatedCentre.setNom(nom);
        updatedCentre.setEmail(email);
        updatedCentre.setLieu(lieu);
        updatedCentre.setNum(num);
        updatedCentre.setGouvernorat(gouv);
        String houvStr = String.format("%02d:%02d", houvHour, houvMinute);
        updatedCentre.setHeureouv(houvStr);
        String hfermStr = String.format("%02d:%02d", hfermHour, hfermMinute);
        updatedCentre.setHeureferm(hfermStr);

// Update the record in the database
        try {
            cap.updateOne(updatedCentre);
            System.out.println("Centre updated successfully!");
            closeWindow(event);
            parentFXMLLoader.RefreshTable();
        } catch (SQLException e) {
            System.err.println("Error updating centre: " + e.getMessage());
        }

    }

    public void setParentFXMLLoader(CentreController centreController) {
        this.parentFXMLLoader = centreController;
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
        numFLd.setText("");

        // Reset combo boxes
        govFLd.getSelectionModel().clearSelection();

        // Reset spinner values
        houvHourSpinner.getValueFactory().setValue(0);
        houvMinuteSpinner.getValueFactory().setValue(0);
        hfermHourSpinner.getValueFactory().setValue(0);
        hfermMinuteSpinner.getValueFactory().setValue(0);
    }
    private void closeWindow(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


    public static void setTextField(int id, String nom, String genre, String dateDernierDon, String dateProchainDon, String typeDon, String groupeSanguin, String etatmarital) {
        // Méthode setTextField() inchangée
    }

    public void setParentFXMLLoader(TableCell<CentreDon, String> tableCell) {
    }
}
