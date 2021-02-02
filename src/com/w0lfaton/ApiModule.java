package com.w0lfaton;

import java.util.HashMap;
import java.util.Map;

public class ApiModule {
    private final String MODULE_NAME;
    private HashMap<String, String> moduleFields;

    public ApiModule(String moduleName, HashMap<String, String> moduleFields) {
        this.MODULE_NAME = moduleName;
        this.moduleFields = moduleFields;
    }

    public String getMODULE_NAME() {
        return MODULE_NAME;
    }

    public void setField(String fieldName, String fieldValue) {
        if (this.moduleFields.containsKey(fieldName)) {
            this.moduleFields.replace(fieldName, fieldValue);
        }
    }

    public void setFields(HashMap<String, String> moduleFields) {
        for (Map.Entry<String, String> entry : moduleFields.entrySet()) {
            if (this.moduleFields.containsKey(entry.getKey())) {
                this.moduleFields.replace(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private void removeField(String fieldName) {
        this.moduleFields.remove(fieldName);
    }

    private void addField(String fieldName, String fieldValue) {
        this.moduleFields.put(fieldName, fieldValue);
    }
}
