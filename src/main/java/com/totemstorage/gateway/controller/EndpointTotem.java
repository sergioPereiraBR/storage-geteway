package com.totemstorage.gateway.controller;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import com.totemstorage.gateway.dto.TotemPackgeDTO;
import com.totemstorage.gateway.services.GatewayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/totem")
public class EndpointTotem {

    @Autowired
    private GatewayService gatewayStorageService;

    @CrossOrigin
    @GetMapping
    public String getGatewayTotem() {
        Date date = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");       
        return "Return from /totem endpoint at " + dateForm.format(date);
    }

    @CrossOrigin
    @PostMapping(consumes = "application/json")
    public ResponseEntity<TotemPackgeDTO> toPacketStoragee(@Valid @RequestBody TotemPackgeDTO totemPacketDTO)
    {   
        TotemPackgeDTO response = gatewayStorageService.blobToBlobStorage(totemPacketDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{totem}").buildAndExpand(response).toUri();
        return ResponseEntity.created(uri).build();
    }

}

