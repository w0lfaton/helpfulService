package com.company;

import javafx.collections.ObservableList;

public class ApiData {
    private static ApiData instance = new ApiData();
    private static String filename = "apiData.json";

    private ObservableList<ApiModule> apiModules;

    private ApiData() {

    }

    public static ApiData getInstance() {
        return instance;
    }

    public boolean saveData() {
        return false;
    }

    public boolean loadData() {
        return false;
    }
}
