package cotrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Rendezvous;
import models.Reponse;
import services.EmailSenderApp;
import services.RendezvousService;
import services.ReponseService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AjoutReponseController {

    ReponseService rs = new ReponseService();
    Reponse response = new Reponse();
    Rendezvous rendezvous = new Rendezvous();

    RendezvousService rvs = new RendezvousService();

    private AfficheReponse afficheReponseController;
    public void setAfficheReponseController(AfficheReponse afficheReponseController) {
        this.afficheReponseController = afficheReponseController;
    }
    public Rendezvous rv;

    @FXML
    private ComboBox<String> choiseCB;

    @FXML
    private Label dateLB;

    @FXML
    void Add(ActionEvent event) throws SQLException, IOException {
        String selectedOption = choiseCB.getValue();
        System.out.println("Selected option: " + selectedOption);
        response.setDescription(selectedOption);
        response.setDate(rv.getDate());
        System.out.println(response.toString());
        rs.create(response);
        rv.setEtat(true);
        rvs.update(rv);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Traité !");
        alert.setHeaderText(null);
        alert.setContentText("Rendez-vous traité !");
        alert.showAndWait();
        Stage stage = (Stage) dateLB.getScene().getWindow();

        // Close the current stage
        stage.close();
        SmsController.sms();
        if (afficheReponseController != null) {
            afficheReponseController.RefreshTable();
        } else {
            System.out.println("AfficheReponseController reference is not set.");
        }
        EmailSenderApp.sendEmail("samali.nesryne@esprit.tn","réponse envoyé", response.getDescription());

    }

    @FXML
    void onClick(ActionEvent event) {

    }


    public void initData(Rendezvous r) {
        rv = r;
        System.out.println(r + "#############################");
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateString = r.getDate().toString();
        LocalDate date = LocalDate.parse(dateString, inputFormatter);
        String formattedDate = outputFormatter.format(date);
        dateLB.setText(formattedDate);

        // Initialize ComboBox options
        ObservableList<String> options = FXCollections.observableArrayList("accepté", "refusé");
        choiseCB.setItems(options);

    }

}
