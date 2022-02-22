package com.totemstorage.gateway.dto;

import javax.validation.constraints.NotNull;

public class TotemPacketDTO {

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
