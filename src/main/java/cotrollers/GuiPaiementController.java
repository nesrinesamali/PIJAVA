package cotrollers;


import com.stripe.exception.StripeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Rendezvous;
import services.RendezvousService;
import utils.PaymentAPI;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiPaiementController implements Initializable {

    @FXML
    private TextField anneeExp;

    @FXML
    private TextField carte;

    @FXML
    private TextField cvc;

    @FXML
    private TextField moisExp;

    @FXML
    private Button Pay;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public static Rendezvous rendezvous;
    @FXML
    private void Pay(ActionEvent event) throws StripeException, SQLException {
        RendezvousService scom = new RendezvousService();

        if (isValidInput()) {
            // Replace the following line with your payment logic
            // float f = (float) sb.get(4).getTotalCostTTC() * 32;
            // int k = floatToInt(f);
            // PaymentApi.pay(k);
            float f = 10000;
            int k = floatToInt(f);
            String url=PaymentAPI.pay(k);

            // Insert your logic for payment success
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Paiement");
            alert.setContentText("Paiement effectué avec succès");
            alert.showAndWait();
           Stage stage = new Stage ();

            final WebView webView = new WebView();
            final WebEngine webEngine = webView.getEngine();
            webView.getEngine().load(url);

            // create scene
            //   stage.getIcons().add(new Image("/Images/logo.png"));
            stage.setTitle("Reçu");
            Scene scene = new Scene(webView,1000,700, Color.web("#666970"));
            stage.setScene(scene);
            // show stage
            stage.show();
            // Assuming you have a client ID and product ID
          /*  GuiPaiementController.rendezvous.setEtat(true);
            RendezvousService sr=new RendezvousService();
            sr.update(GuiPaiementController.rendezvous); */

            // Creating a product order
          }
    }

    private boolean isValidInput() {
        if (!isValidVisaCardNo(carte.getText())) {
            showError("Numéro de carte invalide", "Veuillez entrer un numéro de carte Visa valide.");
            return false;
        }

        if (moisExp.getText().isEmpty() || !isNum(moisExp.getText()) || Integer.parseInt(moisExp.getText()) < 1 || Integer.parseInt(moisExp.getText()) > 12) {
            showError("Mois d'expiration invalide", "Veuillez entrer un mois d'expiration valide (entre 1 et 12).");
            return false;
        }

        if (anneeExp.getText().isEmpty() || !isNum(anneeExp.getText()) || Integer.parseInt(anneeExp.getText()) < LocalDate.now().getYear()) {
            showError("Année d'expiration invalide", "Veuillez entrer une année d'expiration valide.");
            return false;
        }

        if (cvc.getText().isEmpty() || !isNum(cvc.getText())) {
            showError("Code CVC invalide", "Veuillez entrer un code CVC numérique valide.");
            return false;
        }

        return true;
    }

    private boolean isValidVisaCardNo(String text) {
        String regex = "^4[0-9]{12}(?:[0-9]{3})?$";
        Pattern p = Pattern.compile(regex);
        CharSequence cs = text;
        Matcher m = p.matcher(cs);
        return m.matches();
    }

    public static boolean isNum(String str) {
        String expression = "\\d+";
        return str.matches(expression);
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static int floatToInt(float value) {
        return (int) value;
    }
}
