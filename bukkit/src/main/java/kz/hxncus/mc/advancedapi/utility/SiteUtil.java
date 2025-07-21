package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@UtilityClass
public class SiteUtil {
    public String processAIRequest(String message, String apiUrl, String apiKey) {
        try {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    URL url = new URL(apiUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Authorization", "Bearer " + apiKey);
                    conn.setDoOutput(true);

                    String requestBody = "{"
                    + "\"model\":\"omni-moderation-latest\","
                    + "\"messages\":[{"
                    +   "\"role\":\"system\","
                    +   "\"content\":\"You are a helpful assistant\""
                    + "},{"
                    +   "\"role\":\"user\","
                    +   "\"content\":\"" + JsonUtil.escapeJson(message) + "\""
                    + "}]}";

                    try (OutputStream os = conn.getOutputStream()) {
                        os.write(requestBody.getBytes(StandardCharsets.UTF_8));
                    }

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        return SiteUtil.parseAiResponse(readAllBytes(conn.getInputStream()));
                    } else {
                        throw new RuntimeException("Failed to connect to API " + apiUrl + " status code " + conn.getResponseCode() + " " + readStream(conn.getErrorStream()));
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error processing request " + apiUrl, e);
                }
            }).get(8000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private String readStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "No error content";
        }
        
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }

    private String readAllBytes(InputStream stream) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }

    private String parseAiResponse(String jsonResponse) {
        // Простой парсинг JSON через поиск по подстроке
        int start = jsonResponse.indexOf("\"text\":\"") + 8;
        if (start < 8) return "Invalid response format";
        
        int end = jsonResponse.indexOf("\"", start);
        if (end == -1) return "Response parsing error";
        
        return jsonResponse.substring(start, end)
            .replace("\\\"", "\"")
            .replace("\\\\", "\\");
    }
}
