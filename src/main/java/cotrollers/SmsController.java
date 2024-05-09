package cotrollers;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
public class SmsController {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC29426580069fc89e74927d2d6a861577";
    public static final String AUTH_TOKEN = "201de44f04a1555f5e2035c249603c64";

    public static void sms() {
        System.out.println("Sending SMS now");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21629063475"),
                new com.twilio.type.PhoneNumber("+12568889797"),


                "Vous avez reçu une réponse à votre rendez-vous").create();

        System.out.println(message.getSid());
    }
}
