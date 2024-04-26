package Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
        // Pour nomfld
        Label errorLabel = new Label("Veuillez remplir le nom");
        errorLabel.setTextFill(Color.RED);
        errorLabel.setId("errorLabel"); // Définir un ID unique pour l'étiquette

// Pour nomFLd
        String cinText = nomFLd.getText();
        if (cinText.isEmpty()) {
            nomFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
            errorLabel.setLayoutX(nomFLd.getLayoutX());
            errorLabel.setLayoutY(nomFLd.getLayoutY() + nomFLd.getHeight() + 10); // Ajuster selon votre mise en page
            // Ajouter l'étiquette au parent de nomFLd
            ((Pane) nomFLd.getParent()).getChildren().add(errorLabel);
        } else {
            nomFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Recherche de l'étiquette et suppression si elle existe
            Node errorLabelToRemove = ((Pane) nomFLd.getParent()).lookup("#errorLabel");
            if (errorLabelToRemove != null) {
                ((Pane) nomFLd.getParent()).getChildren().remove(errorLabelToRemove);
            }
        }


        // Déclaration de l'étiquette d'erreur pour l'heure d'ouverture
        Label houvErrorLabel = new Label("L'heure d'ouverture est obligatoire.");
        houvErrorLabel.setTextFill(Color.RED);
        houvErrorLabel.setId("houvErrorLabel"); // Définir un ID unique pour l'étiquette

// Récupération de l'heure d'ouverture
        LocalDate houvFLdValue = houvFLd.getValue();

// Vérification si l'heure d'ouverture est vide ou non
        if (houvFLdValue == null) {
            // Si l'heure d'ouverture est vide, définir le style de bordure en rouge
            houvFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
            houvErrorLabel.setLayoutX(houvFLd.getLayoutX());
            houvErrorLabel.setLayoutY(houvFLd.getLayoutY() + houvFLd.getHeight() + 10); // Ajuster selon votre mise en page
            // Ajouter l'étiquette au parent de houvFLd
            ((Pane) houvFLd.getParent()).getChildren().add(houvErrorLabel);
        } else {
            // Si l'heure d'ouverture est sélectionnée, définir le style de bordure en vert
            houvFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Recherche de l'étiquette et suppression si elle existe
            Node errorLabelToRemove = ((Pane) houvFLd.getParent()).lookup("#houvErrorLabel");
            if (errorLabelToRemove != null) {
                ((Pane) houvFLd.getParent()).getChildren().remove(errorLabelToRemove);
            }
        }
// Déclaration de l'étiquette d'erreur pour l'heure de fermeture
        Label hfermErrorLabel = new Label("L'heure de fermeture est obligatoire.");
        hfermErrorLabel.setTextFill(Color.RED);
        hfermErrorLabel.setId("hfermErrorLabel"); // Définir un ID unique pour l'étiquette

// Récupération de l'heure de fermeture
        LocalDate hfermFLdValue = hfermFLd.getValue();

// Vérification si l'heure de fermeture est vide ou non
        if (hfermFLdValue == null) {
            // Si l'heure de fermeture est vide, définir le style de bordure en rouge
            hfermFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
            hfermErrorLabel.setLayoutX(hfermFLd.getLayoutX());
            hfermErrorLabel.setLayoutY(hfermFLd.getLayoutY() + hfermFLd.getHeight() + 10); // Ajuster selon votre mise en page
            // Ajouter l'étiquette au parent de hfermFLd
            ((Pane) hfermFLd.getParent()).getChildren().add(hfermErrorLabel);
        } else {
            // Si l'heure de fermeture est sélectionnée, définir le style de bordure en vert
            hfermFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Recherche de l'étiquette et suppression si elle existe
            Node errorLabelToRemove = ((Pane) hfermFLd.getParent()).lookup("#hfermErrorLabel");
            if (errorLabelToRemove != null) {
                ((Pane) hfermFLd.getParent()).getChildren().remove(errorLabelToRemove);
            }
        }
// Déclaration de l'étiquette d'erreur pour le gouvernorat
        Label govErrorLabel = new Label("Le gouvernorat est obligatoire.");
        govErrorLabel.setTextFill(Color.RED);
        govErrorLabel.setId("govErrorLabel"); // Définir un ID unique pour l'étiquette

// Récupération du gouvernorat sélectionné
        String selectedGov = govFLd.getValue();

// Vérification si un gouvernorat est sélectionné ou non
        if (selectedGov == null || selectedGov.isEmpty()) {
            // Si aucun gouvernorat n'est sélectionné, définir le style de bordure en rouge
            govFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
            govErrorLabel.setLayoutX(govFLd.getLayoutX());
            govErrorLabel.setLayoutY(govFLd.getLayoutY() + govFLd.getHeight() + 10); // Ajuster selon votre mise en page
            // Ajouter l'étiquette au parent de govFLd
            ((Pane) govFLd.getParent()).getChildren().add(govErrorLabel);
        } else {
            // Si un gouvernorat est sélectionné, définir le style de bordure en vert
            govFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Recherche de l'étiquette et suppression si elle existe
            Node errorLabelToRemove = ((Pane) govFLd.getParent()).lookup("#govErrorLabel");
            if (errorLabelToRemove != null) {
                ((Pane) govFLd.getParent()).getChildren().remove(errorLabelToRemove);
            }
        }
