package org.example.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.models.Medrecord;
import org.example.models.prescription;
import org.example.service.MedrecordService;
import org.example.service.prescriptionService;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditPrescription implements Initializable {
    private int ID ;

    public void setPassedId(int ID) {
        this.ID = ID;
    }
    private Stage primaryStage;
    private prescriptionService es = new prescriptionService();
    @FXML
    private MFXDatePicker Date;

    @FXML
    private MFXTextField Doctor;

    @FXML
    private MFXTextField Instruction;

    @FXML
    private MFXTextField Medication;

    @FXML
    private MFXTextField Patient;

    @FXML
    private MFXTextField Pharmacy;

    @FXML
    private MFXButton back;
    @FXML
    private MFXButton Modifier;




    private final Pattern namePattern = Pattern.compile("[a-zA-Z]{3,}");

    @FXML
    void back(ActionEvent event) {

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prescription e1=new prescription();
        for (prescription e: es.rechercheprescription(ID)) {
            e1 = e;
        }
        Date.setText(e1.getCreatedat().toString());
        Instruction.setText(e1.getInstruction());
        Patient.setText(e1.getPatient());
        Medication.setText(e1.getMedication());
        Doctor.setText(e1.getDoctor());
        Pharmacy.setText(e1.getPharmacy());
    }
    public void confirm(javafx.event.ActionEvent event) {
        if (!namePattern.matcher(Patient.getText()).matches()) {
            showAlert("Patient name must be at least 3 letters and contain no numbers.");
            return ;
        }
        if (!namePattern.matcher(Doctor.getText()).matches()) {
            showAlert("Doctor name must be at least 3 letters and contain no numbers.");
            return ;
        }
        if (!namePattern.matcher(Medication.getText()).matches()) {
            showAlert("Medication name must be at least 3 letters and contain no numbers.");
            return ;
        }
        LocalDate eventDate = Date.getValue();
        if (eventDate == null) {
            showAlert("Please select a date.");
            return;
        }
        LocalDate currentDate = LocalDate.now();
        if (eventDate.isBefore(currentDate)) {
            showAlert("Date cannot be in the past.");
            return;
        }
        LocalDate minDate = LocalDate.of(currentDate.getYear(), 1, 1);
        LocalDate maxDate = LocalDate.of(currentDate.getYear(), 12, 31);
        if (eventDate.isBefore(minDate) || eventDate.isAfter(maxDate)) {
            showAlert("Date must be within the current year.");
            return;
        }
        es.Edit(ID,Patient.getText(),Doctor.getText(), java.sql.Date.valueOf(Date.getValue()),Medication.getText(),Instruction.getText(),Pharmacy.getText());

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
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void back(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayPrescription.fxml"));
            Parent root = loader.load();

            Scene currentScene = ((Node) actionEvent.getSource()).getScene();

            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
