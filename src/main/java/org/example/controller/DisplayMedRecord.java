package org.example.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.example.models.Medrecord;
import org.example.service.MedrecordService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class DisplayMedRecord implements Initializable {
    private Stage primaryStage;

    @FXML
    private MFXButton Ajoutp;

    @FXML
    private MFXTextField recherche;

    @FXML
    private MFXButton Delete;

    @FXML
    private MFXButton Editer;
    @FXML
    private MFXComboBox<String> Tri;


    @FXML
    private ListView<Medrecord> List;
    MedrecordService MRC=new MedrecordService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int size = 125;

        List.setCellFactory(param -> new ListCell<>() {

            @Override
            protected void updateItem(Medrecord Medrecord, boolean empty) {
                super.updateItem(Medrecord, empty);

                if (empty || Medrecord == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    GridPane container = new GridPane();

                    TextFlow textFlow = new TextFlow();


                    String labelStyle = "-fx-fill: #9b8385;  -fx-font-size: 27  ;";
                    String nameStyle = "-fx-fill: #8b7080;  -fx-font-size: 40;";

                    String dataStyle = "-fx-fill: #9b8385; -fx-font-size: 20;";


                    Text nameData = new Text(Medrecord.getPatient() + "\n");
                    nameData.setStyle(nameStyle);

                    Text evaluationText = new Text("Diagnosis: ");
                    evaluationText.setStyle(labelStyle);

                    Text evaluationData = new Text(Medrecord.getDiagnosis() + "\n");
                    evaluationData.setStyle(dataStyle);

                    Text difficultyText = new Text("Test: ");
                    difficultyText.setStyle(labelStyle);
                    Text difficultyData = new Text(Medrecord.getTest() + "\n");
                    difficultyData.setStyle(dataStyle);




                    String demonstrationPath ="C:\\Users\\essay\\Desktop\\Sami\\src\\main\\resources\\img.png";
                    Image demonstrationImage = new Image(new File(demonstrationPath).toURI().toString());
                    ImageView imageView = new ImageView(demonstrationImage);
                    nameData.setWrappingWidth(200);
                    evaluationData.setWrappingWidth(200);
                    evaluationText.setWrappingWidth(200);
                    difficultyText.setWrappingWidth(200);
                    difficultyData.setWrappingWidth(200);
                    ColumnConstraints col1 = new ColumnConstraints(200);
                    ColumnConstraints col2 = new ColumnConstraints(450);
                    container.getColumnConstraints().addAll(col1, col2);


                    textFlow.getChildren().addAll(nameData, evaluationText, evaluationData, difficultyText, difficultyData);
                    container.add(textFlow, 1, 0);  // Add textFlow to column 0
                    container.add(imageView, 0, 0); // Add imageView to column 1
                    ColumnConstraints columnConstraints = new ColumnConstraints();
                    columnConstraints.setHgrow(Priority.ALWAYS);
                    container.getColumnConstraints().addAll(columnConstraints, columnConstraints, columnConstraints);

                    container.setHgap(30);

                    setGraphic(container);

                }
            }
        });

        List.getItems().addAll(MRC.fetch());
        String[] trier = { "Patient","Diagnosis", "Test"};
        Tri.getItems().addAll(trier);

    }



    public void deleteSelectedMedRecord(ActionEvent event) {

        Medrecord SP = List.getSelectionModel().getSelectedItem();

        if (SP != null) {
            int id = SP.getId();
            MRC.delete(id);
            List.getItems().remove(SP);
        }}
    public void Editer(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditMedRecord.fxml"));
            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == EditMedRecord.class) {
                    EditMedRecord editionController = new EditMedRecord();
                    Medrecord selectedExercice = List.getSelectionModel().getSelectedItem();
                    int id = selectedExercice.getId();
                    editionController.setPassedId(id);
                    return editionController;
                } else {
                    return new EditMedRecord();
                }
            });

            Parent root = loader.load();

            Scene currentScene = ((Node) actionEvent.getSource()).getScene();

            currentScene.setRoot(root);

            EditMedRecord editionExerciceController = loader.getController();
            editionExerciceController.setPrimaryStage(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateListView() {
        List.getItems().setAll(MRC.fetch());
    }
    public void handleCellClick(MouseEvent mouseEvent) {
    }

    public void ajoutpass(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddMedRecord.fxml"));
            Parent root = loader.load();

            Scene currentScene = ((Node) event.getSource()).getScene();

            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void back(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayPrescription.fxml"));
            Parent root = loader.load();

            Scene currentScene = ((Node) actionEvent.getSource()).getScene();

            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void tri(ActionEvent event) {
        if(Tri.getValue().equals("Patient")) {
            java.util.List<Medrecord> EventList =  List.getItems().stream().sorted(Comparator.comparing(Medrecord::getPatient)).toList();
            ObservableList<Medrecord> observableEventList = FXCollections.observableArrayList(EventList);
            List.setItems(observableEventList);
        }
        if(Tri.getValue().equals("Diagnosis")) {
            java.util.List<Medrecord> EventList =  List.getItems().stream().sorted(Comparator.comparing(Medrecord::getDiagnosis)).toList();
            ObservableList<Medrecord> observableEventList = FXCollections.observableArrayList(EventList);
            List.setItems(observableEventList);
        }
        if(Tri.getValue().equals("Test")) {
            java.util.List<Medrecord> EventList =  List.getItems().stream().sorted(Comparator.comparing(Medrecord::getTest)).toList();
            ObservableList<Medrecord> observableEventList = FXCollections.observableArrayList(EventList);
            List.setItems(observableEventList);
        }
    }

    public void ok(ActionEvent event) {
        java.util.List<Medrecord> EventList =  List.getItems().stream().filter(Event -> Event.getPatient().contains(recherche.getText())).toList();
        ObservableList<Medrecord> observableEventList = FXCollections.observableArrayList(EventList);
        List.setItems(observableEventList);
    }

    public void refrech(ActionEvent event) {
        List.getItems().setAll(MRC.fetch());

    }

}
