package services;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.json.JSONObject;

public class GPTAPI {

    private final HttpClient httpClient;
    private final String apiKey;

    public GPTAPI(String apiKey) {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.apiKey = apiKey;
    }

    public String queryGPT(String prompt, String model) {
        try {
            JSONObject data = new JSONObject();
            data.put("prompt", prompt);
            data.put("model", model);
            data.put("max_tokens", 500); // Adjust based on your needs

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + this.apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(data.toString()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Successfully received the response from the API
                return response.body();
            } else {
                // Handle non-200 responses
                return "Error: " + response.statusCode() + " - " + response.body();
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
