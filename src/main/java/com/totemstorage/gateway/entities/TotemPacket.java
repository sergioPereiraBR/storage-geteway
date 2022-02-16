package com.totemstorage.gateway.entities;

import javax.validation.constraints.NotNull;

public class TotemPacket {

    @NotNull(message = "The first argument cannot be null.")
    private String fileName;

    @NotNull(message = "The second argument cannot be null.")
    private String data;

    
    public String getFileName() {
        return fileName;
    }

    public String getData() {
        return data;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setData(String data) {
        this.data = data;
    }

}