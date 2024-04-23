package cotrollers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Rendezvous;
import services.RendezvousService;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AfficherRendezvous implements Initializable {

        @FXML
        private TableColumn<Rendezvous, ?> action;

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TableColumn<Rendezvous, Date> date;

        @FXML
        private TableColumn<Rendezvous, String> heure;

        @FXML
        private TableColumn<Rendezvous, String> nommedecin;

        @FXML
        private TableColumn<Rendezvous, String> nompatient;

        @FXML
        private TableView<Rendezvous> tv;

        RendezvousService rs = new RendezvousService();
        private ObservableList<Rendezvous> observableList = FXCollections.observableArrayList();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                try {
                        List<Rendezvous> rendezvousList = rs.read();
                        observableList = FXCollections.observableList(rendezvousList);
                        tv.setItems(observableList);
                        nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
                        nommedecin.setCellValueFactory(new PropertyValueFactory<>("nommedecin"));
                        date.setCellValueFactory(new PropertyValueFactory<>("date"));
                        heure.setCellValueFactory(new PropertyValueFactory<>("heure"));

                        // Add action buttons column
                        TableColumn<Rendezvous, Void> actionsColumn = new TableColumn<>("Actions");
                        actionsColumn.setCellFactory(param -> new TableCell<>() {
                                private final FontAwesomeIconView deleteButton = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                                private final FontAwesomeIconView updateButton = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                                {
                                        deleteButton.setOnMouseClicked(event -> {
                                                Rendezvous rendezvous = getTableView().getItems().get(getIndex());
                                                try {
                                                        rs.delete(rendezvous.getId());
                                                        getTableView().getItems().remove(rendezvous);
                                                        RefreshTable(); // Rafraîchir le TableView après la suppression
                                                } catch (SQLException e) {
                                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                                        alert.setTitle("Error");
                                                        alert.setContentText("Error when deleting item");
                                                        alert.showAndWait();
                                                }
                                        });

                                        updateButton.setOnMouseClicked(event -> {
                                                Rendezvous selectedRendezvous = getTableView().getItems().get(getIndex());
                                                if (selectedRendezvous != null) {
                                                        try {
                                                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierRendezvous.fxml"));
                                                                Parent parent = loader.load();
                                                                ModifierRendezvous modifierRendezvous = loader.getController();
                                                                modifierRendezvous.setRendezvousData(selectedRendezvous);
                                                                Scene scene = new Scene(parent);
                                                                Stage stage = new Stage();
                                                                stage.setScene(scene);
                                                                stage.initStyle(StageStyle.UTILITY);
                                                                stage.showAndWait(); // Attendre que la fenêtre soit fermée avant de rafraîchir
                                                                RefreshTable(); // Rafraîchir le TableView après la modification
                                                        } catch (IOException ex) {
                                                                Logger.getLogger(AfficherRendezvous.class.getName()).log(Level.SEVERE, null, ex);
                                                        }
                                                } else {
                                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                                        alert.setTitle("No Selection");
                                                        alert.setHeaderText(null);
                                                        alert.setContentText("Please select a rendezvous to update.");
                                                        alert.showAndWait();
                                                }
                                        });
                                }

                                @Override
                                protected void updateItem(Void item, boolean empty) {
                                        super.updateItem(item, empty);
                                        if (empty) {
                                                setGraphic(null);
                                        } else {
                                                HBox buttons = new HBox(deleteButton, updateButton);
                                                setGraphic(buttons);

                                                // Styling
                                                updateButton.setStyle("-fx-cursor: hand ; -glyph-size:28px; -fx-fill:#ff1744;");
                                                deleteButton.setStyle("-fx-cursor: hand ; -glyph-size:28px; -fx-fill:#00E676;");
                                        }
                                }
                        });

                        // Add the actions column to the TableView
                        action.getColumns().add(actionsColumn);

                } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Error when retrieving data from database");
                        alert.showAndWait();
                }
        }

        void RefreshTable() {
                try {
                        // Récupérer à nouveau les données de la base de données
                        List<Rendezvous> rendezvousList = rs.read();
                        // Mettre à jour la liste observable avec les nouvelles données
                        observableList.clear();
                        observableList.addAll(rendezvousList);
                        // Mettre à jour le TableView
                        tv.setItems(observableList);
                } catch (SQLException e) {
                        // Gérer les exceptions
                        e.printStackTrace();
                }
        }
}
