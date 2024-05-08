package utils;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rassa
 */
public class PaymentAPI {

    public static String pay(int f) throws StripeException {
        Stripe.apiKey ="sk_test_51MfmZCAyRwA2k3iVYHgxsElKg90nJP1n7s5xCZHH1pE2ZMNnllelyJGpfoXt5RaKKAFjdizlCONe6Qgt0nmZpXBy0068HpRI3i";
        Map<String, Object> params = new HashMap<>();
        params.put("amount", f);
        params.put("currency", "usd");
        params.put("customer", "cus_NQdtcHsC8SKEPl");

        // Get the client's Payment Page URL

        Charge charge = Charge.create(params);
        System.out.println(charge);
        return charge.getReceiptUrl();



    }
}