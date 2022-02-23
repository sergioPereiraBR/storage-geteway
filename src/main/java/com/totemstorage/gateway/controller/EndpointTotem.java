package com.totemstorage.gateway.controller;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import com.totemstorage.gateway.dto.TotemPacketDTO;
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

@Configuration
@RestController
@RequestMapping
public class EndpointTotem {

    @Autowired
    private GatewayService gatewayStorageService;

    @CrossOrigin
    @GetMapping
    public String getGatewayTotem() {
        Date date = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");       
        return "Return from Totem Endpoint at " + dateForm.format(date);
    }

    @CrossOrigin
    @PostMapping(value="/totem", consumes = ("application/json"))
    public ResponseEntity<TotemPacketDTO> toPacketStoragee(@Valid @RequestBody TotemPacketDTO totemPacketDTO)
    {   
        TotemPacketDTO response = gatewayStorageService.blobStorage(totemPacketDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{totem}").buildAndExpand(response).toUri();
        return ResponseEntity.created(uri).build();
    }
}

