package com.w0lfaton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class ScoroAPIService {
    private static ScoroAPIService scoroAPIService = new ScoroAPIService();
    private static HTMLRequestService htmlRequestService = new HTMLRequestService();
    private static final String[] MODULE_ENDPOINTS = new String[]{"projects", "invoices", "prepayments", "tasks", "contacts", "timeEntries", "products", "orders", "quotes", "userAuth", "search"};
    private static final String[] ACTIONS = new String[]{"list", "view", "modify", "delete"};
    private HashMap<String, String> requestFields = new HashMap<>();

    private ScoroAPIService() {
        System.out.println("Creating API service instance");
        this.requestFields.put("lang", "eng");
        this.requestFields.put("request", "{}");
    }

    public static ScoroAPIService getInstance() {
        return scoroAPIService;
    }

    public ObservableList<ObjectField> getAllObservableRequestFields() {
        ObservableList<ObjectField> result = FXCollections.observableArrayList();
        requestFields.forEach((k,v) -> result.add(new ObjectField(k,v)));
        return result;
    }

    public boolean removeField(String field) {
        if (!field.equals("")) {
            this.requestFields.remove(field);
            return true;
        } else {
            System.out.println("Passed field parameter cannot be empty.");
            return false;
        }
    }

    public void removeLoginFields() {
        this.requestFields.remove("username");
        this.requestFields.remove("password");
        this.requestFields.remove("device_type");
        this.requestFields.remove("device_name");
        this.requestFields.remove("device_id");
    }

    public void setField(String field, String value) {
        this.requestFields.put(field, value);
    }

    public boolean setRequest(String value) {
        if (value.startsWith("{") && value.endsWith("}")) {
            this.requestFields.replace("request", value);
            return true;
        }
        return false;
    }

    public void setLogin(String username, String password, String companyAccount) {
        if (this.getLogin(username, password, companyAccount)) {
            System.out.println("Login set.");
        } else {
            System.out.println("Probably won't reach this far, aye.");
        }
    }

    //TODO : I check which action and module it is but I haven't handled when an exception is raised - such a smart cookie...
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
                return "ERROR 01-1-03.2: Exception thrown when trying to send a POST request : " + e.getMessage();
            }
        }
        return "Unrecognized command";
    }

    private void getDevice() {
        java.net.InetAddress localMachine = null;
        NetworkInterface netInterface;
        StringBuilder sb = new StringBuilder();
        try {
            localMachine = java.net.InetAddress.getLocalHost();
            netInterface = NetworkInterface.getByInetAddress(localMachine);
            byte[] mac = netInterface.getHardwareAddress();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format(
                        "%02X%s", mac[i],
                        (i < mac.length - 1) ? "-" : ""));
            }
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        String deviceType = System.getProperty("os.name");
        this.requestFields.put("device_type", deviceType);
        assert localMachine != null;
        this.requestFields.put("device_name", localMachine.getHostName());
        this.requestFields.put("device_id", sb.toString());
    }

    private boolean getLogin(String username, String password, String companyAccount) {
        try {
            this.requestFields.put("username", username);
            this.requestFields.put("password", password);
            this.requestFields.put("company_account_id", companyAccount);
            this.getDevice();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isInArray(String[] array, String input) {
        for (String entry : array) {
            if (entry.equals(input)) {
                return true;
            }
        }
        return false;
    }
}
