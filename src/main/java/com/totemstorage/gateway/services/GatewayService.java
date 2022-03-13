package com.totemstorage.gateway.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.azure.storage.common.StorageSharedKeyCredential;
import com.totemstorage.gateway.dto.TotemPackgeDTO;

import org.springframework.stereotype.Service;

@Service
public class GatewayService {
    private static final String GATEWAY_FAILURE = "Totem Storage Gateway: connection failed. ";
    private boolean blobExists = false;
    private InputStream dataStream;

    byte[] dataTotem;

    private BlobContainerClient container() {
        try {
            String accountName = CredentialService.getAccountName();
            String accountKey = CredentialService.getAccountKey();
            StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);
            String endpoint = String.format(Locale.ROOT, "https://%s.blob.core.windows.net", accountName);
            BlobServiceClient serviceClinte = new BlobServiceClientBuilder().endpoint(endpoint).credential(credential).buildClient();
            BlobContainerClient container = serviceClinte.getBlobContainerClient("nde"); //sandbox/safety_analytics/totem/relatos

            return container;
        } catch (Exception e) {
            throw new ExceptionGateway(GATEWAY_FAILURE +"<<container()>>. "+ e.getMessage());
        }
    }

    public TotemPackgeDTO blobToBlobStorage(TotemPackgeDTO totemPackgeDTO) {
        try {
            BlobContainerClient container = container();
            
            blobExists = container.getBlobClient(totemPackgeDTO.getBlobName()).exists();
            BlockBlobClient blobClient = container.getBlobClient(totemPackgeDTO.getBlobName()).getBlockBlobClient();

            dataTotem = totemPackgeDTO.getBlobPackage().toString().getBytes(StandardCharsets.UTF_8);
            dataStream = new ByteArrayInputStream(dataTotem); 
            blobClient.upload(dataStream, dataTotem.length);
            dataStream.close();
        
        } catch (Exception e) {
            if(blobExists)
            throw new ExceptionGateway("<<Não é permitido sobrescrever o blob>>. " + e.getMessage());
            else throw new ExceptionGateway("<<blobStorage(TotemPacketDTO totemPacketDTO)>>. " + e.getMessage());
        }

        return totemPackgeDTO;
    }

    public List<String> blobsFromBlobStorage() {
        List<String> list = new ArrayList<String>();

        try {
            BlobContainerClient container = container();

            container.listBlobs()
                .forEach(blobItem -> list.add(
                    blobItem.getName()
                ));

        } catch (Exception e) {
            throw new ExceptionGateway("<<listBlobs()>>. " + e.getMessage());
        }

        return list;
    }

    public ByteArrayOutputStream blobFromBlobStorage(String id) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            BlobContainerClient container = container();

            BlobClient blobClient = container.getBlobClient(id);

            blobClient.download(os);
        } catch (Exception e) {
                throw new ExceptionGateway("<<blob(String id)>>. " + e.getMessage());
        }

        return os;
    }

    public TotemPackgeDTO downloadBlobFromBlobStorage(String id) {
        TotemPackgeDTO blob = new TotemPackgeDTO ();

        try {
            blob.setBlobPackage(blobFromBlobStorage(id).toString());
            blob.setBlobName(id);
        } catch (Exception e) {
            throw new ExceptionGateway("<<downloadBlobStorage(String id)>>. "+blob.getBlobName() +" - "+blob.getBlobPackage()   +" - "+ e.getMessage());
        }

        return blob;
    }

    public List<TotemPackgeDTO> downloadAllBlobsFromBlobStorage() {
        List<TotemPackgeDTO> list = new ArrayList<TotemPackgeDTO>();

        try {
            List<String> blobsList = blobsFromBlobStorage();

            blobsList.forEach(blobName -> 
                list.add(downloadBlobFromBlobStorage(blobName))
            );
        } catch (Exception e) {
            throw new ExceptionGateway("<<listBlobs()>>. " + e.getMessage());
        }
        
        return list;
    } 

    // TODO: Ajustar com Gestor se implementa downloadRangeBlobsFromBlobStorage [data inicial - data final]
}
