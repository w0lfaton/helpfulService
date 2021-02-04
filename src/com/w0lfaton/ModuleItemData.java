package com.w0lfaton;

import javafx.collections.ObservableList;

public class ModuleItemData {
    private static ModuleItemData instance = new ModuleItemData();
    private static String filename = "resources/ModuleItemData.json";

    private ObservableList<ModuleItem> moduleItems;

    private ModuleItemData() {
        this.loadData();
    }

    public static ModuleItemData getInstance() {
        return instance;
    }

    public void saveData() {

    }

    public ObservableList<ModuleItem> getToDoItems() {
        return moduleItems;
    }

    public void addToDoItem(ModuleItem toDoItem) {
        if (toDoItem != null) {
            moduleItems.add(toDoItem);
        }
    }

    public boolean loadData() {
        return false;
    }
}
