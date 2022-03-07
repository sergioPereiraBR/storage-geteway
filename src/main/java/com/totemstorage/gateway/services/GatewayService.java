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
import com.totemstorage.gateway.dto.TotemPacketDTO;

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

    public TotemPacketDTO blobStorage(TotemPacketDTO totemPacketDTO) {
        try {
            BlobContainerClient container = container();
            
            blobExists = container.getBlobClient(totemPacketDTO.getFileName()).exists();
            BlockBlobClient blobClient = container.getBlobClient(totemPacketDTO.getFileName()).getBlockBlobClient();

            dataTotem = totemPacketDTO.getData().toString().getBytes(StandardCharsets.UTF_8);
            dataStream = new ByteArrayInputStream(dataTotem); 
            blobClient.upload(dataStream, dataTotem.length);
            dataStream.close();
        
        } catch (Exception e) {
            if(blobExists)
            throw new ExceptionGateway("<<Não é permitido sobrescrever o blob existente>>. " + e.getMessage());
            else throw new ExceptionGateway("<<blobStorage(TotemPacketDTO totemPacketDTO)>>. " + e.getMessage());
        }

        return totemPacketDTO;
    }

    public List<String> listBlobs() {
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

    public ByteArrayOutputStream blob(String id) {
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

    public TotemPacketDTO downloadBlobStorage(String id) {
        TotemPacketDTO blob = new TotemPacketDTO ();

        try {
            blob.setData(blob(id).toString());
            blob.setFileName(id);
        } catch (Exception e) {
            throw new ExceptionGateway("<<downloadBlobStorage(String id)>>. "+blob.getFileName() +" - "+blob.getData()   +" - "+ e.getMessage());
        }

        return blob;
    }

    public List<TotemPacketDTO> dounloadListBlobs() {
        //public List<TotemPacketDTO> dounloadListBlobs(List<String> id){
        List<TotemPacketDTO> list = new ArrayList<TotemPacketDTO>();

        try {
            List<String> blobsList = listBlobs();

            blobsList.forEach(blobName -> 
                list.add(downloadBlobStorage(blobName))
            );
        } catch (Exception e) {
            throw new ExceptionGateway("<<listBlobs()>>. " + e.getMessage());
        }

        return list;
    }
    
}
