package com.totemstorage.gateway.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.azure.storage.common.StorageSharedKeyCredential;
import com.totemstorage.gateway.dto.TotemPacketDTO;

import org.springframework.stereotype.Service;

@Service
public class GatewayService {
    private static final String GATEWAY_FAILURE = "Totem Storage Gateway: connection failed. ";
    private boolean blobExists = false;
    private InputStream dataStream;
    byte[] dataTotem;

    public TotemPacketDTO blobStorage(TotemPacketDTO totemPacketDTO) {

        try {
            String accountName = CredentialService.getAccountName();
            String accountKey = CredentialService.getAccountKey();
            StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);
            String endpoint = String.format(Locale.ROOT, "https://%s.blob.core.windows.net", accountName);
            BlobServiceClient storageClient = new BlobServiceClientBuilder().endpoint(endpoint).credential(credential).buildClient();
            
            BlobContainerClient blobContainerClient = storageClient.getBlobContainerClient("sandbox/safety_analytics/totem/relatos");
            blobExists = blobContainerClient.getBlobClient(totemPacketDTO.getFileName()).exists();
            BlockBlobClient blobClient = blobContainerClient.getBlobClient(totemPacketDTO.getFileName()).getBlockBlobClient();

            dataTotem = totemPacketDTO.getData().toString().getBytes(StandardCharsets.UTF_8);
            dataStream = new ByteArrayInputStream(dataTotem); 
            blobClient.upload(dataStream, dataTotem.length);
            dataStream.close();
        
        } catch (Exception e) {
            if(blobExists)
            throw new ExceptionGateway("Já existe. " + e.getMessage());
            else throw new ExceptionGateway(GATEWAY_FAILURE + e.getMessage());
        }

        return totemPacketDTO;
    }
}
