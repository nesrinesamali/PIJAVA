package org.example.controller;


import okhttp3.*;
import org.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BadWordsChecker {
    private static final String API_KEY = "zWXgsNnCMc28ZzxSAkwfOcgtGUc7Mkwk";
    private static final String API_ENDPOINT = "https://api.apilayer.com/bad_words?censor_character=*";

    public static boolean checkForBadWords(String textToCheck) throws IOException {
        // Custom list
        List<String> customBadWords = new ArrayList<>();
        customBadWords.add("Balti");
        customBadWords.add("Khraief");

        String text = textToCheck.toLowerCase();

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, text);

        Request request = new Request.Builder()
                .url(API_ENDPOINT)
                .addHeader("apikey", API_KEY)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Error response: " + response.code());
                return false;
            }

            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);

            int badWordsTotal = jsonResponse.getInt("bad_words_total");
            JSONArray badWordsList = jsonResponse.getJSONArray("bad_words_list");

            boolean hasBadWords = badWordsTotal > 0;


            if (hasBadWords) {
                System.out.println("Bad words detected from API:");
                for (int i = 0; i < badWordsList.length(); i++) {
                    JSONObject badWord = badWordsList.getJSONObject(i);
                    String originalWord = badWord.getString("original");
                    System.out.println("- " + originalWord);
                }
            }


            for (String customBadWord : customBadWords) {
                if (text.contains(customBadWord)) {
                    System.out.println("Custom bad word detected: " + customBadWord);
                    return true;
                }
            }


            return hasBadWords;
        }
    }
}