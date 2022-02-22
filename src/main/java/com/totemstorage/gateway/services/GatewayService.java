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

import org.springframework.stereotype.Service;

@Service
public class GatewayService {
    private static final String GATEWAY_FAILURE = "Totem Storage Gateway: connection failed.";

    public String blobStorage(String totemPacketFileName, String totemPacketData) {

        try {
            String accountName = CredentialService.getAccountName();
            String accountKey = CredentialService.getAccountKey();
            StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);
            String endpoint = String.format(Locale.ROOT, "https://%s.blob.core.windows.net", accountName);

            BlobServiceClient storageClient = new BlobServiceClientBuilder().endpoint(endpoint).credential(credential).buildClient();
            BlobContainerClient blobContainerClient = storageClient.getBlobContainerClient("sandbox/safety_analytics/totem/relatos");
            BlockBlobClient blobClient = blobContainerClient.getBlobClient(totemPacketFileName).getBlockBlobClient();

            InputStream dataStream = new ByteArrayInputStream(totemPacketData.getBytes(StandardCharsets.UTF_8)); 
            blobClient.upload(dataStream, totemPacketData.length());
            dataStream.close();
        } catch (Exception e) {
            throw new ExceptionGateway(GATEWAY_FAILURE + e.getMessage());
        }

        return totemPacketFileName;
    }
}
