package com.company;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ApiResponseController {
    private static ScoroAPIService scoroAPIService = new ScoroAPIService();

    @FXML
    private TextField action;

    @FXML
    private TextField objectId;

    public String getResponse(String endpoint) {
        if (action.getText().trim().equals("view") || action.getText().trim().equals("modify")) {
            return scoroAPIService.request(endpoint, action.getText().trim(), Integer.parseInt(objectId.getText().trim()));
        } else {
            return scoroAPIService.request(endpoint, action.getText().trim());
        }
    }
}
