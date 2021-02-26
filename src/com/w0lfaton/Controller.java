package com.w0lfaton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    @FXML
    private ListView itemView;

    @FXML
    private TextArea itemDetailsTextArea;

    @FXML
    private Label count;

    @FXML
    private Label status;

    @FXML
    private Label statusCode;

    @FXML
    private Label messages;

    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {
        itemView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (t1 != null) {
                    ModuleItem item = (ModuleItem) itemView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.toString());
                }
            }
        });
    }

    @FXML
    public void showActionDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("");
    }

    @FXML
    public void handleKeyPressed() {

    }

    @FXML
    public void handleMenuItemEvent() {

    }

    @FXML
    public void getResponse(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Response");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("apiResponse.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ApiResponseController apiResponseController = fxmlLoader.getController();
            MenuItem selectedItem = (MenuItem) event.getSource();
            String module = selectedItem.getText().trim().toLowerCase();
            String response = apiResponseController.getResponse(module);
            // Temporary
            //String response = "{\"status\":\"OK\",\"statusCode\":200,\"messages\":null,\"data\":[{\"project_id\":1,\"no\":\"1\",\"project_name\":\"Captive Inc campaign analysis\",\"description\":\"Consultancy and analysis project for Captive Inc.\",\"company_id\":18,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#b4e0fa\",\"status\":\"completed\",\"manager_id\":1,\"date\":\"2020-11-04\",\"deadline\":\"2021-01-29\",\"duration\":\"160:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"\",\"modified_date\":\"2020-12-11T16:35:13+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":2,\"no\":\"2\",\"project_name\":\"Concept LLC web banners\",\"description\":\"Banner design project for Concept LLC\",\"company_id\":13,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#b1bbcd\",\"status\":\"completed\",\"manager_id\":1,\"date\":\"2020-11-09\",\"deadline\":\"2020-11-27\",\"duration\":\"90:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"\",\"modified_date\":\"2020-12-11T16:33:30+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":3,\"no\":\"5\",\"project_name\":\"Provision Ltd TV commercial\",\"description\":\"TVC production project for Provision Ltd.\",\"company_id\":16,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#c2c2c2\",\"status\":\"cancelled\",\"manager_id\":1,\"date\":\"2020-10-20\",\"deadline\":\"2021-01-31\",\"duration\":\"150:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"quote\",\"modified_date\":\"2020-12-11T16:35:52+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":4,\"no\":\"3\",\"project_name\":\"Fineline Inc rebranding\",\"description\":\"Brand update and strategy project for Fineline Inc.\",\"company_id\":15,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#bbe5b3\",\"status\":\"cancelled\",\"manager_id\":1,\"date\":\"2020-09-07\",\"deadline\":\"2020-12-25\",\"duration\":\"250:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"quote\",\"modified_date\":\"2020-12-11T16:35:55+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":5,\"no\":\"6\",\"project_name\":\"Luminous Group app development\",\"description\":\"New mobile app design and develompent project for Luminous Group.\",\"company_id\":19,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#9fc5e8\",\"status\":\"cancelled\",\"manager_id\":1,\"date\":\"2020-08-10\",\"deadline\":\"2021-01-30\",\"duration\":\"250:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"quote\",\"modified_date\":\"2020-12-11T16:35:47+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":6,\"no\":\"4\",\"project_name\":\"Optimist Group web design and development\",\"description\":\"Website design and development project for Optimist Group\",\"company_id\":12,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#ffea8a\",\"status\":\"cancelled\",\"manager_id\":1,\"date\":\"2020-09-30\",\"deadline\":\"2021-01-03\",\"duration\":\"200:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"\",\"modified_date\":\"2020-12-11T16:35:54+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":12,\"no\":\"8\",\"project_name\":\"Website development for Coca-Cola\",\"description\":\"Website development project for Coca-Cola.\",\"company_id\":37,\"company_name\":\"\",\"is_personal\":0,\"is_private\":1,\"color\":\"#e43958\",\"status\":\"completed\",\"manager_id\":1,\"date\":\"2020-12-08\",\"deadline\":\"2021-04-21\",\"duration\":\"100:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"\",\"modified_date\":\"2020-12-08T10:11:16+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":13,\"no\":\"9\",\"project_name\":\"Website development for Coca-Cola 2.\",\"description\":\"\",\"company_id\":37,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#e43958\",\"status\":\"completed\",\"manager_id\":1,\"date\":\"2020-12-08\",\"deadline\":\"2021-04-15\",\"duration\":\"225:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"quote\",\"modified_date\":\"2020-12-10T10:56:51+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":14,\"no\":\"10\",\"project_name\":\"Stone Cold Brew Web Application\",\"description\":\"Web applications are a thing right now.\",\"company_id\":50,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#ffea8a\",\"status\":\"inprogress\",\"manager_id\":1,\"date\":\"2020-12-11\",\"deadline\":\"2021-05-31\",\"duration\":\"225:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"quote\",\"modified_date\":\"2020-12-11T16:41:59+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":15,\"no\":\"11\",\"project_name\":\"Development for Silver Shining Ltd.\",\"description\":\"Placeholder project for Silver Shining Ltd. \",\"company_id\":200,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#DEE5F1\",\"status\":\"pending\",\"manager_id\":1,\"date\":\"2020-12-16\",\"deadline\":\"2021-03-31\",\"duration\":\"250:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"quote\",\"modified_date\":\"2021-01-11T16:12:36+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":16,\"no\":\"12\",\"project_name\":\"Advertisement Video Production\",\"description\":\"Advertisement\",\"company_id\":202,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#9c6ade\",\"status\":\"pending\",\"manager_id\":1,\"date\":\"2020-12-17\",\"deadline\":\"2021-01-31\",\"duration\":\"90:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"quote\",\"modified_date\":\"2021-01-11T16:12:31+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":18,\"no\":\"13\",\"project_name\":\"Website development for Bonyless Ltd.\",\"description\":\"Webbidy web.\",\"company_id\":205,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#f49342\",\"status\":\"inprogress\",\"manager_id\":1,\"date\":\"2020-12-18\",\"deadline\":\"2021-04-30\",\"duration\":\"225:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"quote\",\"modified_date\":\"2020-12-18T13:35:58+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":19,\"no\":\"14\",\"project_name\":\"Market Research for Camille's Candy Inc.\",\"description\":\"\",\"company_id\":206,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#47c1bf\",\"status\":\"future\",\"manager_id\":1,\"date\":\"2020-12-21\",\"deadline\":\"2021-02-25\",\"duration\":\"50:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"quote\",\"modified_date\":\"2020-12-21T14:26:58+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":20,\"no\":\"15\",\"project_name\":\"Web application for Donald\",\"description\":\"\",\"company_id\":207,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#b3bcf5\",\"status\":\"inprogress\",\"manager_id\":11,\"date\":\"2021-03-01\",\"deadline\":\"2021-07-31\",\"duration\":\"225:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"quote\",\"modified_date\":\"2021-01-18T12:40:27+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":21,\"no\":\"16\",\"project_name\":\"Market Research for Johnny Jon Jawbreakers Inc.\",\"description\":\"\",\"company_id\":48,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#202e78\",\"status\":\"inprogress\",\"manager_id\":1,\"date\":\"2021-01-04\",\"deadline\":\"2021-04-30\",\"duration\":\"40:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"quote\",\"modified_date\":\"2021-01-04T16:16:49+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":23,\"no\":\"17\",\"project_name\":\"Branding and design for Michel Inc.\",\"description\":\"\",\"company_id\":208,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#b3bcf5\",\"status\":\"inprogress\",\"manager_id\":12,\"date\":\"2021-01-12\",\"deadline\":\"2021-04-01\",\"duration\":\"100:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"\",\"modified_date\":\"2021-01-12T09:35:22+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":24,\"no\":\"18\",\"project_name\":\"Kriimsilma Projekt\",\"description\":\"\",\"company_id\":209,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#084e8a\",\"status\":\"inprogress\",\"manager_id\":1,\"date\":\"2021-01-18\",\"deadline\":\"2021-06-30\",\"duration\":\"50:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"\",\"modified_date\":\"2021-01-19T15:50:38+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0},{\"project_id\":25,\"no\":\"19\",\"project_name\":\"Website development for Kriimsilm OÜ\",\"description\":\"\",\"company_id\":211,\"company_name\":\"\",\"is_personal\":0,\"is_private\":0,\"color\":\"#ffc58b\",\"status\":\"inprogress\",\"manager_id\":12,\"date\":\"2021-01-20\",\"deadline\":\"2021-08-31\",\"duration\":\"225:00:00\",\"account_id\":\"scorotesthenri\",\"budget_type\":\"quote\",\"modified_date\":\"2021-01-20T12:52:08+02:00\",\"deleted_date\":null,\"permissions\":null,\"stripDescription\":null,\"is_deleted\":0}]}";
            HashMap<String, Object> responseFields = mapResponse(response);
            status.setText(responseFields.get("status").toString());
            statusCode.setText(responseFields.get("statusCode").toString());
            messages.setText(responseFields.get("messages").toString());
            if (responseFields.get("messages").toString().startsWith("\"error\""))  {
                System.out.println(mapError(responseFields.get("messages").toString()));
                return;
            }
            LinkedList<HashMap<String, String>> itemList = mapData(responseFields.get("data").toString());
            ObservableList<ModuleItem> resultItems = FXCollections.observableArrayList();
            for (HashMap<String, String> itemMap : itemList) {
                ModuleItem item = new ModuleItem(itemMap);
                resultItems.add(item);
            }
            updateListView(resultItems, module);
            itemDetailsTextArea.setText("");
        }
    }

    private void updateListView(ObservableList<ModuleItem> moduleItems, String module) {
        itemView.setItems(moduleItems);
        itemView.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView listView) {
                ListCell<ModuleItem> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(ModuleItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            if (module.equals("projects")) {
                                setText(item.getFieldValue("project_name"));
                                setTextFill(Color.valueOf(item.getFieldValue("color")));
                            }
                            if (module.equals("contacts")) {
                                setText(item.getFieldValue("contact_id") +
                                        " - " + item.getFieldValue("name") +
                                        " " + item.getFieldValue("lastname"));
                            }
                            if (module.equals("invoices")) {
                                setText(item.getFieldValue("id") +
                                        "-" + item.getFieldValue("no") +
                                        " " + item.getFieldValue("status"));
                            }
                            //TODO - more complex for prepayments
                            if (module.equals("prepayments")) {
                                setText(item.getFieldValue("project_name"));
                            }
                            if (module.equals("tasks")) {
                                setText(item.getFieldValue("event_id") +
                                        " - " + item.getFieldValue("event_name") +
                                        " - " + item.getFieldValue("datetime_due"));
                            }
                            //TODO - escape characters come through as a hexadecimal code point and doesn't look nice
                            if (module.equals("products")) {
                                setText(item.getFieldValue("product_id") +
                                        " - " + item.getFieldValue("name") +
                                        " : " + item.getFieldValue("description"));
                            }
                            if (module.equals("orders")) {
                                setText(item.getFieldValue("id") +
                                        "-" + item.getFieldValue("no") +
                                        " " + item.getFieldValue("status"));
                            }
                            //TODO: company_name only available in view action and not list
                            if (module.equals("quotes")) {
                                setText(item.getFieldValue("id") +
                                        "-" + item.getFieldValue("no") +
                                        " " + item.getFieldValue("company_name") +
                                        " - " + item.getFieldValue("estimated_closing_date"));
                            }
                        }
                    }
                };

                return cell;
            }
        });
        itemView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        itemView.getSelectionModel().selectFirst();
    }

    private LinkedList<HashMap<String, String>> mapData(String data) {
        LinkedList<HashMap<String, String>> result = new LinkedList<>();
        Pattern pattern = Pattern.compile("((\\{)(.*)(}))*");
        Matcher matcher = pattern.matcher(data);
        int count = 0;
        while (matcher.find()) {
            if (matcher.group(3) == null) {
                break;
            }
            String[] objects = matcher.group(3).split("},\\{");
            for (String field : objects) {
                HashMap<String, String> itemDataFields = new HashMap<>();
                //Assumption: All field names start with a quotation mark - (")
                String[] fields = field.split(",(?=\")");
                for (String dataField : fields) {
                    String[] fieldNameVal = dataField.split(":", 2);
                    String name;
                    if (fieldNameVal[0].length() > 2) {
                        name = fieldNameVal[0].substring(1, fieldNameVal[0].length() - 1);
                    } else {
                        name = "";
                    }
                    String value;
                    System.out.println(fieldNameVal[0]);
                    if (fieldNameVal[1].startsWith("\"")) {
                        value = fieldNameVal[1].substring(1, fieldNameVal[1].length() - 1);
                    } else {
                        value = fieldNameVal[1];
                    }
                    itemDataFields.put(name, value);
                }
                result.add(itemDataFields);
                count++;
            }
        }
        this.count.setText(String.valueOf(count));
        return result;
    }

    private HashMap<String, Object> mapResponse(String response) {
        HashMap<String, Object> result = new HashMap<>();
        String[] statusFields = response.substring(1, response.length()-1).split(",", 4);
        for (String field : statusFields) {
            String[] fieldVal = field.split(":", 2);
            result.put(fieldVal[0].substring(1, fieldVal[0].length() -1 ),
                    (fieldVal[1].equals("null") ? "null" :
                            ((fieldVal[1].startsWith("[") || fieldVal[1].startsWith("\"") || fieldVal[1].startsWith("{")) ? fieldVal[1].substring(1, fieldVal[1].length() - 1) :
                                    Integer.parseInt(fieldVal[1]))));
        }
        return result;
    }

    private HashMap<String, Object> mapError(String response) {
        HashMap<String, Object> result = new HashMap<>();
        String[] errorFields = response.substring(1, response.length()-1).split(":");
        result.put(errorFields[0].substring(1, errorFields[0].length()-1),errorFields[1]);
        return result;
    }
}
