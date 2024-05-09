package org.example.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.models.Medrecord;
import org.example.service.MedrecordService;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditMedRecord implements Initializable {
    private int ID ;

    @FXML
    private MFXButton Modifier;

    @FXML
    private MFXDatePicker Date;

    @FXML
    private MFXTextField Diagnosis;

    @FXML
    private MFXTextField Patient;

    @FXML
    private MFXTextField Test;
    public void setPassedId(int ID) {
        this.ID = ID;
    }
    private Stage primaryStage;
    private MedrecordService es = new MedrecordService();

    public void setPrimaryStage(Stage primaryStage) {



        this.primaryStage = primaryStage;
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Medrecord e1=new Medrecord();
        for (Medrecord e: es.rechercheMedrecord(ID)) {
            e1 = e;
        }
        Date.setText(e1.getDate().toString());
        Diagnosis.setText(e1.getDiagnosis());
        Patient.setText(e1.getPatient());
        Test.setText(e1.getTest());

    }

    public void EditRec(ActionEvent event) {
        if (Diagnosis.getText().isEmpty() || Diagnosis.getText().length() < 3) {
            showAlert("Error", "Diagnosis must not be empty and must have at least 3 characters.");
            return;
        }
        if (Patient.getText().isEmpty() || Patient.getText().length() < 3) {
            showAlert("Error", "Patient must not be empty and must have at least 3 characters.");
            return;
        }
        if (Test.getText().isEmpty() || Test.getText().length() < 3) {
            showAlert("Error", "Test must not be empty and must have at least 3 characters.");
            return;
        }
        LocalDate eventDate = Date.getValue();
        if (eventDate == null) {
            showAlert("Error", "Please select a date.");
            return;
        }
        LocalDate currentDate = LocalDate.now();
        if (eventDate.isBefore(currentDate)) {
            showAlert("Error", "Date cannot be in the past.");
            return;
        }
        LocalDate minDate = LocalDate.of(currentDate.getYear(), 1, 1);
        LocalDate maxDate = LocalDate.of(currentDate.getYear(), 12, 31);
        if (eventDate.isBefore(minDate) || eventDate.isAfter(maxDate)) {
            showAlert("Error", "Date must be within the current year.");
            return;
        }
        es.Edit(ID,Patient.getText(), java.sql.Date.valueOf(Date.getValue()),Diagnosis.getText(),Test.getText());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayPrescription.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene currentScene = ((Node) event.getSource()).getScene();
        currentScene.setRoot(root);
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void back(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayMedRecord.fxml"));
            Parent root = loader.load();

            Scene currentScene = ((Node) actionEvent.getSource()).getScene();

            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
