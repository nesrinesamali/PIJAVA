package cotrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Rendezvous;
import services.RendezvousService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;


public class AjoutRendezvous {

    @FXML
    private DatePicker date;

    @FXML
    private TextField heure;

    @FXML
    private TextField nommedecin;

    @FXML
    private TextField nompatient;
RendezvousService rs = new RendezvousService();
    @FXML
    void AjoutRendezvous(ActionEvent event) {
        try {
            // Récupérer les valeurs des champs GUI
            LocalDate selectedDate = date.getValue();
            String selectedHeure = heure.getText();
            String selectedNomMedecin = nommedecin.getText();
            String selectedNomPatient = nompatient.getText();

            // Vérifier si des champs obligatoires sont vides
            if (selectedDate == null || selectedHeure.isEmpty() || selectedNomMedecin.isEmpty() || selectedNomPatient.isEmpty()) {
                // Afficher un message d'erreur ou gérer la situation d'une autre manière
                System.out.println("Veuillez remplir tous les champs.");
                return;
            }

            // Créer un nouvel objet Rendezvous avec les valeurs récupérées
            Rendezvous newRendezvous = new Rendezvous();
            newRendezvous.setDate(Date.valueOf(selectedDate)); // Conversion de LocalDate en java.sql.Date
            newRendezvous.setHeure(selectedHeure);
            newRendezvous.setNommedecin(selectedNomMedecin);
            newRendezvous.setNompatient(selectedNomPatient);

            // Envoyer l'objet au service pour la création dans la base de données
            rs.create(newRendezvous);

            // Réinitialiser les champs après la création réussie
            date.setValue(null);
            heure.clear();
            nommedecin.clear();
            nompatient.clear();

            // Afficher un message de succès ou gérer la situation d'une autre manière
            System.out.println("Rendez-vous ajouté avec succès.");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERORE");
            alert.setContentText("Error in navigation");
            alert.showAndWait();
        }
    }


}
