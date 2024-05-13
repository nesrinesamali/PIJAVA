package controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSController {

    private static final String ACCOUNT_SID = "AC6bd1d3db4174430e016faddbb3e3808d";
    private static final String AUTH_TOKEN = "47f8efeff1c3a84c2ccb204b10f324a7"; // Replace this with your actual Auth Token
    private static final String TWILIO_PHONE_NUMBER = "+12564084113"; // Replace with your Twilio phone number

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    // Method to send SMS
    public static void sendSMS(String to, String message) {
        // Send SMS using Twilio API
        try {
            Message.creator(
                            new PhoneNumber(to),
                            new PhoneNumber(TWILIO_PHONE_NUMBER),
                            message)
                    .create();
        } catch (Exception ex) {

            System.out.println("err!" + ex.getMessage());
        }
        System.out.println("SMS sent successfully!");
    }
}





