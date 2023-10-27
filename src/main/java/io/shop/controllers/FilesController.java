package io.shop.controllers;

import io.shop.services.api.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class FilesController {
    private final ImageService imageService;

    public FilesController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) throws IOException {
        Path filePath = imageService.uploadFile(file);
        return ResponseEntity.ok().build();
    }

    @Value("${path.to.files.folder}")
    private String filesDir;

    @GetMapping(value = "/files/{name}/download")
    public void downloadFile(@PathVariable String name, HttpServletResponse response) throws IOException {
        imageService.downloadFile(name, response);
    }

}
