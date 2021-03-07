package com.w0lfaton;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class RequestFieldEditController {

    @FXML
    private Label fieldName;

    @FXML
    private TextArea fieldValueArea;

    public void editField() {
        ScoroAPIService.getInstance().setField(fieldName.getText(), fieldValueArea.getText());
    }

    public void setFieldName(String text) {
        if (!text.equals("")) {
            fieldName.setText(text);
        } else {
            System.out.println("ERROR: Cannot pass empty value to field name while editing");
        }
    }

    public void setFieldValueArea(String text) {
        fieldValueArea.setText(text);
    }
}
