package Controller;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.CentreDon;
import models.Dons;

import services.ServiceCentre;
import services.ServiceDon;

import java.sql.SQLException;
import java.time.LocalDate;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class AjouterDonFXML implements Initializable {

    @FXML
    private TextField CinFld;

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
    @FXML
    private Label cinErrorLabel;

    @FXML
    private Label genreErrorLabel;

    @FXML
    private Label dateproErrorLabel;

    @FXML
    private Label datederErrorLabel;

    @FXML
    private Label groupeErrorLabel;

    @FXML
    private Label typeErrorLabel;

    @FXML
    private Label etatErrorLabel;

    @FXML
    private Label centreDonErrorLabel;
    int donId;
    DonController parentFXMLLoader;
    ServiceDon sap = new ServiceDon();
    ServiceCentre sapcentre = new ServiceCentre();
    private Object selectedType;


    //controle de saisie
    private boolean validateCin(String cin) {
        String cinRegex = "\\d{8}"; // Expression régulière pour 8 chiffres exactement
        if (Pattern.matches(cinRegex, cin)) {
            cinErrorLabel.setText(""); // Effacez le message d'erreur si la saisie est valide
            return true;
        } else {
            cinErrorLabel.setText("CIN must contain exactly 8 digits"); // Affichez un message d'erreur si la saisie est invalide
            return false;
        }
    }



    // Méthode de validation pour le ComboBox GenreFLd
    private boolean validateGenre() {
        String selectedGenre = GenreFLd.getValue();

        if (selectedGenre != null && !selectedGenre.isEmpty()) {
            genreErrorLabel.setText(""); // Effacez le message d'erreur si un genre est sélectionné
            return true;
        } else {
            genreErrorLabel.setText("Please select gender"); // Affichez un message d'erreur si aucun genre n'est sélectionné
            return false;
        }
    }
    private boolean validateEtat() {
        if (etatFLd.getSelectionModel().getSelectedIndex() != -1) {
            etatErrorLabel.setText(""); // Effacez le message d'erreur si une sélection a été faite
            return true;
        } else {
            etatErrorLabel.setText("Please select a marital status"); // Affichez un message d'erreur si aucune sélection n'a été faite
            return false;
        }
    }
    // Méthode de validation pour le ComboBox groupeFLd
    private boolean validateGroupe() {
        if (groupeFLd.getSelectionModel().getSelectedIndex() != -1) {
            groupeErrorLabel.setText(""); // Effacez le message d'erreur si une sélection a été faite
            return true;
        } else {
            groupeErrorLabel.setText("Please select a blood group"); // Affichez un message d'erreur si aucune sélection n'a été faite
            return false;
        }
    }

    // Méthode de validation pour le ComboBox typeFLd
    private boolean validateType() {
        if (typeFLd.getSelectionModel().getSelectedIndex() != -1) {
            typeErrorLabel.setText(""); // Effacez le message d'erreur si une sélection a été faite
            return true;
        } else {
            typeErrorLabel.setText("Please select a donation type"); // Affichez un message d'erreur si aucune sélection n'a été faite
            return false;
        }
    }

    // Méthode de validation pour le ComboBox centreDonComboBox
    private boolean validateCentreDon() {
        if (centreDonComboBox.getSelectionModel().getSelectedItem() != null) {
            centreDonErrorLabel.setText(""); // Effacez le message d'erreur si une sélection a été faite
            return true;
        } else {
            centreDonErrorLabel.setText("Please select a donation center"); // Affichez un message d'erreur si aucune sélection n'a été faite
            return false;
        }
    }

// Ajoutez des gestionnaires d'événements pour déclencher la validation

    @FXML
    private void validateGroupeInput() {
        if (!validateGroupe()) {
            groupeErrorLabel.setText("Please select a Etat.");
        } else {
            groupeErrorLabel.setText(""); // Effacez le message d'erreur si un genre est sélectionné
        }
    }

    @FXML
    private void validateTypeInput() {
        if (!validateType()) {
            typeErrorLabel.setText("Please select a Etat.");
        } else {
            typeErrorLabel.setText(""); // Effacez le message d'erreur si un genre est sélectionné
        }
    }

    @FXML
    private void validateCentreDonInput() {
        if (!validateCentreDon()) {
            centreDonErrorLabel.setText("Please select a Etat.");
        } else {
            centreDonErrorLabel.setText(""); // Effacez le message d'erreur si un genre est sélectionné
        }
    }



    // Ajoutez un gestionnaire d'événements pour déclencher la validation
    @FXML
    private void validateGenreInput() {
        if (!validateGenre()) {
            genreErrorLabel.setText("Please select a gender.");
        } else {
            genreErrorLabel.setText(""); // Effacez le message d'erreur si un genre est sélectionné
        }
    }

    @FXML
    private void validateEtatInput() {
        if (!validateGenre()) {
            etatErrorLabel.setText("Please select a Etat.");
        } else {
            etatErrorLabel.setText(""); // Effacez le message d'erreur si un genre est sélectionné
        }
    }

    @FXML
    private void validateCinInput() {
        String cin = CinFld.getText();
        validateCin(cin);
    }
    @FXML
    private void close(MouseEvent event) {
        FontAwesomeIconView closeIcon = (FontAwesomeIconView) event.getSource();
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void reset() {
        // Reset text fields
        CinFld.setText("");

        // Reset combo boxes
        GenreFLd.getSelectionModel().clearSelection();
        groupeFLd.getSelectionModel().clearSelection();
        typeFLd.getSelectionModel().clearSelection();
        etatFLd.getSelectionModel().clearSelection();

        // Reset date pickers
        dateproFLd.setValue(null);
        datederFLd.setValue(null);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        typeFLd.setItems(FXCollections.observableArrayList("Sang","Plasma","Organnes"));
        // Initialisation de dateproFLd
        dateproFLd = new DatePicker();
        // Attribuer des valeurs aux autres ComboBoxes
        GenreFLd.setItems(FXCollections.observableArrayList("Homme", "Femme"));
        groupeFLd.setItems(FXCollections.observableArrayList("O+", "O-", "A+", "A-", "AB+", "AB-", "B+", "B-"));
        etatFLd.setItems(FXCollections.observableArrayList("Celibataire", "Marié"));
        // Vous pouvez également définir une date par défaut ici si nécessaire
        // Par exemple :
        dateproFLd.setValue(null);
        datederFLd.setValue(null);
        // Charger les informations sur les centres de don
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

    @FXML
    private void save(MouseEvent event) {
// Pour CinFld
        Label errorLabel = new Label("Veuillez entrer votre numéro d'identification nationale contenant 8 chiffres.");
        errorLabel.setTextFill(Color.RED);
        errorLabel.setId("errorLabel"); // Définir un ID unique pour l'étiquette
// Pour CinFld
        if (CinFld.getText().isEmpty() || !Pattern.matches("\\d{8}", CinFld.getText())) { // Vérification de la longueur et du format
            CinFld.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
            errorLabel.setLayoutX(CinFld.getLayoutX());
            errorLabel.setLayoutY(CinFld.getLayoutY() + CinFld.getHeight() + 10); // Ajuster selon votre mise en page
            // Ajouter l'étiquette au parent de CinFld
            ((Pane)CinFld.getParent()).getChildren().add(errorLabel);
        } else {
            CinFld.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Recherche de l'étiquette et suppression si elle existe
            Node errorLabelToRemove = ((Pane)CinFld.getParent()).lookup("#errorLabel");
            if (errorLabelToRemove != null) {
                ((Pane)CinFld.getParent()).getChildren().remove(errorLabelToRemove);
            }
        }

// Pour GenreFLd
        Label genreErrorLabel = new Label("Veuillez sélectionner votre genre.");
        genreErrorLabel.setTextFill(Color.RED);
        genreErrorLabel.setId("genreErrorLabel"); // Définir un ID unique pour l'étiquette
        // Pour GenreFLd
        if (GenreFLd.getValue() == null || GenreFLd.getValue().isEmpty()) {
            GenreFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
            genreErrorLabel.setLayoutX(GenreFLd.getLayoutX());
            genreErrorLabel.setLayoutY(GenreFLd.getLayoutY() + GenreFLd.getHeight() + 10); // Ajuster selon votre mise en page
            // Ajouter l'étiquette au parent de GenreFLd
            ((Pane)GenreFLd.getParent()).getChildren().add(genreErrorLabel);
        } else {
            GenreFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Recherche de l'étiquette et suppression si elle existe
            Node genreErrorLabelToRemove = ((Pane)GenreFLd.getParent()).lookup("#genreErrorLabel");
            if (genreErrorLabelToRemove != null) {
                ((Pane)GenreFLd.getParent()).getChildren().remove(genreErrorLabelToRemove);
            }
        }
// Pour dateproFLd
// Pour dateproFLd
        Label dateproerrorlabel = new Label("Veuillez sélectionner votre date de prochaine don.");
        dateproerrorlabel.setTextFill(Color.RED);
        dateproerrorlabel.setId("dateproerrorlabel"); // Définir un ID unique pour l'étiquette
        if (dateproFLd.getValue() == null) {
            dateproFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Vérifier si le parent du DatePicker est nul avant de rechercher
            if (dateproFLd.getParent() != null) {
                Node existingErrorLabel = ((Pane) dateproFLd.getParent()).lookup("#dateproerrorlabel"); // Correction du nom de l'ID
                if (existingErrorLabel == null) {
                    // Placement de l'étiquette en dessous du champ
                    VBox parent = new VBox(dateproFLd, dateproerrorlabel);
                    VBox.setMargin(dateproerrorlabel, new Insets(5, 0, 0, 0)); // Espacement en haut
                    ((Pane) dateproFLd.getParent()).getChildren().add(parent);
                }
            }
        } else {
            dateproFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Vérifier si le parent du DatePicker est nul avant de rechercher
            if (dateproFLd.getParent() != null) {
                Node dateproErrorLabelToRemove = ((Pane) dateproFLd.getParent()).lookup("#dateproerrorlabel"); // Correction du nom de l'ID
                if (dateproErrorLabelToRemove != null) {
                    ((Pane) dateproFLd.getParent()).getChildren().remove(dateproErrorLabelToRemove);
                }
            }
        }

// Pour datederFLd
        Label datederErrorLabel = new Label("La date de dernier don ne peut pas prendre cette valeur.");
        datederErrorLabel.setTextFill(Color.RED);
        datederErrorLabel.setId("datederErrorLabel"); // Définir un ID unique pour l'étiquette

// Limiter la sélection aux dates antérieures ou égales à aujourd'hui
        datederFLd.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isAfter(LocalDate.now())); // Désactiver les dates futures
            }
        });

