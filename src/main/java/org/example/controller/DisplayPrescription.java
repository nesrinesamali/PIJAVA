package org.example.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.example.models.Medrecord;
import org.example.models.prescription;
import org.example.service.MedrecordService;
import org.example.service.prescriptionService;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayPrescription implements Initializable {
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
    private ListView<prescription> List;
    prescriptionService PRC=new prescriptionService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int size = 125;

        List.setCellFactory(param -> new ListCell<>() {

            @Override
            protected void updateItem(prescription prescription, boolean empty) {
                super.updateItem(prescription, empty);

                if (empty || prescription == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    GridPane container = new GridPane();

                    TextFlow textFlow = new TextFlow();


                    String labelStyle = "-fx-fill: #9b8385;  -fx-font-size: 27  ;";
                    String nameStyle = "-fx-fill: #8b7080;  -fx-font-size: 40;";

                    String dataStyle = "-fx-fill: #9b8385; -fx-font-size: 20;";


                    Text nameData = new Text(prescription.getPatient() + "\n");
                    nameData.setStyle(nameStyle);

                    Text evaluationText = new Text("Diagnosis: ");
                    evaluationText.setStyle(labelStyle);

                    Text evaluationData = new Text(prescription.getDoctor() + "\n");
                    evaluationData.setStyle(dataStyle);

                    Text difficultyText = new Text("Medication: ");
                    difficultyText.setStyle(labelStyle);
                    Text difficultyData = new Text(prescription.getMedication() + "\n");
                    difficultyData.setStyle(dataStyle);

                    Text ins = new Text("Instruction: ");
                    ins.setStyle(labelStyle);
                    Text insd = new Text(prescription.getInstruction() + "\n");
                    insd.setStyle(dataStyle);

                    Text phr = new Text("Pharmacy: ");
                    phr.setStyle(labelStyle);
                    Text phrd = new Text(prescription.getPharmacy() + "\n");
                    phrd.setStyle(dataStyle);
                    Text date = new Text("Date: ");
                    date.setStyle(labelStyle);
                    Text dated = new Text(prescription.getCreatedat() + "\n");
                    dated.setStyle(dataStyle);

                    BufferedImage qrCodeImage = createQRImage(nameData.getText() + " \n" +
                                    evaluationText.getText() + " :\n" +
                                    evaluationData.getText() + " \n" +
                                    difficultyText.getText() + " :\n" +
                                    difficultyData.getText() + " \n"

                            , 125);
                    Image qrCodeImageFX = SwingFXUtils.toFXImage(qrCodeImage, null);
                    ImageView Qr = new ImageView(qrCodeImageFX);






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
                    ColumnConstraints col3 = new ColumnConstraints(450);

                    container.getColumnConstraints().addAll(col1, col2,col3);


                    textFlow.getChildren().addAll(nameData, evaluationText, evaluationData, difficultyText, difficultyData,ins,insd,phr,phrd,date,dated);
                    container.add(textFlow, 1, 0);
                    container.add(imageView, 0, 0);
                    container.add(Qr, 2, 0);

                    ColumnConstraints columnConstraints = new ColumnConstraints();
                    columnConstraints.setHgrow(Priority.ALWAYS);
                    container.getColumnConstraints().addAll(columnConstraints, columnConstraints, columnConstraints);

                    container.setHgap(30);

                    setGraphic(container);

                }
            }
        });

        List.getItems().addAll(PRC.fetch());
        String[] trier = { "Patient","Medication", "Pharmacy"};
        Tri.getItems().addAll(trier);

    }

    public void updateListView() {
        List.getItems().setAll(PRC.fetch());
    }
    public void handleCellClick(MouseEvent mouseEvent) {
    }

    public void ajoutpass(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddPrescription.fxml"));
            Parent root = loader.load();

            Scene currentScene = ((Node) event.getSource()).getScene();

            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Editer(ActionEvent ev) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditPrescription.fxml"));
            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == EditPrescription.class) {
                    EditPrescription editionController = new EditPrescription();
                    prescription selectedExercice = List.getSelectionModel().getSelectedItem();
                    int id = selectedExercice.getId();
                    editionController.setPassedId(id);
                    return editionController;
                } else {
                    return new EditPrescription();
                }
            });

            Parent root = loader.load();

            Scene currentScene = ((Node) ev.getSource()).getScene();

            currentScene.setRoot(root);

            EditPrescription editionExerciceController = loader.getController();
            editionExerciceController.setPrimaryStage(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSelectedMedRecord(ActionEvent event) {

        prescription SP = List.getSelectionModel().getSelectedItem();

        if (SP != null) {
            int id = SP.getId();
            PRC.delete(id);
            List.getItems().remove(SP);
        }
    }
    public void back(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayMedRecord.fxml"));
            Parent root = loader.load();

            Scene currentScene = ((Node) actionEvent.getSource()).getScene();

            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void tri(ActionEvent event) {
        if(Tri.getValue().equals("Patient")) {
            java.util.List<prescription> EventList =  List.getItems().stream().sorted(Comparator.comparing(prescription::getPatient)).toList();
            ObservableList<prescription> observableEventList = FXCollections.observableArrayList(EventList);
            List.setItems(observableEventList);
        }
        if(Tri.getValue().equals("Medication")) {
            java.util.List<prescription> EventList =  List.getItems().stream().sorted(Comparator.comparing(prescription::getMedication)).toList();
            ObservableList<prescription> observableEventList = FXCollections.observableArrayList(EventList);
            List.setItems(observableEventList);
        }
        if(Tri.getValue().equals("Pharmacy")) {
            java.util.List<prescription> EventList =  List.getItems().stream().sorted(Comparator.comparing(prescription::getPharmacy)).toList();
            ObservableList<prescription> observableEventList = FXCollections.observableArrayList(EventList);
            List.setItems(observableEventList);
        }
    }

    public void ok(ActionEvent event) {
        java.util.List<prescription> EventList =  List.getItems().stream().filter(Event -> Event.getPatient().contains(recherche.getText())).toList();
        ObservableList<prescription> observableEventList = FXCollections.observableArrayList(EventList);
        List.setItems(observableEventList);
    }

    public void refrech(ActionEvent event) {
        List.getItems().setAll(PRC.fetch());

    }
    public BufferedImage createQRImage(String qrCodeText, int size)
    {
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = null;
        try {
            byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        return image;
    }

}
