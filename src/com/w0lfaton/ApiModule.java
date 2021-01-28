package com.w0lfaton;

public class ApiModule {
    private final String MODULE_NAME;
    private String[] fields;

    public ApiModule(String moduleName) {
        this.MODULE_NAME = moduleName;
    }

    public String getMODULE_NAME() {
        return MODULE_NAME;
    }
}
