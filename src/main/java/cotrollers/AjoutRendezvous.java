package cotrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import models.Rendezvous;
import models.User;
import services.RendezvousService;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    Rendezvous rendezvous;

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
        // Vérifier la séparation d'une heure avec les rendez-vous existants

        Pattern pattern = Pattern.compile("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");
        Matcher matcher = pattern.matcher(selectedHeure);

        if (!matcher.matches()) {
            // Afficher un message d'erreur si le format de l'heure est invalide
            heureErrorLabel.setText("Entrer une heure valide ");
            return;
        }

        // Récupérer les rendez-vous existants pour la même date
        List<Rendezvous> rendezvousExistants = rs.getRendezvousByDate(selectedDate);

        // Vérifier la séparation d'une heure avec les rendez-vous existants
        for (Rendezvous rendezvous : rendezvousExistants) {
            // Calculer la différence entre l'heure du rendez-vous existant et l'heure du nouveau rendez-vous
            //long diffMinutes = java.time.Duration.between(Rendezvous.getHeure(), selectedHeure).toMinutes();
// Convertir les chaînes de caractères en objets LocalTime
            LocalTime heureRendezvous = LocalTime.parse(rendezvous.getHeure());
            LocalTime heureSelectionnee = LocalTime.parse(selectedHeure);

// Calculer la différence de temps entre les deux heures
            long diffMinutes = java.time.Duration.between(heureRendezvous, heureSelectionnee).toMinutes();

            // Vérifier si la différence est inférieure à 60 minutes (une heure)
            if (Math.abs(diffMinutes) < 60) {
                // Afficher un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText("Impossible d'ajouter le rendez-vous");
                alert.setContentText("Il doit y avoir une séparation d'une heure entre les rendez-vous pour la même date.");
                alert.showAndWait();
                return; // Arrêter l'ajout du rendez-vous
            }
        }



// Appliquer les styles CSS à l'alerte
        // Charger le fichier CSS
        String cssPath = "/alert-styles.css";
        URL cssResource = getClass().getResource(cssPath);
        if (cssResource != null) {
            String css = cssResource.toExternalForm();
// Créer une nouvelle alerte
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText("Impossible d'ajouter le rendez-vous");
            alert.setContentText("Il doit y avoir une séparation d'une heure entre les rendez-vous pour la même date.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(cssPath);
            alert.showAndWait();
            // Continue with loading the CSS file and configuring the Alert
            // ...
        } else {
            // Handle case where the CSS resource is not found
            System.err.println("CSS resource not found: " + cssPath);
        }


// Afficher l'alerte



        // Si la validation réussit, créer le nouvel objet Rendezvous et l'ajouter
        // au service pour la création dans la base de données
        // ...



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

    @FXML
    private TextField pictureTF;


}
