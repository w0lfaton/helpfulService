package com.w0lfaton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private String token;

    @FXML
    private ListView itemView;

    @FXML
    private ListView itemFieldList;

    @FXML
    private TableView requestFieldTable;

    @FXML
    private TableColumn requestFNameCol;

    @FXML
    private TableColumn requestFValueCol;

    @FXML
    private TextField itemFieldValue;

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

    @FXML
    private ContextMenu listContextMenu;

    public void initialize() {
        listContextMenu = new ContextMenu();
        MenuItem openEditDialog = new MenuItem("Open");
        openEditDialog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ObjectField field = (ObjectField) requestFieldTable.getSelectionModel().getSelectedItem();
                showFieldEditDialog(field);
            }
        });
        listContextMenu.getItems().addAll(openEditDialog);
        itemView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (t1 != null) {
                    ModuleItem item = (ModuleItem) itemView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.toString());
                    itemFieldList.setItems(item.getAllObservableRequestFields());
                    itemFieldList.setCellFactory(new Callback<ListView, ListCell>() {
                        @Override
                        public ListCell call(ListView listView) {
                            ListCell<ObjectField> cell = new ListCell<>() {
                                @Override
                                protected void updateItem(ObjectField item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty) {
                                        setText(null);
                                    } else {
                                        setText(item.getName());
                                    }
                                }
                            };

                            return cell;
                        }
                    });
                    itemFieldList.getSelectionModel().selectFirst();
                }
            }
        });
        itemFieldList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (t1 != null) {
                    ObjectField item = (ObjectField) itemFieldList.getSelectionModel().getSelectedItem();
                    itemFieldValue.setText(item.getValue());
                    //itemDetailsTextArea.setText(item.toString());
                }
            }
        });

        requestFNameCol.setCellValueFactory(new PropertyValueFactory<ObjectField, String>("name"));
        requestFValueCol.setCellValueFactory(new PropertyValueFactory<ObjectField, String>("value"));
        requestFValueCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn tableColumn) {
                TableCell<ObjectField, String> cell = new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        } else {
                            setText(item);
                        }
                    }
                };
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        }
                );
                return cell;
            }
        });
    }

    @FXML
    public void showFieldEditDialog(ObjectField field) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("requestFieldEdit.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            e.printStackTrace();
        }
        RequestFieldEditController requestFieldEditController = fxmlLoader.getController();
        requestFieldEditController.setFieldName(field.getName());
        requestFieldEditController.setFieldValueArea(field.getValue());

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            requestFieldEditController.editField();
            requestFieldTable.setItems(ScoroAPIService.getInstance().getAllObservableRequestFields());
            requestFieldTable.getSelectionModel().select(field);
        }
    }

    @FXML
    public void handleKeyPressed() {

    }

    @FXML
    public void handleMenuItemEvent() {

    }

    @FXML
    public void showApiResponseDialog(ActionEvent event) {
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
        ApiResponseController apiResponseController = fxmlLoader.getController();
        MenuItem selectedItem = (MenuItem) event.getSource();
        MenuItem previousMenu = selectedItem.getParentMenu();
        String module = apiResponseController.setModule(previousMenu.getText().trim());
        apiResponseController.setAction(selectedItem.getText());

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            requestFieldTable.setItems(ScoroAPIService.getInstance().getAllObservableRequestFields());
            requestFieldTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            String response = apiResponseController.getResponse();
            HashMap<String, Object> responseFields = mapResponse(response);
            status.setText(responseFields.get("status").toString());
            statusCode.setText(responseFields.get("statusCode").toString());
            messages.setText(responseFields.get("messages").toString());
            if (responseFields.get("messages").toString().startsWith("\"error\""))  {
                if (responseFields.get("messages").toString().equals("\"error\": [\n" +
                        "            \"Invalid user token\"\n" +
                        "        ]")) {

                }
                System.out.println(mapError(responseFields.get("messages").toString()));
                return;
            }
            LinkedList<HashMap<String, String>> itemList = mapData(responseFields.get("data").toString());
            ObservableList<ModuleItem> resultItems = FXCollections.observableArrayList();
            for (HashMap<String, String> itemMap : itemList) {
                ModuleItem item = new ModuleItem(itemMap);
                resultItems.add(item);
            }
            updateListView(resultItems, module.toLowerCase());
            itemDetailsTextArea.setText("");
        }
    }

    @FXML
    public void showSetUserDialog(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Set User");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("setUser.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            e.printStackTrace();
        }
        SetUserController setUserController = fxmlLoader.getController();

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            requestFieldTable.setItems(ScoroAPIService.getInstance().getAllObservableRequestFields());
            requestFieldTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            String response = setUserController.getToken();
            HashMap<String, Object> responseFields = mapResponse(response);
            status.setText(responseFields.get("status").toString());
            statusCode.setText(responseFields.get("statusCode").toString());
            messages.setText(responseFields.get("messages").toString());
            if (responseFields.get("messages").toString().startsWith("\"error\""))  {
                System.out.println(mapError(responseFields.get("messages").toString()));
                return;
            }
            LinkedList<HashMap<String, String>> itemList = mapData(responseFields.get("data").toString());
            this.token = itemList.get(0).get("token");
            ScoroAPIService.getInstance().setField("user_token", this.token);
            System.out.println("Token set.");
        }
    }

    @FXML
    public void searchResponse(ActionEvent event) {

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
                            //TODO - ahh, this reference to module might be the reason for the call.
                            // So the cell gets called once more before being overwritten,
                            // but by then the item has changed.. huh.
                            if (module.equals("projects")) {
                                //TODO - breaks if you make another request straight after
                                System.out.println("cell is being called");
                                /*Color projectColor = Color.valueOf(item.getFieldValue("color"));
                                setTextFill(projectColor);*/
                                setText(item.getFieldValue("project_name"));
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
                                setTextFill(Color.BLACK);
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
        if (!data.startsWith("\\{")) {
            StringBuilder sb = new StringBuilder();
            sb.append(data);
            sb.insert(0,"{");
            sb.append("}");
            data = sb.toString();
        }
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
        result.put(errorFields[0].substring(0, errorFields[0].length()-1),errorFields[1]);
        return result;
    }
}
