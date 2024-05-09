package Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.CentreDon;
import org.w3c.dom.Text;
import services.ServiceCentre;
import test.HelloApplication;
import test.Main;
import utils.sendMail;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Map;

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
    private Spinner<Integer> houvHourSpinner;
    @FXML
    private Spinner<Integer> houvMinuteSpinner;
    @FXML
    private Spinner<Integer> hfermHourSpinner;
    @FXML
    private Spinner<Integer> hfermMinuteSpinner;

    static float latitude;
    static float longitude;


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

    }
    public void savecoords(String latitude, String longitude) {
        AjouterCentre.latitude = Float.parseFloat(latitude);
        AjouterCentre.longitude = Float.parseFloat(longitude);
        System.out.println("LAT LNG FROM CONTROLLER BORNE" + latitude + "  " + longitude);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Liste des gouvernorats de Tunisie
        List<String> gouvernorats = Arrays.asList("Ariana", "Beja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kebili", "Le Kef", "Mahdia", "Manouba", "Medenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan");

        // Ajouter la liste des gouvernorats à la zone de sélection
        govFLd.setItems(FXCollections.observableArrayList(gouvernorats));
        houvHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        houvMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        hfermHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        hfermMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));

        // Rest of your initialization code...
    }
    // Méthode pour définir le style d'erreur et ajouter une étiquette d'erreur
    private void setErrorStyleAndLabel(Control control, String errorMessage, boolean isValid) {
        if (isValid) {
            // Si la saisie est valide, définir le style de bordure en vert
            control.setStyle("-fx-border-color: green ; -fx-border-width: 2px;");
        } else {
            // Si la saisie est invalide, définir le style de bordure en rouge
            control.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");

            // Créer l'étiquette d'erreur
            Label errorLabel = new Label(errorMessage);
            errorLabel.setTextFill(Color.RED);
            errorLabel.setId(control.getId() + "ErrorLabel"); // Définir un ID unique pour l'étiquette

            // Placer l'étiquette en dessous du champ
            errorLabel.setLayoutX(control.getLayoutX());
            errorLabel.setLayoutY(control.getLayoutY() + control.getHeight() + 10); // Ajuster selon votre mise en page

            // Ajouter l'étiquette au parent du contrôle
            ((Pane) control.getParent()).getChildren().add(errorLabel);
        }
    }

    @FXML
    private void save(MouseEvent event) {
        List<String> errorMessages = new ArrayList<>();

        // Vérification du champ "nom"
        String nomText = nomFLd.getText();
        boolean isNomValid = !nomText.isEmpty();
        setErrorStyleAndLabel(nomFLd, "Veuillez remplir le nom.", isNomValid);
        if (!isNomValid) errorMessages.add("Veuillez remplir le nom.");

        // Vérification du gouvernorat
        String gouv = govFLd.getValue();
        boolean isGouvValid = gouv != null && !gouv.isEmpty();
        setErrorStyleAndLabel(govFLd, "Le gouvernorat est obligatoire.", isGouvValid);
        if (!isGouvValid) errorMessages.add("Le gouvernorat est obligatoire.");

        // Vérification du champ "numéro de téléphone"
        String numText = numFLd.getText();
        boolean isNumValid = !numText.isEmpty() && numText.matches("\\d{8}");
        setErrorStyleAndLabel(numFLd, "Le numéro de téléphone doit contenir exactement 8 chiffres.", isNumValid);
        if (!isNumValid) errorMessages.add("Le numéro de téléphone doit contenir exactement 8 chiffres.");

        // Vérification de l'adresse email
        String emailText = emailFLd.getText();
        boolean isEmailValid = !emailText.isEmpty() && emailText.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        setErrorStyleAndLabel(emailFLd, "Veuillez saisir une adresse email valide.", isEmailValid);
        if (!isEmailValid) errorMessages.add("Veuillez saisir une adresse email valide.");

        // Vérification du champ "lieu"
        String lieuText = lieuFLd.getText();
        boolean isLieuValid = !lieuText.isEmpty() && lieuText.length() >= 8;
        setErrorStyleAndLabel(lieuFLd, "Le lieu doit contenir au moins 8 caractères.", isLieuValid);
        if (!isLieuValid) errorMessages.add("Le lieu doit contenir au moins 8 caractères.");

        // Récupération des heures d'ouverture et de fermeture
        int houvHour = houvHourSpinner.getValue();
        int houvMinute = houvMinuteSpinner.getValue();
        int hfermHour = hfermHourSpinner.getValue();
        int hfermMinute = hfermMinuteSpinner.getValue();

        // Vérification des heures d'ouverture et de fermeture
        boolean isHouvValid = houvHour != 0 || houvMinute != 0;
        setErrorStyleAndLabel(houvHourSpinner, "L'heure d'ouverture est obligatoire.", isHouvValid);
        if (!isHouvValid) errorMessages.add("L'heure d'ouverture est obligatoire.");

        boolean isHfermValid = hfermHour != 0 || hfermMinute != 0;
        setErrorStyleAndLabel(hfermHourSpinner, "L'heure de fermeture est obligatoire.", isHfermValid);
        if (!isHfermValid) errorMessages.add("L'heure de fermeture est obligatoire.");

        // Afficher toutes les erreurs
        if (!errorMessages.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (String error : errorMessages) {
                errorMessage.append(error).append("\n");
            }
            System.out.println(errorMessage.toString());
            return;
        }

        // Construction des objets LocalTime à partir des valeurs des spinners
        LocalTime houvTime = LocalTime.of(houvHour, houvMinute);
        LocalTime hfermTime = LocalTime.of(hfermHour, hfermMinute);

        try {
            // Création de l'objet CentreDon avec les valeurs récupérées
            CentreDon centreDon = new CentreDon();
            centreDon.setNom(nomText);
            centreDon.setGouvernorat(gouv);
            centreDon.setNum(Integer.parseInt(numText));
            centreDon.setEmail(emailText);
            centreDon.setLieu(lieuText);
            centreDon.setHeureouv(houvTime.toString());
            centreDon.setHeureferm(hfermTime.toString());
            Map<String, String> emailData = new HashMap<>();
            Map<String, String> data = new HashMap<>();
            data.put("titlePlaceholder", "Transaction effectuée avec succées");
            data.put("msgPlaceholder", "Hello, this is the email message.");
            data.put("emailSubject", "Transaction effectuée avec succées");

            // Insertion du centre dans la base de données
            cap.insertOne(centreDon);
            System.out.println("Centre ajouté avec succès !");
            closeWindow(event);

            sendMail.send(data);

            // Suppose que vous avez une instance du contrôleur stat appelée statController
        } catch (NumberFormatException e) {
            System.out.println("Erreur de format pour le numéro du centre.");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout du centre : " + ex.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String readHtmlContent(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return ""; // Return empty string in case of error
        }
    }
    private void closeWindow(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}