// Pour datederFLd
        if (datederFLd.getValue() == null || datederFLd.getValue().isAfter(LocalDate.now())) {
            datederFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
            datederErrorLabel.setLayoutX(datederFLd.getLayoutX());
            datederErrorLabel.setLayoutY(datederFLd.getLayoutY() + datederFLd.getHeight() + 10); // Ajuster selon votre mise en page
            // Ajouter l'étiquette au parent de datederFLd
            ((Pane)datederFLd.getParent()).getChildren().add(datederErrorLabel);
        } else {
            datederFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Recherche de l'étiquette et suppression si elle existe
            Node datederErrorLabelToRemove = ((Pane)datederFLd.getParent()).lookup("#datederErrorLabel");
            if (datederErrorLabelToRemove != null) {
                ((Pane)datederFLd.getParent()).getChildren().remove(datederErrorLabelToRemove);
            }
        }

// Pour groupeFLd
        Label groupeErrorLabel = new Label("Veuillez sélectionner votre groupe sanguin.");
        groupeErrorLabel.setTextFill(Color.RED);
        groupeErrorLabel.setId("groupeErrorLabel"); // Définir un ID unique pour l'étiquette

// Pour groupeFLd
        if (groupeFLd.getValue() == null || groupeFLd.getValue().isEmpty()) {
            groupeFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
            groupeErrorLabel.setLayoutX(groupeFLd.getLayoutX());
            groupeErrorLabel.setLayoutY(groupeFLd.getLayoutY() + groupeFLd.getHeight() + 10); // Ajuster selon votre mise en page
            // Ajouter l'étiquette au parent de groupeFLd
            ((Pane)groupeFLd.getParent()).getChildren().add(groupeErrorLabel);
        } else {
            groupeFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Recherche de l'étiquette et suppression si elle existe
            Node groupeErrorLabelToRemove = ((Pane)groupeFLd.getParent()).lookup("#groupeErrorLabel");
            if (groupeErrorLabelToRemove != null) {
                ((Pane)groupeFLd.getParent()).getChildren().remove(groupeErrorLabelToRemove);
            }
        }


