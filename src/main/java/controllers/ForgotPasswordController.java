package controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Random;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.UserService;
import services.EmailSender;


public class ForgotPasswordController {
    @javafx.fxml.FXML
    private TextField emailTF;
    @javafx.fxml.FXML
    public static int code;
    public static String EmailReset ;



    public void send(ActionEvent actionEvent) throws UnsupportedEncodingException {
        code = generateVerificationCode();
        Alert A = new Alert(Alert.AlertType.WARNING);
        UserService su = new UserService();

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        boolean verifMail = emailTF.getText().matches(emailRegex);

        if (!emailTF.getText().equals("") && verifMail) {
            if (su.checkEmailExists(emailTF.getText())) {
                EmailReset = emailTF.getText();
                String appPassword = "xgjw fysh ierw xebu";
                //   EmailSender.sendEmail("mohamedyassinekhlaf@gmail.com", "mamanabiha", emailTF.getText(), "Verification code", "Votre code de verification est : " + code);
                EmailSender.sendEmail("shaymagabsy9@gmail.com", appPassword, emailTF.getText(), "Verification code", "Votre code de verification est : " + code);

                try {

                    Parent page1 = FXMLLoader.load(getClass().getResource("/VerifCode.fxml"));

                    Scene scene = new Scene(page1);

                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                    stage.setScene(scene);

                    stage.show();

                } catch (IOException ex) {

                    System.out.println(ex.getMessage());

                }

            } else {
                A.setContentText("pas de compte lié avec cette adresse ! ");
                A.show();
            }
        } else {
            A.setContentText("Veuillez saisir une adresse mail valide ! ");
            A.show();
        }
    }


    @FXML
    private TextField phoneTF;
    public void sendPasswordViaSMS(ActionEvent actionEvent) throws SQLException {
        String email = emailTF.getText();
        String phoneNumber = phoneTF.getText();

        // Check if email and phone number are provided
        if (email.isEmpty() || phoneNumber.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter your email and phone number!");
            alert.show();
            return;
        }

        // Call SMSController method to send password via SMS
        String password = getPasswordForEmail(email);
        if (password != null) {
            // Send SMS
            SMSController.sendSMS(phoneNumber, "Your password: " + password);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Password sent via SMS!");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No user found with this email!");
            alert.show();
        }
    }

    private String getPasswordForEmail(String email) throws SQLException {
        UserService userService = new UserService();
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return user.getPassword();
        } else {
            return null;
        }
    }

    // Method to get the phone number for the given email

    public static int generateVerificationCode() {
        // Générer un code de vérification aléatoire à 6 chiffres
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}