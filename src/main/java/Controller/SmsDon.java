package Controller;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
public class SmsDon {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "ACd710464acb5ab78d48f8d64459966a80";
    public static final String AUTH_TOKEN = "f163cfcb177d708ae8080cffec2067cc";

    public static void sms() {
        System.out.println("Sms envoyé avec succées");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21651551638"),
                new com.twilio.type.PhoneNumber("+12177691446"),


                "Vous avez reçu une réponse à votre rendez-vous").create();

        System.out.println(message.getSid());
    }
}