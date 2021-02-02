package com.w0lfaton;

import javafx.collections.ObservableList;

import java.io.*;

public class ApiData {
    private static ApiData instance = new ApiData();
    private static String filename = "resources/tempData.json";

    private ObservableList<ApiModule> apiModules;

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
