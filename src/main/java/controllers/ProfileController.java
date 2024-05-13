package controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {


    @FXML
    private Label emailLB;

    @FXML
    private Label nomLB;

    @FXML
    private Label prenomLB;

    @FXML
    private ImageView profilePic;

    @FXML
    private Label roleLB;

    @FXML
    void Logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root;
        root = loader.load();
        Scene scene = roleLB.getScene();
        if (scene != null) {
            Stage currentStage = (Stage) scene.getWindow();
            currentStage.close();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setUser(User u){
        emailLB.setText(u.getEmail());
        nomLB.setText(u.getNom());
        prenomLB.setText(u.getPrenom());
        roleLB.setText(u.getRoles());
        profilePic.setImage(new Image(u.getBrochure()));
    }

}
