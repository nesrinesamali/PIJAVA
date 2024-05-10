package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            Parent parent = FXMLLoader.load(getClass().getResource("/login.fxml"));

            // Create the scene and set it on the stage
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);

            // Show the stage
            primaryStage.show();
        } catch (IOException e) {
            // Handle FXML loading errors
            System.err.println("Error loading FXML file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Handle any other exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
