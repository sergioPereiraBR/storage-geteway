package com.totemstorage.gateway.services;

//import com.azure.core.util.Configuration;

public class CredentialService {
    static String getAccountName() {
        return "contastoragessp"; 
        //return Configuration.getGlobalConfiguration().get("PRIMARY_STORAGE_ACCOUNT_NAME");
    }

    static String getAccountKey() {
        return "j25sOuiDIxG0+0gGzNjKyxsuejFNGGh+AKAqJjjfqHI/yP5O7cJq4mqbvQUA886Ta/UGRDeyMMfgd6/Tfx6U/Q==";
        //return Configuration.getGlobalConfiguration().get("PRIMARY_STORAGE_ACCOUNT_KEY");
    }
}
