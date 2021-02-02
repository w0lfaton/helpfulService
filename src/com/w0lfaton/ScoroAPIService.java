package com.w0lfaton;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class ScoroAPIService {
    private static HTMLRequestService htmlRequestService = new HTMLRequestService();
    private static final String[] MODULE_ENDPOINTS = new String[]{"projects", "invoices", "prepayments", "tasks", "contacts", "timeEntries", "products", "orders", "quotes"};
    private static final String[] ACTIONS = new String[]{"list", "view", "modify", "delete"};
    private HashMap<String, String> requestFields = new HashMap<>();

    public ScoroAPIService() {
        if (this.getTempAuth()) {
            this.requestFields.put("lang", "eng");
            this.requestFields.put("per_page", "100");
            this.requestFields.put("page", "");
            this.requestFields.put("request", "{}");
        } else {
            System.out.println("You are not authorised! Or possibly you don't have the auth file. Or maybe there's something wrong with reading it?");
            //TODO: Reminds me that I need to map exceptions, errors and unsuccessful API requests
        }
    }

    public boolean keyToToken() {
        return true;
    }

    public boolean setRequest(String value) {
        if (value.startsWith("{") && value.endsWith("}")) {
            this.requestFields.replace("request", value);
            return true;
        }
        return false;
    }

    public String request(String endpoint, String action) {
        if (this.isInArray(MODULE_ENDPOINTS, endpoint) && this.isInArray(ACTIONS, action)) {
            try {
                return htmlRequestService.postRequest("https://scorotesthenri.scoro.com/api/v2/" + endpoint + "/" + action, requestFields);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return "ERROR 01-1-03.1: Exception thrown when trying to send a POST request : " + e.getMessage();
            }
        }
        return "Unrecognized command";
    }

    public String request(String endpoint, String action, int id) {
        if (this.isInArray(MODULE_ENDPOINTS, endpoint) && this.isInArray(ACTIONS, action)) {
            try {
                return htmlRequestService.postRequest("https://scorotesthenri.scoro.com/api/v2/" + endpoint + "/" + action + "/" + id, requestFields);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return "ERROR 01-1-03.1: Exception thrown when trying to send a POST request : " + e.getMessage();
            }
        }
        return "Unrecognized command";
    }

    private boolean isInArray(String[] array, String input) {
        for (String entry : array) {
            if (entry.equals(input)) {
                return true;
            }
        }
        return false;
    }

    private boolean getTempAuth() {
        try {
            Path path = Paths.get("tempAuth.json");
            String json = Files.readString(path);
            String[] lines = json.substring(3, json.length()-1).split("\n"); // Removing {, \n and \t from start and } from end
            for (String line : lines) {
                String[] splitLine = line.split(" : ");
                if (splitLine[1].trim().endsWith(",")) {
                    this.requestFields.put(splitLine[0].substring(3, splitLine[0].length() - 1), splitLine[1].substring(1, splitLine[1].length() - 3));
                } else {
                    this.requestFields.put(splitLine[0].substring(3, splitLine[0].length() - 1), splitLine[1].substring(1, splitLine[1].length() - 2));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
