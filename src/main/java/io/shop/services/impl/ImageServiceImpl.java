package io.shop.services.impl;

import io.shop.exceptions.AdNotFoundException;
import io.shop.exceptions.ImageNotFoundException;
import io.shop.exceptions.UserNotFoundException;
import io.shop.model.Ad;
import io.shop.model.StoredImage;
import io.shop.model.User;
import io.shop.repository.AdRepository;
import io.shop.repository.StoredImageRepository;
import io.shop.repository.UserRepository;
import io.shop.services.api.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class ImageServiceImpl implements ImageService {
    private final StoredImageRepository storedImageRepository;
    @Value("${path.to.files.folder}")
    private String filesDir;
    private final AdRepository adRepository;

    private final UserRepository userRepository;

    public ImageServiceImpl(StoredImageRepository storedImageRepository, AdRepository adRepository, UserRepository userRepository) {
        this.storedImageRepository = storedImageRepository;
        this.adRepository = adRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Path uploadFile(MultipartFile file) throws IOException {
        Path filePath = Path.of(filesDir,file.getOriginalFilename());

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
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

    @Override
    public List<byte[]> updateImage(Integer id, MultipartFile image) throws IOException {
        //редактировать изображение может только зарегистрированный пользователь
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(()
                -> {
            throw new UserNotFoundException("Пользователь с именем " + auth.getName() + " не найден в базе");
        });

        //пользователь может изменить только свое изображение
        StoredImage storedImage = storedImageRepository.findById(id).orElseThrow(()
                -> {
            throw new ImageNotFoundException("Изображение с id " + id + " не найдено в базе", id);
        });

        if (storedImage.getAd().getAuthor().getId() != user.getId()) {
            throw new RuntimeException("нет прав на редактирование изображения");
        }

        Path path = uploadFile(image);
        Ad ad = adRepository.findById(id).orElseThrow(()
                -> { throw new AdNotFoundException(id);});
         storedImage = new StoredImage();
        storedImage.setPath(path.toString());
        storedImage = storedImageRepository.save(storedImage);
        ad.setStoredImage(Arrays.asList(storedImage));
        return Collections.singletonList(image.getBytes());
    }
}
