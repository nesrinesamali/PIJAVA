package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.UserService;
import entities.User;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class registercontroller {
    private File selectedFile; // Field to store the selected file


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
    void initialize() {
        choicebox.getItems().addAll("médecin", "patient", "donneur");
    }

    @FXML
    void cnt(ActionEvent event) {
        // Votre logique de navigation vers une autre vue
    }


    @FXML
    void GoToLogin(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root;
        root = loader.load();
        Scene scene = pren.getScene();
        if (scene != null) {
            Stage currentStage = (Stage) scene.getWindow();
            currentStage.close();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void validee(ActionEvent event) {
        String motdp = mdp.getText();
        String email = emaill.getText();
        String name = nomm.getText();
        String prenom = pren.getText();
        String role = choicebox.getValue();
        String c_motdp = cmdp.getText(); // Ajoutez cette ligne
        String profilePic = ""; // Initialize with an empty string for now


        // Vérification des champs vides et du rôle sélectionné
        if (motdp.isEmpty() || email.isEmpty() || name.isEmpty() || prenom.isEmpty() || role == null || c_motdp.isEmpty()) {
            showAlert("Error", "Veuillez remplir tous les champs et sélectionner un rôle.");
            return;
        }

        // Vérification du format de l'email
        if (!isValidEmail(email)) {
            showAlert("Error", "Format d'email invalide.");
            return;
        }

        // Validation supplémentaire pour le nom et le prénom : s'assurer qu'ils contiennent uniquement des lettres
        if (!isValidName(name) || !isValidName(prenom)) {
            showAlert("Error", "Le nom et le prénom doivent contenir uniquement des lettres.");
            return;
        }

        // Vérification si les mots de passe correspondent
        if (!motdp.equals(c_motdp)) {
            showAlert("Error", "Les mots de passe ne correspondent pas.");
            return;
        }


        // Ajouter l'utilisateur à la base de données
        try {
            UserService us = new UserService();
            boolean test = us.checkUsernameExists(email);
            if (test){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Email already Registered");
                alert.showAndWait();
            } else {
                profilePic = selectedFile.toURI().toString();
                // Passer une chaîne vide pour la brochure en tant que placeholder
                User newuser = new User(name, email, motdp, prenom, profilePic, role);
                System.out.println(role);
                UserService userService = new UserService();
                newuser.setBrochure("https://i.ibb.co/hCpbz8M/icons8-folder-tree-100.png");
                userService.ajouter(newuser);
                System.out.println(newuser);
                System.out.println("Utilisateur ajouté avec succès !");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
                Parent root;
                root = loader.load();
                Scene scene = pren.getScene();
                if (scene != null) {
                    Stage currentStage = (Stage) scene.getWindow();
                    currentStage.close();
                }
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }

        } catch (Exception e) {
            showAlert("Error", "Erreur lors de l'ajout de l'utilisateur: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidEmail(String email) {
        // Expression régulière pour valider l'email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Vérifie si l'email correspond au regex
        return Pattern.matches(emailRegex, email);
    }


    private boolean isValidName(String name) {
        // Valider si le nom contient uniquement des lettres
        return Pattern.matches("[a-zA-Z]+", name);
    }

    @FXML
    private void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Show open file dialog
        selectedFile = fileChooser.showOpenDialog(null);


        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            System.out.println("Selected file: " + imagePath);
            // Now you can save the imagePath to the database or perform any other operation

        }

    }

}