// Déclaration de l'étiquette d'erreur pour l'email
        Label emailErrorLabel = new Label("Veuillez saisir une adresse email valide.");
        emailErrorLabel.setTextFill(Color.RED);
        emailErrorLabel.setId("emailErrorLabel"); // Définir un ID unique pour l'étiquette

// Récupération de l'email saisi
        String emailText = emailFLd.getText();

// Vérification si l'email est vide ou s'il est au format valide
        if (emailText.isEmpty() || !emailText.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            // Si l'email est vide ou invalide, définir le style de bordure en rouge
            emailFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
            emailErrorLabel.setLayoutX(emailFLd.getLayoutX());
            emailErrorLabel.setLayoutY(emailFLd.getLayoutY() + emailFLd.getHeight() + 10); // Ajuster selon votre mise en page
            // Ajouter l'étiquette au parent de emailFLd
            ((Pane) emailFLd.getParent()).getChildren().add(emailErrorLabel);
        } else {
            // Si l'email est valide, définir le style de bordure en vert
            emailFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Recherche de l'étiquette et suppression si elle existe
            Node errorLabelToRemove = ((Pane) emailFLd.getParent()).lookup("#emailErrorLabel");
            if (errorLabelToRemove != null) {
                ((Pane) emailFLd.getParent()).getChildren().remove(errorLabelToRemove);
            }
        }
// Déclaration de l'étiquette d'erreur pour le numéro de téléphone
        Label numErrorLabel = new Label("Le numéro de téléphone doit contenir exactement 8 chiffres.");
        numErrorLabel.setTextFill(Color.RED);
        numErrorLabel.setId("numErrorLabel"); // Définir un ID unique pour l'étiquette

// Récupération du numéro de téléphone saisi
        String numText = numFLd.getText();

// Vérification si le numéro de téléphone est vide ou s'il ne contient pas exactement 8 chiffres
        if (numText.isEmpty() || !numText.matches("\\d{8}")) {
            // Si le numéro de téléphone est vide ou invalide, définir le style de bordure en rouge
            numFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
            numErrorLabel.setLayoutX(numFLd.getLayoutX());
            numErrorLabel.setLayoutY(numFLd.getLayoutY() + numFLd.getHeight() + 10); // Ajuster selon votre mise en page
            // Ajouter l'étiquette au parent de numFLd
            ((Pane) numFLd.getParent()).getChildren().add(numErrorLabel);
        } else {
            // Si le numéro de téléphone est valide, définir le style de bordure en vert
            numFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Recherche de l'étiquette et suppression si elle existe
            Node errorLabelToRemove = ((Pane) numFLd.getParent()).lookup("#numErrorLabel");
            if (errorLabelToRemove != null) {
                ((Pane) numFLd.getParent()).getChildren().remove(errorLabelToRemove);
            }
        }
// Déclaration de l'étiquette d'erreur pour le lieu
        Label lieuErrorLabel = new Label("Le lieu doit contenir au moins 8 caractères.");
        lieuErrorLabel.setTextFill(Color.RED);
        lieuErrorLabel.setId("lieuErrorLabel"); // Définir un ID unique pour l'étiquette

// Récupération du texte saisi dans le champ du lieu
        String lieuText = lieuFLd.getText();

// Vérification si le texte du lieu est vide ou s'il contient moins de 8 caractères
        if (lieuText.isEmpty() || lieuText.length() < 8) {
            // Si le lieu est vide ou invalide, définir le style de bordure en rouge
            lieuFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
            lieuErrorLabel.setLayoutX(lieuFLd.getLayoutX());
            lieuErrorLabel.setLayoutY(lieuFLd.getLayoutY() + lieuFLd.getHeight() + 10); // Ajuster selon votre mise en page
            // Ajouter l'étiquette au parent du champ lieuFLd
            ((Pane) lieuFLd.getParent()).getChildren().add(lieuErrorLabel);
        } else {
            // Si le lieu est valide, définir le style de bordure en vert
            lieuFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Recherche de l'étiquette et suppression si elle existe
            Node errorLabelToRemove = ((Pane) lieuFLd.getParent()).lookup("#lieuErrorLabel");
            if (errorLabelToRemove != null) {
                ((Pane) lieuFLd.getParent()).getChildren().remove(errorLabelToRemove);
            }
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
            parentFXMLLoader.RefreshTable();
        } catch (NumberFormatException e) {
            System.out.println("Erreur de format pour le numéro du centre.");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout du centre : " + ex.getMessage());
        }
    }



    public void setParentFXMLLoader(CentreController centreController) {
    }

}



