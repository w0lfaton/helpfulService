package com.w0lfaton;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class SetUserController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField companyAccount;

    @FXML
    private CheckBox loginCheckbox;

    @FXML
    public void grayOutFields() {
        if (this.username.isDisabled()) {
            this.username.setDisable(false);
            this.password.setDisable(false);
            this.companyAccount.setDisable(false);
        } else {
            this.username.setDisable(true);
            this.password.setDisable(true);
            this.companyAccount.setDisable(true);
        }
    }


    public String getToken() {
        if (loginCheckbox.isSelected()) {
            HashMap<String, String> loginFields = getLoginDetails();
            assert loginFields != null;
            ScoroAPIService.getInstance().setLogin(loginFields.get("username"), loginFields.get("password"), loginFields.get("company_account_id"));
        } else {
            ScoroAPIService.getInstance().setLogin(username.getText(), password.getText(), companyAccount.getText());
        }
        String result = ScoroAPIService.getInstance().request("userAuth", "modify");
        ScoroAPIService.getInstance().removeLoginFields();
        return result;
    }

    private HashMap<String, String> getLoginDetails() {
        try {
            HashMap<String, String> result = new HashMap<>();
            Path path = Paths.get("loginDetails.json");
            String json = Files.readString(path);
            System.out.println(json);
            String[] lines = json.substring(3, json.length()-1).split("\n"); // Removing {, \n and \t from start and } from end
            for (String line : lines) {
                String[] splitLine = line.split(" : ");
                if (splitLine[1].trim().endsWith(",")) {
                    result.put(splitLine[0].substring(3, splitLine[0].length() - 1), splitLine[1].substring(1, splitLine[1].length() - 3));
                } else {
                    result.put(splitLine[0].substring(3, splitLine[0].length() - 1), splitLine[1].substring(1, splitLine[1].length() - 2));
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
