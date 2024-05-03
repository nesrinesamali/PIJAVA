/*package Controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import javafx.scene.control.ButtonType;
import javafx.geometry.Insets;
import java.util.Optional;

import models.Dons;
import services.ServiceDon;

public class Details {

    @FXML
    private VBox vbox;  // Ensure this matches fx:id in FXML
    @FXML
    private Label typeLabel, amountLabel, dateLabel;
    @FXML
    private Button updateButton, deleteButton;
    private Dons don;
    private ServiceDon donService = new ServiceDon();

    @FXML
    public void initialize() {
        // Set padding programmatically to avoid FXML load issues
        vbox.setPadding(new Insets(20, 20, 20, 20));
        updateButton.setOnAction(e -> {
            if (don != null) {
                showDonForm(don);
            } else {
                // Gérer le cas où don est null
            }
        });
        // Initialization logic here
    }

    public void setDon(Dons don) {
        this.don = don;
        typeLabel.setText("Cin: " + don.getCin());
        amountLabel.setText("Groupe Sanguin: " + (don.getGroupeSanguin() == null ? "N/A" : don.getGroupeSanguin()));
        dateLabel.setText("Date de Prochaine Donation: " + don.getDatePro());
        updateButton.setOnAction(e -> showDonForm(don));
        deleteButton.setOnAction(e -> deleteDon(don.getId()));
    }

    private void showDonForm(Dons don) {
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

/*
    private void refreshDonDisplay() {
        if (don != null) {
            // Assuming DonService or similar method to fetch the latest data
            Optional<Dons> optionalDon = ServiceDon.(don.getId());
            if (optionalDon.isPresent()) {
                Dons updatedDon = optionalDon.get();
                setDon(updatedDon); // Reuse the setDon method to update the UI
            }
        }
    }


    @FXML
    private void handleBack() {
        initialize(); // Ensure vbox is initialized
        if (vbox != null && vbox.getScene() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/okok.fxml"));
                Parent previousPage = loader.load();
                vbox.getScene().setRoot(previousPage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Cannot navigate back because vbox is not attached to any scene.");
        }
    }




    private void deleteDon(int id) {
        Alert confirmation = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this donation?");
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    donService.deleteOne(don);
                    // Navigate back to DonView after successful deletion
                    handleBack();
                } catch (Exception ex) {
                    Alert errorAlert = new Alert(AlertType.ERROR, "Error deleting donation: " + ex.getMessage());
                    errorAlert.show();
                }
            }
        });
    }
}
*/