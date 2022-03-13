package com.totemstorage.gateway.dto;

import javax.validation.constraints.NotNull;

public class TotemPackgeDTO {

    @NotNull(message = "The first argument cannot be null.")
    private String blobName;

    @NotNull(message = "The second argument cannot be null.")
    private Object blobPackage;

    
    public String getBlobName() {
        return blobName;
    }

    public Object getBlobPackage() {
        return blobPackage;
    }

    public void setBlobName(String blobName) {
        this.blobName = blobName;
    }

    public void setBlobPackage(Object blobPackage) {
        this.blobPackage = blobPackage;
    }

}
