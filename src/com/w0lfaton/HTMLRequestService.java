package com.w0lfaton;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class HTMLRequestService {

    public String postRequest(String url, HashMap<String, String> fields) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String body = toJson(fields);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String mapToJson(HashMap<String, String> fields) {
        return toJson(fields);
    }

    private static String toJson(HashMap<String, String> fields) {
        StringBuilder result = new StringBuilder();
        result.append("{");
        for (HashMap.Entry<String, String> entry : fields.entrySet()) {
            result.append("\n\t\"").append(entry.getKey()).append("\" : \"").append(entry.getValue()).append("\",");
        }
        result.deleteCharAt(result.length()-1);
        result.append("\n}");
        return result.toString();
    }
}
