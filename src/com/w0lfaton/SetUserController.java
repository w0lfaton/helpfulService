package com.w0lfaton;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SetUserController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField companyAccount;

    public String getToken() {
        ScoroAPIService.getInstance().setLogin(username.getText(), password.getText(), companyAccount.getText());
        String result = ScoroAPIService.getInstance().request("userAuth", "modify");
        ScoroAPIService.getInstance().removeLoginFields();
        return result;
    }
}
