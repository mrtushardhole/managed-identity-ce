package com.nuance.service;

import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Singleton
public class BlobService {

    public static final String POD_IDENTITY_DEMO_CONTAINER = "pod-identity-demo";
    // hardcoding for demo
    private final String storageEndpoint = "https://podidentitystorageacc.blob.core.windows.net/";
    private static final Logger log = LoggerFactory.getLogger(BlobService.class);

    /**
     * Create new blob with the given name.
     * @param blobName Name of the blob.
     */
    public void addBlob(String blobName){
        log.info("Create client using Pod-Identity");
        final BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().endpoint(storageEndpoint).credential(new ManagedIdentityCredentialBuilder().build())
                .buildClient();
        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(POD_IDENTITY_DEMO_CONTAINER);
        if (!blobContainerClient.exists()) {
            blobServiceClient.createBlobContainer(POD_IDENTITY_DEMO_CONTAINER);
        }
        if (blobContainerClient.getBlobClient(blobName).exists()) {
            log.info("Blob with name {} already exists.", blobName);
        } else {
            // Blob does not exists, create new blob
            BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
            // Create empty blob
            InputStream targetStream = new ByteArrayInputStream("".getBytes());
            blobClient.upload(targetStream, 0);
            log.debug("Created New Blob having name: {} ", blobName);
        }
    }

}
