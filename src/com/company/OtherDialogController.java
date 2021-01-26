package com.company;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class OtherDialogController {

    @FXML
    private TextField shortDescriptionField;

    @FXML
    private TextArea detailedDescriptionArea;

    @FXML
    private DatePicker deadlinePicker;

}