// Pour typeFLd
        Label typeErrorLabel = new Label("Veuillez choisir le type de don.");
        typeErrorLabel.setTextFill(Color.RED);
        typeErrorLabel.setId("typeErrorLabel"); // Définir un ID unique pour l'étiquette

// Pour typeFLd
        if (typeFLd.getValue() == null || typeFLd.getValue().isEmpty()) {
            typeFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
            typeErrorLabel.setLayoutX(typeFLd.getLayoutX());
            typeErrorLabel.setLayoutY(typeFLd.getLayoutY() + typeFLd.getHeight() + 10); // Ajuster selon votre mise en page
            // Ajouter l'étiquette au parent de typeFLd
            ((Pane)typeFLd.getParent()).getChildren().add(typeErrorLabel);
        } else {
            typeFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Recherche de l'étiquette et suppression si elle existe
            Node typeErrorLabelToRemove = ((Pane)typeFLd.getParent()).lookup("#typeErrorLabel");
            if (typeErrorLabelToRemove != null) {
                ((Pane)typeFLd.getParent()).getChildren().remove(typeErrorLabelToRemove);
            }
        }


// Pour etatFLd
        Label etatErrorLabel = new Label("Veuillez choisir votre état matrimonial.");
        etatErrorLabel.setTextFill(Color.RED);
        etatErrorLabel.setId("etatErrorLabel"); // Définir un ID unique pour l'étiquette

