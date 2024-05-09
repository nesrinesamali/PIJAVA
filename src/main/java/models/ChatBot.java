//package models;
//
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import okhttp3.*;
//
//import java.io.IOException;
//
//public class ChatBot {
//    public static String processInput(String input) {
//        // Assuming you have a method to generate GPT-3 responses
//        String gpt3Response = generateGPT3Response(input);
//
//        // Check if GPT-3 provided a meaningful response
//        if (gpt3Response != null && !gpt3Response.isEmpty()) {
//            return gpt3Response;
//        } else {
//            // Fallback to your predefined responses
//            switch (input.toLowerCase()) {
//                case "salut":
//                    return "bonjour, comment puis-je vous aider ?";
//                // ... other predefined responses ...
//
//                default:
//                    return "Malheureusement je n'ai pas de réponse à ce genre de message. Merci d'attendre nos mises à jour système!";
//            }
//        }
//    }
//
//    // Method to interact with GPT-3 and generate a response
//    private static String generateGPT3Response(String input) {
//        try {
////            OkHttpClient client = new OkHttpClient();
////
////            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
////            String model = "gpt-3.5-turbo"; // Initialize the model variable with the correct model name
////            String apiKey = "sk-proj-eeUvR3s1OrfJ54hH5k1zT3BlbkFJsgRWIJdBtkGCuzDWDrPY"; // Replace with your actual API key
////            String requestBody = "{\"model\":\"" + model + "\",\"prompt\":\"" + input + "\",\"max_tokens\":150}";
////
////            Request request = new Request.Builder()
////                    .url("https://api.openai.com/v1/completions")
////                    .post(RequestBody.create(requestBody, mediaType))
////                    .addHeader("Content-Type", "application/json")
////                    .addHeader("Authorization", "Bearer " + apiKey) // Use the retrieved API key
////                    .build();
////
////            Response response = client.newCall(request).execute();
////            String responseBody = response.body().string();
////
////            if (response.isSuccessful()) {
////                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
////                String generatedText = jsonResponse.getAsJsonArray("choices").get(0).getAsJsonObject().get("text").getAsString();
////                return generatedText;
////            } else {
////                // Handle unsuccessful response
////                return "Oops! An error occurred while fetching the answer. Response code: " + response.code();
////            }
//