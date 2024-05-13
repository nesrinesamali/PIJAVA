package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import models.Dons;
import services.ServiceDon;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Statdon implements Initializable {
    @FXML
    public BarChart<String, Integer> barChart;

    @FXML
    public CategoryAxis xAxis;

    @FXML
    public NumberAxis yAxis;

    private final ServiceDon serviceDon = new ServiceDon();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Récupérer les données sur les dons depuis la base de données
            Map<String, Integer> groupesSanguins = serviceDon.countDonsByGroupeSanguin();

            // Récupérer les données du BarChart depuis le serviceDon
            ObservableList<XYChart.Data<String, Integer>> barChartData = FXCollections.observableArrayList();

            // Parcourir les données des groupes sanguins et les ajouter au BarChart
            for (Map.Entry<String, Integer> entry : groupesSanguins.entrySet()) {
                String groupeSanguin = entry.getKey();
                int nombreDons = entry.getValue();

                // Ajouter les données au BarChart
                xAxis = new CategoryAxis();
                yAxis = new NumberAxis();
                barChartData.add(new XYChart.Data<>(groupeSanguin, nombreDons));

            }

            // Configurer les axes
            xAxis.setCategories(FXCollections.observableArrayList(groupesSanguins.keySet()));
            yAxis.setLabel("Nombre de Dons");

            // Créer une série et ajouter les données à cette série
            BarChart.Series<String, Integer> series = new BarChart.Series<>();
            series.getData().addAll(barChartData);

            // Ajouter la série au BarChart
            barChart.getData().add(series);
        } catch (SQLException ex) {
            Logger.getLogger(Statdon.class.getName()).log(Level.SEVERE, "Error loading blood group statistics", ex);
            // Gérer l'erreur
        }
    }

}

