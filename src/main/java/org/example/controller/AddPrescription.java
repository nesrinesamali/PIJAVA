package org.example.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import org.example.models.Medrecord;
import org.example.models.prescription;
import org.example.service.EmailService;
import org.example.service.MedrecordService;
import org.example.service.prescriptionService;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddPrescription implements Initializable {
    private Map<String, Integer> Prescriptionmap=new HashMap<>() ;
    @FXML
    private MFXDatePicker Date;

    @FXML
    private MFXTextField Doctor;

    @FXML
    private MFXTextField Instruction;

    @FXML
    private MFXComboBox<String> MedicalRecord;

    @FXML
    private MFXTextField Medication;

    @FXML
    private MFXTextField Patient;

    @FXML
    private MFXTextField Pharmacy;

    @FXML
    private Label label1;

    @FXML
    private MFXButton openmap;
    @FXML
    private WebView mapweb;
    private WebEngine engine;
    @FXML
    private MFXButton close;

MedrecordService es =new MedrecordService();
prescriptionService exp= new prescriptionService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Medrecord e: es.fetch()) {
            Prescriptionmap.put(e.getDiagnosis(), e.getId());
            MedicalRecord.getItems().add(e.getDiagnosis());
        }
        mapweb.setVisible(false);
        close.setVisible(false);
        engine=mapweb.getEngine();
    }
    public void Ajoutrec(ActionEvent event) {
         final Pattern namePattern = Pattern.compile("[a-zA-Z]{3,}");


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
            exp.add(new prescription(Prescriptionmap.get(MedicalRecord.getValue()), 2,Patient.getText(),Doctor.getText(),Medication.getText(),Instruction.getText(),Pharmacy.getText(), java.sql.Date.valueOf(Date.getValue())));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayPrescription.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene currentScene = ((Node) event.getSource()).getScene();
        currentScene.setRoot(root);
        EmailService ems=new EmailService();
        ems.sendEmail("Sami.boumaiza@esprit.tn","Prescription","Here's the prescription for "+Patient.getText()+"\n Doctor: "+Doctor.getText()+"\n Medications: "+Medication.getText()+"\n Instructions: "+Instruction.getText()+"\n Pharmacy: "+Pharmacy.getText());

    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void back(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayPrescription.fxml"));
            Parent root = loader.load();

            Scene currentScene = ((Node) actionEvent.getSource()).getScene();

            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void map(ActionEvent event) {
        engine.load("https://www.google.com/maps/search/Pharmacies/@36.8906336,10.1840512,15z?entry=ttu");
        mapweb.setVisible(true);
        close.setVisible(true);
    }
    public void close(ActionEvent event) {
        mapweb.setVisible(false);
        close.setVisible(false);
    }
}
