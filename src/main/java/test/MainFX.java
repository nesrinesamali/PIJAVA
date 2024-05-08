package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class MainFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register");
        primaryStage.show();
    }

    // Méthode pour ouvrir une nouvelle scène lors du clic sur un bouton
    public void openLoginScene(ActionEvent event) {
        try {
            // Charger le fichier FXML pour la nouvelle scène (login.fxml)
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/login.fxml"));

            // Créer une nouvelle scène
            Scene loginScene = new Scene(loginRoot);

            // Récupérer la fenêtre principale à partir de l'événement
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène dans la fenêtre principale
            mainStage.setScene(loginScene);
            mainStage.setTitle("Login");
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }
}
