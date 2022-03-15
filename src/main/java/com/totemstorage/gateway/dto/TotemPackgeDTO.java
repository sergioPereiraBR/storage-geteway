package com.totemstorage.gateway.dto;

import javax.validation.constraints.NotNull;

public class TotemPackgeDTO {

    @NotNull(message = "The first argument cannot be null.")
    private String fileName;

    @NotNull(message = "The second argument cannot be null.")
    private Object data;

    
    public String getFileName() {
        return fileName;
    }

    public Object getData() {
        return data;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
