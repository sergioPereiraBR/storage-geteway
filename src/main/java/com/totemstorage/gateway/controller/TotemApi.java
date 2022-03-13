package com.totemstorage.gateway.controller;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.totemstorage.gateway.dto.TotemPacketDTO;
import com.totemstorage.gateway.services.GatewayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/totemapi/v1/admin")
public class TotemApi {

    @Autowired
    private GatewayService gatewayStorageService;

    @CrossOrigin
    @GetMapping("/all")
    public String getAllBlobs() {
        List<TotemPacketDTO> list = new ArrayList<TotemPacketDTO>();
        list = gatewayStorageService.downloadAllBlobsFromBlobStorage();
        Gson gson = new Gson();
        return  gson.toJson(list);
    }

    @CrossOrigin
    @GetMapping(value = "/id/{id}")
    public TotemPacketDTO getBlob(@PathVariable String id){
        return gatewayStorageService.downloadBlobFromBlobStorage(id);
    }
}

