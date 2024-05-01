package Controller;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.IOException;
import java.sql.SQLException;

import javafx.stage.StageStyle;
import models.CentreDon;
import models.Dons;
import services.ServiceCentre;
import services.ServiceDon;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.TilePane;
import services.ServiceDon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.Dons;
import services.ServiceDon;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontController {
    @FXML
    HBox hh;
    @FXML
    AnchorPane navapp ;
    @FXML
    AnchorPane main_form;
    @FXML
    private TilePane cardsContainer;
    @FXML
    private Hyperlink addDonLink; // Hyperlink to add a new donation

    private ServiceDon donService = new ServiceDon();

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

    @FXML
    public void initialize() {
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
        LocalDate defaultDate = LocalDate.now();
        dateproFLd.setValue(defaultDate);
        LocalDate defaultDate2 = LocalDate.now().plusDays(7); // 7 jours après aujourd'hui
        datederFLd.setValue(defaultDate2);
        // Charger les informations sur les centres de don
        loadCentreDonData();
        setupAddDonLink();
        loadDons();
    }
    private void loadCentreDonData() {
        try {
            List<CentreDon> centreDons = sapcentre.selectAll();
            centreDonComboBox.setItems(FXCollections.observableArrayList(centreDons));
        } catch (SQLException ex) {
            Logger.getLogger(AjouterDonFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setupAddDonLink() {
        addDonLink.setOnMouseClicked(e -> showDonForm(null));
        // Passing null for a new donation
    }

    private void showDonForm(Dons don) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/okok.fxml"));
            AnchorPane main_form = loader.load();
          //  Stage stage = new Stage();
           // stage.setScene(new Scene(main_form));
           // stage.setTitle(don == null ? "Ajouter un Nouveau Don" : "Modifier le Don");
           // stage.showAndWait(); // Use showAndWait to refresh list after adding or updating
            reloadDons(); // Reload the list after closing the form
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        main_form.setVisible(true);
        hh.setVisible(false);
    }



    private void loadDons() {
        cardsContainer.getChildren().clear(); // Clear existing cards before loading new ones
        try {
            for (Dons don : donService.selectAll()) {
                VBox card = createDonCard(don);
                cardsContainer.getChildren().add(card);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private VBox createDonCard(Dons don) {
        VBox card = new VBox(5); // Réduire l'espacement entre les éléments
        card.getStyleClass().add("card-pane"); // Attribuer une classe de style pour le style CSS
        card.setPadding(new Insets(10)); // Réduire la marge autour des éléments

        // Étiquette pour le type de don
        Label typeLabel = new Label("Type: " + don.getTypeDeDon());
        typeLabel.setStyle("-fx-font-weight: bold; -fx-font-family: Arial;"); // Rendre la police en gras et définir la police de caractères

        // Étiquette pour le CIN du donneur
        Label cinLabel = new Label("CIN: " + (don.getCin() == null ? "N/A" : don.getCin()));
        cinLabel.setStyle("-fx-font-family: Arial;"); // Définir la police de caractères

        // Étiquette pour le groupe sanguin du donneur
        Label bloodGroupLabel = new Label("Groupe Sanguin: " + (don.getGroupeSanguin() == null ? "N/A" : don.getGroupeSanguin()));
        bloodGroupLabel.setStyle("-fx-font-family: Arial;"); // Définir la police de caractères

        // Étiquette pour la date de la prochaine donation
        Label dateLabel = new Label("Date de Prochaine Donation: " + (don.getDatePro() != null ? don.getDateDer().toString() : "N/A"));
        dateLabel.setStyle("-fx-font-family: Arial;"); // Définir la police de caractères

        // Bouton pour afficher plus de détails sur le don
        Button detailsButton = new Button("Voir Détails");
        detailsButton.setOnAction(e -> showDonDetails(don)); // Définir l'action pour afficher les détails du don

        // Ajouter tous les composants à VBox
        card.getChildren().addAll(typeLabel, cinLabel, bloodGroupLabel, dateLabel, detailsButton);

        card.setAlignment(Pos.CENTER); // Centrer le contenu dans VBox

        return card; // Retourner la carte entièrement construite
    }


    private void showDonDetails(Dons dons) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Details.fxml"));
            Parent detailsRoot = loader.load();
            Details controller = loader.getController();
            controller.setDon(dons);

            // Use the scene of an existing control that's already part of the current scene
            Scene currentScene = cardsContainer.getScene();
            if (currentScene != null) {
                currentScene.setRoot(detailsRoot);
            } else {
                System.out.println("Cannot change the scene because cardsContainer is not attached to any scene.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void reloadDons() {

    }

    public void setDon(Dons don) {
    }
    @FXML
    private void handleNavigation(ActionEvent event) {
        /*try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Appnav.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de chargement du fichier FXML
        }*/
        navapp.setVisible(true);
        main_form.setVisible(false);

        hh.setVisible(false);
        hh.setManaged(false);
    }
    private void ajout(ActionEvent event) {
        /*try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Appnav.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de chargement du fichier FXML
        }*/
        navapp.setVisible(false);
        hh.setVisible(false);
        hh.setManaged(false);
        main_form.setVisible(true);
    }
    @FXML
    private void handledons(MouseEvent event) {
        /*try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Appnav.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de chargement du fichier FXML
        }*/
        hh.setVisible(true);
        navapp.setVisible(false);
    }

    public void save(MouseEvent mouseEvent) {

// Pour CinFld
        Label errorLabel = new Label("Veuillez entrer votre numéro d'identification nationale.");
        errorLabel.setTextFill(Color.RED);
        errorLabel.setId("errorLabel"); // Définir un ID unique pour l'étiquette

// Pour CinFld
        if (CinFld.getText().isEmpty()) {
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
        if (dateproFLd.getValue() == null) {
            dateproFLd.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
            // Vérifier si le parent du DatePicker est nul avant de rechercher
            if (dateproFLd.getParent() != null) {
                Node existingErrorLabel = ((Pane) dateproFLd.getParent()).lookup("#dateproErrorLabel");
                if (existingErrorLabel == null) {
                    // Placement de l'étiquette en dessous du champ
                    VBox parent = new VBox(dateproFLd, dateproErrorLabel);
                    VBox.setMargin(dateproErrorLabel, new Insets(5, 0, 0, 0)); // Espacement en haut
                    ((Pane) dateproFLd.getParent()).getChildren().add(parent);
                }
            }
        } else {
            dateproFLd.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
            // Vérifier si le parent du DatePicker est nul avant de rechercher
            if (dateproFLd.getParent() != null) {
                Node dateproErrorLabelToRemove = ((Pane) dateproFLd.getParent()).lookup("#dateproErrorLabel");
                if (dateproErrorLabelToRemove != null) {
                    ((Pane) dateproFLd.getParent()).getChildren().remove(dateproErrorLabelToRemove);
                }
            }
        }



// Pour datederFLd
        Label datederErrorLabel = new Label("Veuillez sélectionner la date de votre dernier don.");
        datederErrorLabel.setTextFill(Color.RED);
        datederErrorLabel.setId("datederErrorLabel"); // Définir un ID unique pour l'étiquette

// Pour datederFLd
        if (datederFLd.getValue() == null) {
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
}
