package com.w0lfaton;

import java.util.HashMap;
import java.util.LinkedHashMap;

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
}
