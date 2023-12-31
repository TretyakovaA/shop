package io.shop.services.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ImageService {

    Path uploadFile(MultipartFile file) throws IOException;

    ResponseEntity<Object> downloadFile(String name, HttpServletResponse response) throws IOException;

    List<byte[]> updateImage(Integer adId, MultipartFile image) throws IOException;
}
