package io.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.shop.dto.RoleEnum;
import io.shop.model.Ad;
import io.shop.model.StoredImage;
import io.shop.model.User;
import io.shop.repository.StoredImageRepository;
import io.shop.services.impl.ImageServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@WebMvcTest(controllers = ImageApiController.class)
class ImageApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoredImageRepository storedImageRepository;

    @SpyBean
    private ImageServiceImpl imageServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    public StoredImage resultStoredImage() {
        StoredImage storedImage = new StoredImage();
        storedImage.setId(1);
        Ad ad = new Ad();
        ad.setPk(1);
        storedImage.setAd(ad);
        storedImage.setPath("C:\\Users\\Acer\\IdeaProjects\\spring-server-generated\\src\\main\\resources");
        return storedImage;
    }

    public User resultUser() {
        User user = new User();
        user.setId(1);
        user.setImage(null);
        user.setAds(null);
        user.setPassword("password");
        user.setFirstName("Timofei");
        user.setLastName("Timofeev");
        user.setCity("Moscow");
        user.setEmail("timofeev@gmail.com");
        user.setComments(null);
        user.setPhone("+12345678");
        user.setRegDate(LocalDateTime.parse("2023-10-01T12:00:00.00000"));
        user.setRole(RoleEnum.USER);
        user.setUsername("timofeev@gmail.com");
        return user;
    }

    @Test
    void updateImage() {
    }
}