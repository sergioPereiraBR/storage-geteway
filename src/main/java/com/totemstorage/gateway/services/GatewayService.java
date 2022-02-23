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
    private static final String GATEWAY_FAILURE = "Totem Storage Gateway: connection failed.";
    private boolean blobExists = false;

    public TotemPacketDTO blobStorage(TotemPacketDTO totemPacketDTO) {

        try {
            String accountName = CredentialService.getAccountName();
            String accountKey = CredentialService.getAccountKey();
            StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);
            String endpoint = String.format(Locale.ROOT, "https://%s.blob.core.windows.net", accountName);

            BlobServiceClient storageClient = new BlobServiceClientBuilder().endpoint(endpoint).credential(credential).buildClient();
            BlobContainerClient blobContainerClient = storageClient.getBlobContainerClient("sandbox/safety_analytics/totem/relatos");
            blobExists = blobContainerClient.getBlobClient(totemPacketDTO.getFileName()).exists();

            if(!blobExists){
                BlockBlobClient blobClient = blobContainerClient.getBlobClient(totemPacketDTO.getFileName()).getBlockBlobClient();
                InputStream dataStream = new ByteArrayInputStream(totemPacketDTO.getData().toString().getBytes(StandardCharsets.UTF_8)); 
                blobClient.upload(dataStream, totemPacketDTO.getData().toString().length());
                dataStream.close();
            } else {
                totemPacketDTO.setFileName("Não permitido");
                totemPacketDTO.setData("Não permitido");

            }
         
        
        } catch (Exception e) {
            throw new ExceptionGateway(GATEWAY_FAILURE + e.getMessage());
        }

        return totemPacketDTO;
    }
}
