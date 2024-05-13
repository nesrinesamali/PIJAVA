package org.example.controller;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import org.example.models.Medrecord;
import org.example.service.MedrecordService;

import java.io.IOException;
import java.time.LocalDate;

public class AddMedRecord {
    @FXML
    private MFXDatePicker Date;

    @FXML
    private MFXTextField Diagnosis;

    @FXML
    private MFXTextField Patient;

    @FXML
    private MFXTextField Test;
    MedrecordService exp=new MedrecordService();

    public void Ajoutrec(javafx.event.ActionEvent event) {
        LocalDate eventDate = Date.getValue();
        LocalDate currentDate = LocalDate.now();
        LocalDate minDate = LocalDate.of(currentDate.getYear(), 1, 1);
        LocalDate maxDate = LocalDate.of(currentDate.getYear(), 12, 31);

        boolean containsBadWords = false;
        try {
            containsBadWords = BadWordsChecker.checkForBadWords(Patient.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (Diagnosis.getText().isEmpty() || Diagnosis.getText().length() < 3) {
            showAlert("Error", "Diagnosis must not be empty and must have at least 3 characters.");
            return;
        }
        else if (Patient.getText().isEmpty() || Patient.getText().length() < 3) {
            showAlert("Error", "Patient must not be empty and must have at least 3 characters.");
            return;
        }
       else  if (Test.getText().isEmpty() || Test.getText().length() < 3) {
            showAlert("Error", "Test must not be empty and must have at least 3 characters.");
            return;
        }
       else  if (eventDate == null) {
            showAlert("Error", "Please select a date.");
            return;
        }
        else if (eventDate.isBefore(currentDate)) {
            showAlert("Error", "Date cannot be in the past.");
            return;
        }
        else if (eventDate.isBefore(minDate) || eventDate.isAfter(maxDate)) {
            showAlert("Error", "Date must be within the current year.");
            return;
        }
        else  if(containsBadWords){
            showAlert("Error", "A profanity is detected.");
            return;
        }

        exp.add(new Medrecord(2, java.sql.Date.valueOf(Date.getValue()),Patient.getText(),Diagnosis.getText(),Test.getText()));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayMedRecord.fxml"));
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
