/*package Controller;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.IOException;
import java.sql.SQLException;

import javafx.scene.Parent;

import javafx.stage.StageStyle;
import models.Dons;
import services.ServiceDon;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.TilePane;
import services.ServiceDon;

public class DonFront  {
    @FXML
    private TilePane cardsContainer;
    @FXML
    private Hyperlink addDonLink; // Hyperlink to add a new donation

    private ServiceDon donService = new ServiceDon();

    @FXML
    public void initialize() {
        setupAddDonLink();
        loadDons();
    }

    private void setupAddDonLink() {
        addDonLink.setOnMouseClicked(e -> showDonForm(null));
        // Passing null for a new donation
    }

    private void showDonForm(Dons don) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/okok.fxml"));

            StackPane form = loader.load();
            AjouterDonFront controller = loader.getController(); // Change to AjouterDonFXML
           /* if (don != null) {
                controller.setDon(don); // If updating an existing donation
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(form));
            stage.setTitle(don == null ? "Ajouter un Nouveau Don" : "Modifier le Don");
            stage.showAndWait(); // Use showAndWait to refresh list after adding or updating
            reloadDons(); // Reload the list after closing the form
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void loadDons() {
        cardsContainer.getChildren().clear(); // Clear existing cards before loading new ones
        try {
            for (Dons don : donService.selectAll()) {
                VBox card = createDonCard(don);
                cardsContainer.getChildren().add(card);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private VBox createDonCard(Dons don) {
        VBox card = new VBox(5); // Réduire l'espacement entre les éléments
        card.getStyleClass().add("card-pane"); // Attribuer une classe de style pour le style CSS
        card.setPadding(new Insets(10)); // Réduire la marge autour des éléments

        // Étiquette pour le type de don
        Label typeLabel = new Label("Type: " + don.getTypeDeDon());
        typeLabel.setStyle("-fx-font-weight: bold; -fx-font-family: Arial;"); // Rendre la police en gras et définir la police de caractères

        // Étiquette pour le CIN du donneur
        Label cinLabel = new Label("CIN: " + (don.getCin() == null ? "N/A" : don.getCin()));
        cinLabel.setStyle("-fx-font-family: Arial;"); // Définir la police de caractères

        // Étiquette pour le groupe sanguin du donneur
        Label bloodGroupLabel = new Label("Groupe Sanguin: " + (don.getGroupeSanguin() == null ? "N/A" : don.getGroupeSanguin()));
        bloodGroupLabel.setStyle("-fx-font-family: Arial;"); // Définir la police de caractères

        // Étiquette pour la date de la prochaine donation
        Label dateLabel = new Label("Date de Prochaine Donation: " + (don.getDatePro() != null ? don.getDateDer().toString() : "N/A"));
        dateLabel.setStyle("-fx-font-family: Arial;"); // Définir la police de caractères

        // Bouton pour afficher plus de détails sur le don
        Button detailsButton = new Button("Voir Détails");
        detailsButton.setOnAction(e -> showDonDetails(don)); // Définir l'action pour afficher les détails du don

        // Ajouter tous les composants à VBox
        card.getChildren().addAll(typeLabel, cinLabel, bloodGroupLabel, dateLabel, detailsButton);

        card.setAlignment(Pos.CENTER); // Centrer le contenu dans VBox

        return card; // Retourner la carte entièrement construite
    }


    private void showDonDetails(Dons dons) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Details.fxml"));
            Parent detailsRoot = loader.load();
            Details controller = loader.getController();
            controller.setDon(dons);

            // Use the scene of an existing control that's already part of the current scene
            Scene currentScene = cardsContainer.getScene();
            if (currentScene != null) {
                currentScene.setRoot(detailsRoot);
            } else {
                System.out.println("Cannot change the scene because cardsContainer is not attached to any scene.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void reloadDons() {

    }

    public void setDon(Dons don) {
    }
}*/
