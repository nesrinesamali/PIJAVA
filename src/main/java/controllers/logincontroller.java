package controllers;

import Controller.FrontController;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.UserService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class logincontroller {
    User ConnectUser;

    @FXML
    private TextField emailTF;
    public static User user;
@FXML
    private PasswordField mdpTF;

    @FXML
    void Connect(ActionEvent event) {
        String nom = emailTF.getText();
        String password = mdpTF.getText();

        if (nom.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter both username and password");
        } else {
            try {
                UserService userService = new UserService();
                User loggedInUser = userService.login(nom, password);
                if (loggedInUser != null) {
                    System.out.println(loggedInUser);
                    logincontroller.user=loggedInUser;
                    if (loggedInUser.getRoles().contains("ADMIN")) {
                        System.out.println("Loading admin interface");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
                        Parent root = loader.load();
                        emailTF.getScene().setRoot(root);
                    } else {
                        UserService us = new UserService();
                        User connectUser = us.getUserByEmail(nom);
                        System.out.println("Loading user profile");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/okok.fxml"));
                        Parent root = loader.load();
                        emailTF.getScene().setRoot(root);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                }
            } catch (SQLException | IOException ex) {
                JOptionPane.showMessageDialog(null, "An error occurred while logging in. Please try again later.");
                ex.printStackTrace();
            }
        }

        }

        @FXML
        void GoToRegister(MouseEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/register.fxml"));
            Parent root = loader.load();
            Scene scene = emailTF.getScene();
            if (scene != null) {
                Stage currentStage = (Stage) scene.getWindow();
                currentStage.close();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }

        @FXML
        private void forgor() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/forgotPassword.fxml"));
                Parent root = loader.load();
                emailTF.getScene().setRoot(root);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        }
