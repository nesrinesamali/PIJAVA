package cotrollers;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.Rendezvous;
import services.RendezvousService;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficheReponse implements Initializable {
    private Button acceptButton;
    private Button refuseButton;

    @FXML
    private TableColumn<Rendezvous, ?> Etat;

    @FXML
    private TableColumn<Rendezvous, Void> actionsColumn;

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
                private final Button acceptButton = createButton("Accepter", "#4CAF50");
                private final Button refuseButton = createButton("Refuser", "#f44336");

                {
                    // Handle button actions
                    acceptButton.setOnAction(event -> {
                        Rendezvous rendezvous = getTableView().getItems().get(getIndex());
                        // Do something when accepting the appointment
                    });

                    refuseButton.setOnAction(event -> {
                        Rendezvous rendezvous = getTableView().getItems().get(getIndex());
                        // Do something when refusing the appointment
                    });
                }

                private Button createButton(String text, String backgroundColor) {
                    Button button = new Button(text);
                    button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-text-fill: white;");
                    button.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    return button;
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty ) {
                        setGraphic(null);
                    } else {
                        setGraphic(new HBox(acceptButton, refuseButton));

                        Rendezvous rendezvous = getTableView().getItems().get(getIndex());

                            acceptButton.setVisible(true);
                            refuseButton.setVisible(true);

                        }
                    }

            });

            tv.getColumns().add(actionsColumn);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error when retrieving data from database");
            alert.showAndWait();
        }
    }

    void RefreshTable() {
        try {
            List<Rendezvous> rendezvousList = rs.read();
            observableList.clear();
            observableList.addAll(rendezvousList);
            tv.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
