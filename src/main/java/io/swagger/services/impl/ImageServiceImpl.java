package io.swagger.services.impl;

import io.swagger.repository.StoredImageRepository;
import io.swagger.services.api.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class ImageServiceImpl implements ImageService {
    private final StoredImageRepository storedImageRepository;
    @Value("${path.to.files.folder}")
    private String filesDir;

    public ImageServiceImpl(StoredImageRepository storedImageRepository) {
        this.storedImageRepository = storedImageRepository;
    }

    @Override
    public Path uploadFile(MultipartFile file) throws IOException {
        Path filePath = Path.of(filesDir,file.getOriginalFilename());
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        return filePath;
    }

    @Override
    public ResponseEntity<Object> downloadFile(String name, HttpServletResponse response) throws IOException {

        File file = new File(filesDir + "/" + name);

        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }

       /* Path path = Path.of(filesDir + "/" + name);

        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream();
        ) {
            response.setStatus(200);
            response.setContentType(String.valueOf(MediaType.APPLICATION_OCTET_STREAM));
            response.setContentLength((int) Files.size(path));
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
            is.transferTo(os);
        }*/
    }
}
