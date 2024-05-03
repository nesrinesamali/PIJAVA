package Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.IOException;
import java.sql.SQLException;
import models.CentreDon;
import models.Dons;
import services.ServiceCentre;
import services.ServiceDon;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.TilePane;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.ImageView;

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
    private Hyperlink addDonLink;
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
    private AnchorPane vbox;
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
    @FXML
    private Dons selectedDon; // Pour stocker les données du don sélectionné
    @FXML
    private Label dernier,cinn,genre,prochaine,groupe,etat,typedon,centre;
    @FXML
        AnchorPane modif;
    @FXML
    private TextField CIN;

    @FXML
    private ComboBox<String> GENRE;

    @FXML
    private DatePicker DATEPRO;

    @FXML
    private DatePicker DATEDER;

    @FXML
    private ComboBox<String> GROUPE;

    @FXML
    private ComboBox<String> TYPEDON;

    @FXML
    private ComboBox<String> ETAT;
    @FXML
    private ComboBox<CentreDon> CENTRE;
    public ImageView updateButton;
    ServiceCentre sapcentre = new ServiceCentre();
    private ServiceDon donService = new ServiceDon();
    private Dons don;

    @FXML
    public void initialize() {
        // TODO
        typeFLd.setItems(FXCollections.observableArrayList("Sang","Plasma","Organnes"));
        // Initialisation de dateproFLd
        // Attribuer des valeurs aux autres ComboBoxes
        GenreFLd.setItems(FXCollections.observableArrayList("Homme", "Femme"));
        groupeFLd.setItems(FXCollections.observableArrayList("O+", "O-", "A+", "A-", "AB+", "AB-", "B+", "B-"));
        etatFLd.setItems(FXCollections.observableArrayList("Celibataire", "Marié"));
        // Vous pouvez également définir une date par défaut ici si nécessaire
        // Par exemple :
        dateproFLd.setValue(null);
        datederFLd.setValue(null);
        // Charger les informations sur les centres de don
        // TODO
        TYPEDON.setItems(FXCollections.observableArrayList("Sang","Plasma","Organnes"));
        // Initialisation de dateproFLd
        // Attribuer des valeurs aux autres ComboBoxes
        GENRE.setItems(FXCollections.observableArrayList("Homme", "Femme"));
        GROUPE.setItems(FXCollections.observableArrayList("O+", "O-", "A+", "A-", "AB+", "AB-", "B+", "B-"));
        ETAT.setItems(FXCollections.observableArrayList("Celibataire", "Marié"));
        // Vous pouvez également définir une date par défaut ici si nécessaire
        // Par exemple :
        DATEPRO.setValue(null);
        DATEDER.setValue(null);
        // Charger les informations sur les centres de don
        loadCentreDonData();
        setupAddDonLink();
        loadDons();
        LOAD();

    }
    private void LOAD() {
        try {
            List<CentreDon> centreDons = sapcentre.selectAll();
            CENTRE.setItems(FXCollections.observableArrayList(centreDons));
        } catch (SQLException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void loadCentreDonData() {
        try {
            List<CentreDon> centreDons = sapcentre.selectAll();
            centreDonComboBox.setItems(FXCollections.observableArrayList(centreDons));
        } catch (SQLException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
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

        // ImageView pour l'image du don (remplacez "path_vers_votre_image" par le chemin de votre image)
        ImageView imageView = new ImageView(new Image("C:\\Users\\Lenovo\\Desktop\\java\\PIJAVA\\src\\main\\java\\Controller\\cherry-437.png"));
        imageView.setFitWidth(100); // Ajuster la largeur de l'image
        imageView.setPreserveRatio(true); // Préserver le rapport hauteur/largeur de l'image

        // Étiquette pour le type de don
        Label typeLabel = new Label("\uD835\uDE4F\uD835\uDE6E\uD835\uDE65\uD835\uDE5A \uD835\uDE59\uD835\uDE6A \uD835\uDE59\uD835\uDE64\uD835\uDE63\uD835\uDE56\uD835\uDE69\uD835\uDE5E\uD835\uDE64\uD835\uDE63: " + don.getTypeDeDon());
        typeLabel.setStyle("-fx-font-weight: bold; -fx-font-family: Arial;"); // Rendre la police en gras et définir la police de caractères

        // Étiquette pour le CIN du donneur
        Label cinLabel = new Label("\uD835\uDE72\uD835\uDE78\uD835\uDE7D: " + (don.getCin() == null ? "N/A" : don.getCin()));
        cinLabel.setStyle("-fx-font-family: Arial;"); // Définir la police de caractères

        // Étiquette pour le groupe sanguin du donneur
        Label bloodGroupLabel = new Label("\uD835\uDE76\uD835\uDE9B\uD835\uDE98\uD835\uDE9E\uD835\uDE99\uD835\uDE8E \uD835\uDE82\uD835\uDE8A\uD835\uDE97\uD835\uDE90\uD835\uDE9E\uD835\uDE92\uD835\uDE97: " + (don.getGroupeSanguin() == null ? "N/A" : don.getGroupeSanguin()));
        bloodGroupLabel.setStyle("-fx-font-family: Arial;"); // Définir la police de caractères

        // Étiquette pour la date de la prochaine donation
        Label dateLabel = new Label("\uD835\uDE73\uD835\uDE8A\uD835\uDE9D\uD835\uDE8E \uD835\uDE8D\uD835\uDE8E \uD835\uDE7F\uD835\uDE9B\uD835\uDE98\uD835\uDE8C\uD835\uDE91\uD835\uDE8A\uD835\uDE92\uD835\uDE97\uD835\uDE8E \uD835\uDE73\uD835\uDE98\uD835\uDE97\uD835\uDE8A\uD835\uDE9D\uD835\uDE92\uD835\uDE98\uD835\uDE97: " + (don.getDatePro() != null ? don.getDateDer().toString() : "N/A"));
        dateLabel.setStyle("-fx-font-family: Arial;"); // Définir la police de caractères

        // Bouton pour afficher plus de détails sur le don
        Button detailsButton = new Button();
        detailsButton.setOnAction(e -> showDonDetails(don)); // Définir l'action pour afficher les détails du don

        // Ajouter tous les composants à VBox
        card.getChildren().addAll(imageView, typeLabel, cinLabel, bloodGroupLabel, dateLabel, detailsButton);
        card.setAlignment(Pos.CENTER); // Centrer le contenu dans VBox
        return card; // Retourner la carte entièrement construite
    }


    public void setDonData() {
        System.out.println(don);
        // Vérifier si don est null
        if (don != null) {
            // Stocker les données du don sélectionné
            selectedDon = don;
            // Remplir les champs avec les données du don
            CIN.setText(don.getCin());
            GENRE.setValue(don.getGenre());
            DATEPRO.setValue(LocalDate.parse(don.getDatePro()));
            DATEDER.setValue(LocalDate.parse(don.getDateDer()));
            GROUPE.setValue(don.getGroupeSanguin());
            TYPEDON.setValue(don.getTypeDeDon());
            ETAT.setValue(don.getEtatMarital());
            CENTRE.setValue(don.getCentreDon());
        } else {
            // Gérer le cas où don est null
            System.err.println("Erreur: L'objet don est null.");
        }
    }
    private void showDonDetails(Dons dons) {
       this.don=dons;
        setDon();
        hh.setVisible(false);
        this.vbox.setVisible(true);
    }

    private void reloadDons() {

    }

    public void setDon() {
        System.out.println(don);

        cinn.setText("Cin: " + don.getCin());
        groupe.setText("Groupe Sanguin: " + (don.getGroupeSanguin() == null ? "N/A" : don.getGroupeSanguin()));
        prochaine.setText("Date de Prochaine Donation: " + don.getDatePro());
        dernier.setText("Date de dernier Donation: " + don.getDateDer());
        etat.setText("Etat Marital: " + (don.getEtatMarital() == null ? "N/A" : don.getEtatMarital()));
        centre.setText("Centre de Donation: " + (don.getCentreDon() == null ? "N/A" : don.getCentreDon()));
        genre.setText("Genre: " + (don.getGenre() == null ? "N/A" : don.getGenre()));
        typedon.setText("Type de Don: " + (don.getTypeDeDon() == null ? "N/A" : don.getTypeDeDon()));
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
        vbox.setVisible(false);
        modif.setVisible(false);   }

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
    @FXML
    private void handleUpdateButtonClick() {
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
        setDonData();
        modif.setVisible(true);
        vbox.setVisible(false);
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
            donService.insertOne(don);
            System.out.println("Don added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding Don: " + e.getMessage());
        }
    }

        public void MODIFIER(MouseEvent mouseEvent) {
        // Pour CinFld
        if (CinFld.getText().length() == 0) {
            CinFld.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            CinFld.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
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
        String cin = CinFld.getText().trim();
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
            return;
        }
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

        try {
            donService.updateOne(don);
            System.out.println("Donation updated successfully!");
        } catch (Exception e) {
            System.out.println(don);
            e.getMessage();
        }
    }

    @FXML
    private void handleBack() {
            hh.setVisible(true);
            vbox.setVisible(false);
        main_form.setVisible(false);
    }


    public void update(MouseEvent mouseEvent) {
        // Pour CinFld
        if (CIN.getText().length() == 0) {
            CIN.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            CIN.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour GenreFLd
        if (GENRE.getValue() == null || GENRE.getValue().isEmpty()) {
            GENRE.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            GENRE.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour dateproFLd
        if (DATEPRO.getValue() == null) {
            DATEPRO.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            DATEPRO.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour datederFLd
        if (DATEDER.getValue() == null) {
            DATEDER.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            DATEDER.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour groupeFLd
        if (GROUPE.getValue() == null || GROUPE.getValue().isEmpty()) {
            GROUPE.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            GROUPE.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour typeFLd
        if (TYPEDON.getValue() == null || TYPEDON.getValue().isEmpty()) {
            TYPEDON.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            TYPEDON.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour etatFLd
        if (ETAT.getValue() == null || ETAT.getValue().isEmpty()) {
            ETAT.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            ETAT.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

// Pour centreDonComboBox
        if (CENTRE.getValue() == null) {
            CENTRE.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
        } else {
            CENTRE.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        }

        String cin = CIN.getText().trim();
        String selectedGenre = GENRE.getSelectionModel().getSelectedItem();
        String selectedGroupe = GROUPE.getSelectionModel().getSelectedItem();
        String selectedType = TYPEDON.getSelectionModel().getSelectedItem();
        String selectedEtat = ETAT.getSelectionModel().getSelectedItem();
        LocalDate datePro = DATEPRO.getValue();
        LocalDate dateDer = DATEDER.getValue();

        CentreDon selectedCentre = CENTRE.getSelectionModel().getSelectedItem();
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
            donService.updateOne(don);
            System.out.println("Donation updated successfully!");
        } catch (Exception e) {
            System.out.println(don);
            e.getMessage();
        }
    }

}
 /*
    private void Modifier(Dons don) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierDonFront.fxml"));
            Parent root = loader.load();

            FrontController controller = loader.getController();
            controller.setDon(don);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier le Don");
            stage.showAndWait();

            // Mettez à jour l'affichage ou effectuez d'autres actions après la fermeture de la fenêtre de modification si nécessaire
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    */

    /*
    private void deleteDon(int id) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this donation?");
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    donService.deleteOne(don);
                    // Navigate back to DonView after successful deletion
                    handleBack();
                } catch (Exception ex) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error deleting donation: " + ex.getMessage());
                    errorAlert.show();
                }
            }
        });
    }

     */