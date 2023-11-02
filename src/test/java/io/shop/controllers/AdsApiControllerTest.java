package io.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.shop.dto.RoleEnum;
import io.shop.mapper.*;
import io.shop.model.Ad;
import io.shop.model.Comment;
import io.shop.model.User;
import io.shop.repository.AdRepository;
import io.shop.repository.CommentRepository;
import io.shop.services.impl.AdServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(controllers = AdsApiController.class)
class AdsApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdRepository adRepository;

    @SpyBean
    private AdServiceImpl adServiceImpl;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private AdBodyDtoMapper adBodyDtoMapper;

    @SpyBean
    private AdDtoMapper adDtoMapper;

    @SpyBean
    private CommentDtoMapper commentDtoMapper;

    @SpyBean
    private CreateAdDtoMapper createDtoMapper;

    @SpyBean
    private FullAdDtoMapper fullAdDtoMapper;

    @SpyBean
    private ResponseWrapperAdDtoMapper responseWrapperAdDtoMapper;

    @SpyBean
    private ResponseWrapperCommentDtoMapper responseWrapperCommentDtoMapper;

    public Comment resultComment() {
        Comment comment = new Comment();
        Ad ad = new Ad();
        ad.setPk(1);
        comment.setAd(ad);
        comment.setPk(1);
        comment.setText("текст объявления");
        User user = new User();
        user.setId(1);
        comment.setAuthor(user);
        comment.setCreatedAt(LocalDateTime.of(2023, 10, 1, 12, 00));
        return comment;
    }

    public Ad resultAd() {
        Ad ad = new Ad();
        ad.setPk(1);
        User user = new User();
        user.setId(1);
        ad.setAuthor(user);
        Comment comment = new Comment();
        comment.setPk(1);
        List<Comment> comments = List.of(comment);
        ad.setComments(comments);
        ad.setPrice(1000);
        ad.setTitle("Заголовок объявления");
        ad.setDescription("Описание объявления");
        ad.setStoredImage(null);
        return ad;
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
    void addAds() {
    }

    @Test
    void addComments() {
    }

    @Test
    void deleteComments() {
    }

    @Test
    void getALLAds() {
    }

    @Test
    void getAds() {
    }

    @Test
    void getAdsMeUsingGET() {
    }

    @Test
    void getComments() {
    }

    @Test
    void getComments1() {
    }

    @Test
    void removeAds() {
    }

    @Test
    void updateAds() {
    }

    @Test
    void updateComments() {
    }
}