package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Controller.FrontController;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.CentreDon;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



public class Map implements Initializable {

    public static double lon;
    public static double lat;

    @FXML
    private WebView wv;
    @FXML
    private TextField coordinatesField;
    private WebEngine engine;
    @FXML
    private Button btn;
    public static String pos;
    @FXML
    private Button chatbotButton;



    public void setLocation(String location) {
        // Charger la carte correspondante en fonction de la localisation

        // Construire l'URL de la carte en utilisant la localisation
        String url = "https://www.google.com/maps?q=" + location;

        // Charger l'URL dans le composant WebView
        WebEngine webEngine = wv.getEngine();
        webEngine.load(url);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            File file = new File("C:\\Users\\RT0\\Downloads\\TfarhidaJava-gestion-transport\\TfarhidaJava-gestion-transport\\src\\main\\java\\edu\\esprit\\Map\\map1.html");
            URL htmlUrl = file.toURI().toURL();
            engine = wv.getEngine();
            engine.load(htmlUrl.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    private void tt(MouseEvent event) {
        lat = (Double) wv.getEngine().executeScript("lat");
        lon = (Double) wv.getEngine().executeScript("lon");


        //System.out.println("Lat: " + lat);
        //System.out.println("Lon " + lon);
        coordinatesField.setText("Latitude : "+Double.toString(lat)+" Longitude : "+Double.toString(lon));
    }

    public void Retour(ActionEvent event) {
        System.out.println("retourrrr");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


   /* @FXML
    private void sendd(ActionEvent event) throws IOException {
        // Récupérer l'instance du contrôleur actuel
        FXMLLoader loader = new FXMLLoader(getClass().getResource("IteamA.fxml"));
        Parent root = loader.load();
        IteamController controller = loader.getController();

        // Vérifier si le contrôleur a été correctement récupéré
        if (controller != null) {
            // Récupérer l'activité associée au contrôleur
            Activite activite = controller.getActivite();
            if (activite != null) {
                // Récupérer la localisation de l'activité et l'affecter à la méthode setlocal
                String localisation = activite.getLocalisation();
                controller.setLocal(localisation);
            }
        }

        // Afficher la scène
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }*/

   /* public void setLocation(String localisation) {
        // Mettez à jour la localisation dans votre vue de carte
        // Par exemple, vous pouvez afficher la localisation dans un TextField ou une étiquette
        coordinatesField.setText(localisation);
    }*/

    // JavaScript interface object
    private class JavaApp {
        public void exit() {
            Platform.exit();
        }}

}