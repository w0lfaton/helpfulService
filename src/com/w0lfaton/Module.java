package com.w0lfaton;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Module {
    private final String MODULE_NAME;
    private HashMap<String, String> moduleFields;
    private LinkedList<ModuleItem> moduleItems = new LinkedList<>();

    public Module(String moduleName, HashMap<String, String> moduleFields) {
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

    private void removeModuleItem(ModuleItem item) {
        if (item != null) {
            this.moduleItems.remove(item);
        }
    }

    private void removeModuleItem(int index) {
        if (index >= 0 && index < this.moduleItems.size()) {
            this.moduleItems.remove(index);
        }
    }

    private void addModuleItem(ModuleItem item) {
        this.moduleItems.add(item);
    }

    private void removeField(String fieldName) {
        this.moduleFields.remove(fieldName);
    }

    private void addField(String fieldName, String fieldValue) {
        this.moduleFields.put(fieldName, fieldValue);
    }
}
