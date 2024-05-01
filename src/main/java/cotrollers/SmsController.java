package cotrollers;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
public class SmsController {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC791d09b8db164f42e2229230f42e9bd1";
    public static final String AUTH_TOKEN = "15f711e3ab3c06457d80c90aa393c1b7";

    public static void sms() {
        System.out.println("Sending SMS now");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21629063475"),
                new com.twilio.type.PhoneNumber("+13853992334"),


                "Vous avez reçu une réponse à votre rendez-vous").create();

        System.out.println(message.getSid());
    }
}
