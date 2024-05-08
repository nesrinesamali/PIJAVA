package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.CentreDon;
import services.ServiceCentre;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class stat implements Initializable {
    @FXML
    public PieChart pieChart;


    private ObservableList<CentreDon> CentreList = FXCollections.observableArrayList();
    private ServiceCentre cap = new ServiceCentre();


    private void initializePieChart() {
        try {
            // Récupérer les données des centres depuis la base de données
            List<CentreDon> centres = cap.selectAll();

            // Créer une map pour stocker le nombre de centres par gouvernorat
            Map<String, Integer> centresParGouvernorat = new HashMap<>();

            // Compter le nombre de centres par gouvernorat
            for (CentreDon centre : centres) {
                String gouvernorat = centre.getGouvernorat();
                centresParGouvernorat.put(gouvernorat, centresParGouvernorat.getOrDefault(gouvernorat, 0) + 1);
            }

            // Créer une liste pour stocker les données du PieChart
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            // Parcourir les données des centres par gouvernorat
            for (Map.Entry<String, Integer> entry : centresParGouvernorat.entrySet()) {
                String gouvernorat = entry.getKey();
                int nombreCentres = entry.getValue();

                // Calculer le pourcentage
                double pourcentage = (double) nombreCentres / centres.size() * 100;

                // Créer un segment de PieChart avec le gouvernorat comme nom et le nombre de centres et le pourcentage comme valeur
                PieChart.Data data = new PieChart.Data(gouvernorat + " (" + nombreCentres + ", " + String.format("%.2f", pourcentage) + "%)", nombreCentres);

                // Ajouter le segment au PieChart
                pieChartData.add(data);
            }

            // Ajouter les données au PieChart
            pieChart.setData(pieChartData);
        } catch (SQLException ex) {
            Logger.getLogger(CentreController.class.getName()).log(Level.SEVERE, "Error loading statistics", ex);
            // Gérer l'erreur
        }

    }
    public void updatePieChart() {
        initializePieChart();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializePieChart();

    }
}
