package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import services.UserService;
import entities.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class registercontroller implements Initializable {

        @FXML
        private ChoiceBox<String> choicebox;

        @FXML
        private PasswordField cmdp;

        @FXML
        private TextField emaill;

        @FXML
        private PasswordField mdp;

        @FXML
        private Label mdp_oublie;

        @FXML
        private TextField nomm;

        @FXML
        private TextField pren;

        @FXML
        void cnt(ActionEvent event) {
                try {
                        // Load the login.fxml file
                        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));

                        // Create a new scene
                        Scene scene = new Scene(root);

                        // Get the stage from the ActionEvent
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        // Set the new scene in the stage
                        stage.setScene(scene);
                        stage.show();
                } catch (IOException e) {
                        e.printStackTrace();
                        // Handle the exception
                }
        }


        @Override
        public void initialize(URL location, ResourceBundle resources) {
                choicebox.getItems().addAll("medecin" , "patient" , "donneur");
        }
        @FXML
        void validee(ActionEvent event) {
                System.out.println("boutton valide clicked");
                String motdp= mdp.getText();
                String role = choicebox.getValue();
                String email = emaill.getText();
                String c_motdp = cmdp.getText();
                String name = nomm.getText();
                String prenom = pren.getText();
                try {


                        // Create a new user object
                        User newuser = new User(name, email,motdp,prenom,"azeaze",role );

                        // Add the new user to the database using userService
                        UserService userservice = new UserService();
                        userservice.ajouter(newuser);
                        System.out.println(newuser.toString() + "3333333333333");
                        System.out.println("user ajouté avec succès !");





                } catch (Exception e) {
                        System.err.println("Erreur lors de l'ajout de l'hébergement: " + e.getMessage());
                }
        }

        }



