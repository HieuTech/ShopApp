package org.oauth2.shopapp.controller;

import org.oauth2.shopapp.service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/storage/")
public class BucketController {
    private final AmazonClient amazonClient;
    @Autowired
    BucketController(AmazonClient amazonClient){
        this.amazonClient = amazonClient;
    }

    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestPart(value = "file")MultipartFile file){
        return this.amazonClient.uploadFile(file);
    }
}
