package cotrollers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import models.Rendezvous;
import services.RendezvousService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AfficheReponse implements Initializable {

    @FXML
    private TableColumn<Rendezvous, String> Etat;

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

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private Pagination pagination;

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

            // Suppose que le type de cellData.getValue().getEtat() est un booléen
            Etat.setCellValueFactory(cellData -> {
                boolean etatValue = cellData.getValue().getEtat();
                String etatString = etatValue ? "Traitée" : "Non traitée";
                return new SimpleStringProperty(etatString);
            });

            TableColumn<Rendezvous, Void> actionsColumn = new TableColumn<>("Actions");
            actionsColumn.setCellFactory(param -> new TableCell<>() {
                private final Button acceptButton = createButton("Traiter", "#4CAF50");
                //private final Button refuseButton = createButton("Refuser", "#f44336");

                {
                    // Handle button actions
                    acceptButton.setOnAction(event -> {
                        Rendezvous rendezvous = getTableView().getItems().get(getIndex());
                        //TODO go to ajout reponse
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutReponse.fxml"));
                        Parent root;
                        try {
                            root = loader.load();
                            AjoutReponseController controller = loader.getController();
                            controller.initData(rendezvous); // Initialize selected reservation
                            controller.setAfficheReponseController(AfficheReponse.this); // Set reference to AfficheReponseController
                            controller.initData(rendezvous); // Initialize selected reservation
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(AfficheReponse.class.getName()).log(Level.SEVERE, null, ex);
                        }

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
                        setGraphic(new HBox(acceptButton));

                        Rendezvous rendezvous = getTableView().getItems().get(getIndex());

                        acceptButton.setVisible(true);
                        //refuseButton.setVisible(true);

                    }
                }

            });

            tv.getColumns().add(actionsColumn);
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filterData(newValue);
            });

            // Gestion de l'événement de clic sur le bouton de recherche
            //searchButton.setOnAction(event -> search());

            // Configurer la pagination
            pagination.setPageFactory(this::createPage);

        } catch (SQLException e) {
            showErrorAlert("Error when retrieving data from database");
        }
    }

    @FXML
    private void handleGeneratePDFButtonAction(ActionEvent event) {
        generatePDF();
    }

//    private void search() {
//        String searchText = searchField.getText().toLowerCase();
//
//        // Filtrer la liste des rendez-vous en fonction du texte de recherche
//        ObservableList<Rendezvous> filteredList = observableList.filtered(rendezvous ->
//                rendezvous.getNompatient().toLowerCase().contains(searchText) ||
//                        rendezvous.getNommedecin().toLowerCase().contains(searchText) ||
//                        rendezvous.getDate().toString().contains(searchText) ||
//                        rendezvous.getHeure().toLowerCase().contains(searchText) ||
//                        (rendezvous.getEtat() ? "Traitée" : "Non traitée").toLowerCase().contains(searchText)
//        );
//
//        // Mettre à jour le TableView avec la liste filtrée
//        tv.setItems(filteredList);
//    }
private void filterData(String keyword) {
    if (keyword == null || keyword.isEmpty()) {
        tv.setItems(observableList); // Afficher toutes les données si le champ de recherche est vide
    } else {
        ObservableList<Rendezvous> filteredList = FXCollections.observableArrayList();
        for (Rendezvous rendezvous : observableList) {
            // Filtrer les rendez-vous en fonction du nom du patient, du nom du médecin, de la date, de l'heure ou de l'état
            if (rendezvous.getNompatient().toLowerCase().contains(keyword.toLowerCase()) ||
                    rendezvous.getNommedecin().toLowerCase().contains(keyword.toLowerCase()) ||
                    rendezvous.getDate().toString().contains(keyword) ||
                    rendezvous.getHeure().toLowerCase().contains(keyword.toLowerCase()) ||
                    (rendezvous.getEtat() ? "Traitée" : "Non traitée").toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(rendezvous);
            }
        }
        tv.setItems(filteredList); // Afficher les données filtrées dans la TableView
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

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Créer une page pour la pagination
    private Node createPage(int pageIndex) {
        int itemsPerPage = 1;
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, observableList.size());
        tv.setItems(FXCollections.observableList(observableList.subList(fromIndex, toIndex)));
        return new BorderPane(tv);
    }

    // Méthode pour générer un document PDF
    private void generatePDF() {
        try {
            // Créer un nouveau document PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Ajouter du contenu au document
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);

                // Ajouter le titre de la liste des rendez-vous
                contentStream.showText("Liste des rendez-vous :");
                contentStream.newLine();
                contentStream.newLine();

                // Itérer sur chaque rendez-vous et ajouter ses détails au contenu
                for (Rendezvous rendezvous : tv.getItems()) {
                    contentStream.showText("Nom patient: " + rendezvous.getNompatient());
                    contentStream.newLine();
                    contentStream.showText("Nom médecin: " + rendezvous.getNommedecin());
                    contentStream.newLine();
                    contentStream.showText("Date: " + rendezvous.getDate());
                    contentStream.newLine();
                    contentStream.showText("Heure: " + rendezvous.getHeure());
                    contentStream.newLine();
                    contentStream.showText("État: " + (rendezvous.getEtat() ? "Traitée" : "Non traitée"));
                    contentStream.newLine();
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            // Enregistrer le document sur le disque
            File outputFile = new File("output.pdf");
            document.save(outputFile);
            document.close();

            // Ouvrir le fichier PDF avec l'application par défaut
            try {
                Desktop.getDesktop().open(outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Afficher un message de réussite
            showAlert(Alert.AlertType.INFORMATION, "PDF généré avec succès", "Le document PDF a été généré avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
            // Afficher une alerte en cas d'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur lors de la génération du PDF", "Une erreur s'est produite lors de la génération du document PDF.");
        }
    }

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void stat(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Stat.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle fenêtre pour afficher le formulaire d'ajout
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    /////handle/////
    @FXML
    private void goToDonView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherDon.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle si nécessaire
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void goToCenterView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCentre.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle si nécessaire
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