// Pour etatFLd
        if (etatFLd.getValue() == null || etatFLd.getValue().isEmpty()) {
            etatFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
            etatErrorLabel.setLayoutX(etatFLd.getLayoutX());
            etatErrorLabel.setLayoutY(etatFLd.getLayoutY() + etatFLd.getHeight() + 10); // Ajuster selon votre mise en page
            // Ajouter l'étiquette au parent de etatFLd
            ((Pane)etatFLd.getParent()).getChildren().add(etatErrorLabel);
        } else {
            etatFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Recherche de l'étiquette et suppression si elle existe
            Node etatErrorLabelToRemove = ((Pane)etatFLd.getParent()).lookup("#etatErrorLabel");
            if (etatErrorLabelToRemove != null) {
                ((Pane)etatFLd.getParent()).getChildren().remove(etatErrorLabelToRemove);
            }
        }


// Pour centreDonComboBox
        Label centreDonErrorLabel = new Label("Veuillez sélectionner un centre.");
        centreDonErrorLabel.setTextFill(Color.RED);
        centreDonErrorLabel.setId("centreDonErrorLabel"); // Définir un ID unique pour l'étiquette

// Pour centreDonComboBox
        if (centreDonComboBox.getValue() == null) {
            centreDonComboBox.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Placer l'étiquette en dessous du champ
            centreDonErrorLabel.setLayoutX(centreDonComboBox.getLayoutX());
            centreDonErrorLabel.setLayoutY(centreDonComboBox.getLayoutY() + centreDonComboBox.getHeight() + 10); // Ajuster selon votre mise en page
            // Ajouter l'étiquette au parent de centreDonComboBox
            ((Pane)centreDonComboBox.getParent()).getChildren().add(centreDonErrorLabel);
        } else {
            centreDonComboBox.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Recherche de l'étiquette et suppression si elle existe
            Node centreDonErrorLabelToRemove = ((Pane)centreDonComboBox.getParent()).lookup("#centreDonErrorLabel");
            if (centreDonErrorLabelToRemove != null) {
                ((Pane)centreDonComboBox.getParent()).getChildren().remove(centreDonErrorLabelToRemove);
            }
        }





        // Si toutes les validations sont réussies, vous pouvez ajouter le don
        Integer cin = null;
        try {
            cin = Integer.valueOf(CinFld.getText());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for Cin.");
            return;
        }

        LocalDate selectedDate2 = datederFLd.getValue();
        LocalDate selectedDate = dateproFLd.getValue();
        String selectedType = typeFLd.getSelectionModel().getSelectedItem();
        String genre = GenreFLd.getSelectionModel().getSelectedItem() != null ? GenreFLd.getSelectionModel().getSelectedItem().toString() : "";
        String groupesang = groupeFLd.getSelectionModel().getSelectedItem() != null ? groupeFLd.getSelectionModel().getSelectedItem().toString() : "";
        String typedon = typeFLd.getSelectionModel().getSelectedItem() != null ? typeFLd.getSelectionModel().getSelectedItem().toString() : "";
        String etatmarital = etatFLd.getSelectionModel().getSelectedItem();

        Dons don = new Dons();
        don.setCin(String.valueOf(Integer.valueOf(cin != null ? String.valueOf(cin) : "")));
        don.setGenre(selectedType);
        don.setDateDer(String.valueOf(selectedDate));
        don.setDatePro(String.valueOf(selectedDate2));
        don.setGroupeSanguin(groupesang);
        don.setTypeDeDon(typedon);
        don.setEtatMarital(etatmarital);

        CentreDon selectedCentre = centreDonComboBox.getSelectionModel().getSelectedItem();
        if (selectedCentre == null) {
            System.out.println("Please select a center.");
            return;
        }

        don.setCentreDon(selectedCentre);

        try {
            sap.insertOne(don);
            System.out.println("Don added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding Don: " + e.getMessage());
        }
    }


    public void setParentFXMLLoader(DonController donController) {
    }

    public void setTextField(int id, String cin, String genre, String dateDernierDon, String dateProchainDon, String typeDon, String groupeSanguin, String etatmarital) {
    }


    public void getRefrashable() {
    }
}