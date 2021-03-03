package com.w0lfaton;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ApiResponseController {
    private static ScoroAPIService scoroAPIService = new ScoroAPIService();

    @FXML
    private Label action;

    @FXML
    private Label module;

    @FXML
    private TextField objectId;

    public String getResponse() {
        if (action.getText().trim().toLowerCase().equals("view") || action.getText().trim().toLowerCase().equals("modify")) {
            return scoroAPIService.request(module.getText().toLowerCase(), action.getText().trim().toLowerCase(), Integer.parseInt(objectId.getText().trim()));
        } else {
            return scoroAPIService.request(module.getText().toLowerCase(), action.getText().trim().toLowerCase());
        }
    }

    public void setAction(String action) {
        this.action.setText(action);
    }

    public String setModule(String module) {
        this.module.setText(module);
        return module;
    }
}
