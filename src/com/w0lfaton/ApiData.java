package com.w0lfaton;

import javafx.collections.ObservableList;

public class ApiData {
    private static ApiData instance = new ApiData();
    private static String filename = "resources/apiData.json";

    private ObservableList<Module> modules;

    private ApiData() {
        this.loadData();
    }

    public static ApiData getInstance() {
        return instance;
    }

    public void saveData() {

    }

    public boolean loadData() {
        return false;
    }
}
