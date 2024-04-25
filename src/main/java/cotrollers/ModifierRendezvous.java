package cotrollers;

import cotrollers.AfficherRendezvous;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Rendezvous;
import services.RendezvousService;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifierRendezvous implements Initializable {

    @FXML
    private DatePicker date;

    @FXML
    private TextField heure;

    @FXML
    private TextField nommedecin;

    @FXML
    private TextField nompatient;

    private Rendezvous selectedRv;
    private AfficherRendezvous parentFXMLLoader;

    private RendezvousService rs = new RendezvousService();

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setRendezvousData(Rendezvous rendezvous) {
        selectedRv = rendezvous;
        nompatient.setText(rendezvous.getNompatient());
        nommedecin.setText(rendezvous.getNommedecin());
        date.setValue(rendezvous.getDate().toLocalDate());
        heure.setText(rendezvous.getHeure());
    }

    public void setParentFXMLLoader(AfficherRendezvous parentFXMLLoader) {
        this.parentFXMLLoader = parentFXMLLoader;
    }

    @FXML
    public void update(MouseEvent mouseEvent) {
        String Nompatient = nompatient.getText().trim();
        String Nommedecin = nommedecin.getText().trim();
        LocalDate selectedDate = date.getValue();
        String heureString = heure.getText().trim();

        if (Nompatient.isEmpty() || Nommedecin.isEmpty() || selectedDate == null || heureString.isEmpty()) {
            System.err.println("Please fill in all required fields.");
            return; // Exit the method if any required field is empty
        }

        Rendezvous rd = new Rendezvous();
        rd.setId(selectedRv.getId());
        rd.setNommedecin(Nommedecin);
        rd.setNompatient(Nompatient);
        rd.setDate(Date.valueOf(selectedDate));
        rd.setHeure(heureString);

        // Update the rendezvous
        try {
            rs.update(rd);
            System.out.println("Rendezvous updated successfully!");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modifié !");
            alert.setHeaderText(null);
            alert.setContentText("Rendez-vous modifié !");
            alert.showAndWait();
            Stage stage = (Stage) nompatient.getScene().getWindow();

            // Close the current stage
            stage.close();
            // Appeler la méthode refreshTableView() du contrôleur parent pour rafraîchir le TableView
            parentFXMLLoader.RefreshTable() ;
        } catch (Exception e) {
            // Gérer les exceptions
            System.out.println(rd);
            e.getMessage();
        }
    }

}

