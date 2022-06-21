package com.nuance.controller;

import com.nuance.service.BlobService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;

import javax.inject.Inject;

@Controller("podidentitydemo")
public class BlobController {

    @Inject
    BlobService blobService;

    @Get("/{blobname}")
    public String handleRequest(@PathVariable String blobname) {
        try {
            blobService.addBlob(blobname);
            return "Blob object created (or updated) having name: " + blobname;
        } catch (Exception e) {
            return "Blob object creation failed.";
        }
    }

}
