package cotrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Rendezvous;
import models.User;
import services.RendezvousService;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AjoutRendezvous implements Initializable {

    @FXML
    private DatePicker date;

    @FXML
    private TextField heure;

    @FXML
    private TextField nommedecin;

    @FXML
    private TextField nompatient;

    @FXML
    private Label nommedecinErrorLabel;

    @FXML
    private Label nompatientErrorLabel;

    @FXML
    private Label dateErrorLabel;

    @FXML
    private Label heureErrorLabel;

    RendezvousService rs = new RendezvousService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void AjoutRendezvous(ActionEvent event) throws SQLException {
        // Récupérer les valeurs des champs GUI
        LocalDate selectedDate = date.getValue();
        String selectedHeure = heure.getText();
        String selectedNomMedecin = nommedecin.getText();
        String selectedNomPatient = nompatient.getText();


        // Vérifier si des champs obligatoires sont vides
        if (selectedDate == null || selectedHeure.isEmpty() || selectedNomMedecin.isEmpty() || selectedNomPatient.isEmpty()) {
            // Afficher un message d'erreur ou gérer la situation d'une autre manière
            dateErrorLabel.setText(selectedDate == null ? "Veuillez choisir une date." : "");
            heureErrorLabel.setText(selectedHeure.isEmpty() ? "Veuillez entrer une heure." : "");
            nommedecinErrorLabel.setText(selectedNomMedecin.isEmpty() ? "Veuillez entrer le nom du médecin (minimum 4 caractères)." : "");
            nompatientErrorLabel.setText(selectedNomPatient.isEmpty() ? "Veuillez entrer le nom du patient (minimum 4 caractères)." : "");
            return;
        }

        // Validation de la longueur minimale pour le nom du médecin et du patient
        if (selectedNomMedecin.length() < 4 || selectedNomPatient.length() < 4) {
            // Afficher un message d'erreur si la longueur minimale n'est pas respectée
            nommedecinErrorLabel.setText("Le nom du médecin doit avoir au moins 4 caractères.");
            nompatientErrorLabel.setText("Le nom du patient doit avoir au moins 4 caractères.");
            return;
        }

        // Créer un nouvel objet Rendezvous avec les valeurs récupérées
        Rendezvous newRendezvous = new Rendezvous();
        newRendezvous.setDate(Date.valueOf(selectedDate)); // Conversion de LocalDate en java.sql.Date
        newRendezvous.setHeure(selectedHeure);
        newRendezvous.setNommedecin(selectedNomMedecin);
        newRendezvous.setNompatient(selectedNomPatient);

        RendezvousService rs = new RendezvousService();
        User user = rs.getUserById(2);

        newRendezvous.setUser(user);
        newRendezvous.setReponse_id(1);

        System.out.println(newRendezvous);

        // Envoyer l'objet au service pour la création dans la base de données
        try {
            rs.create(newRendezvous);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Réinitialiser les champs après la création réussie
        date.setValue(null);
        heure.clear();
        nommedecin.clear();
        nompatient.clear();

        // Réinitialiser les labels d'erreur
        dateErrorLabel.setText("");
        heureErrorLabel.setText("");
        nommedecinErrorLabel.setText("");
        nompatientErrorLabel.setText("");

        // Afficher un message de succès ou gérer la situation d'une autre manière
        System.out.println("Rendez-vous ajouté avec succès.");
    }

    @FXML
    void show(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/test.fxml"));
            Parent root = loader.load();
            nompatient.getScene().setRoot(root);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERORE");
            alert.setContentText("Error in navigation");
            alert.showAndWait();
        }
    }

}
