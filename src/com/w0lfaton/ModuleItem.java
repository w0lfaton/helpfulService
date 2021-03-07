package com.w0lfaton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;

public class ModuleItem {
    private HashMap<String, String> fields;

    public ModuleItem(HashMap<String, String> fields) {
        this.fields = fields;
    }

    public void setField(String fieldName, String fieldValue) {
        if (this.fields.containsKey(fieldName)) {
            this.fields.replace(fieldName, fieldValue);
        }
    }

    public String getFieldValue(String fieldName) {
        return this.fields.getOrDefault(fieldName, "ERROR");
    }

    public void removeField(String fieldName) {
        this.fields.remove(fieldName);
    }

    public void addField(String fieldName, String fieldValue) {
        if (!this.fields.containsKey(fieldName)) {
            this.fields.put(fieldName, fieldValue);
        }
    }

    private void addField(String fieldName) {
        if (!this.fields.containsKey(fieldName)) {
            this.fields.put(fieldName, "");
        }
    }

    public ObservableList<ObjectField> getAllObservableRequestFields() {
        ObservableList<ObjectField> result = FXCollections.observableArrayList();
        fields.forEach((k,v) -> result.add(new ObjectField(k,v)));
        return result;
    }


    @Override
    public String toString() {
        return fields.toString();
    }
}
