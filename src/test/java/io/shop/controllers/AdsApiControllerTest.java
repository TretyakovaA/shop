package io.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.shop.mapper.*;
import io.shop.model.Ad;
import io.shop.model.Comment;
import io.shop.model.User;
import io.shop.repository.AdRepository;
import io.shop.repository.CommentRepository;
import io.shop.repository.UserRepository;
import io.shop.services.api.ImageService;
import io.shop.services.impl.AdServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static io.shop.services.impl.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
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

    @Mock
    private AdBodyDtoMapper adBodyDtoMapper;

    @Mock
    private AdDtoMapper adDtoMapper;

    @Mock
    private CommentDtoMapper commentDtoMapper;

    @Mock
    private CreateAdDtoMapper createAdDtoMapper;

    @Mock
    private FullAdDtoMapper fullAdDtoMapper;

    @Mock
    private ResponseWrapperAdDtoMapper responseWrapperAdDtoMapper;

    @Mock
    private ResponseWrapperCommentDtoMapper responseWrapperCommentDtoMapper;

    @Mock
    private ImageService imageService;

    @Mock
    private Authentication auth;

    @MockBean
    private UserRepository userRepository;

    @Test
    void addAds() throws Exception {
        String jsonResult = objectMapper.writeValueAsString(AD_DTO_1);
        when(auth.getName()).thenReturn(USERNAME_1);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));
        when(createAdDtoMapper.toEntity(CREATE_AD_DTO_1)).thenReturn(AD_1);
        when(adRepository.save(any(Ad.class))).thenReturn((AD_1));
        when(imageService.uploadFile(any())).thenReturn(Path.of("/files/user_avatar_test.jpg/download"));
        when(adDtoMapper.toDto(AD_1)).thenReturn(AD_DTO_1);
        String myContent = objectMapper.writeValueAsString(CREATE_AD_DTO_1);
        MockMultipartFile jsonValue =
                new MockMultipartFile("properties", null, MediaType.APPLICATION_JSON_VALUE, myContent.getBytes());

        mockMvc.perform(multipart("/ads") //посылаем
                        .file(jsonValue)
                        .file("image", Files.readAllBytes(Path.of("src/test/java/resources/user_avatar_test.jpg")))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));

        SecurityContextHolder.clearContext();
    }

    @Test
    void addComments() throws Exception {
        String jsonResult = objectMapper.writeValueAsString(COMMENT_DTO_1);

        when(auth.getName()).thenReturn(USERNAME_1);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));
        when(commentDtoMapper.toEntity(COMMENT_DTO_1)).thenReturn(COMMENT_1);
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD_1));
        when(commentRepository.save(any(Comment.class))).thenReturn(COMMENT_1);
        when(commentDtoMapper.toDto(COMMENT_1)).thenReturn(COMMENT_DTO_1);

        mockMvc.perform(MockMvcRequestBuilders
                                .post("/ads/" + ID1 + "/comments") //посылаем
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(COMMENT_DTO_1))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));

        SecurityContextHolder.clearContext();
    }

    @Test
    void deleteComments() throws Exception {
        when(auth.getName()).thenReturn(USERNAME_1);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));
        when(commentDtoMapper.toDto(COMMENT_1)).thenReturn(COMMENT_DTO_1);
        when(commentRepository.findById(any(Integer.class))).thenReturn(Optional.of(COMMENT_1));
        doNothing().when(commentRepository).delete(COMMENT_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ads/" + ID1 + "/comments/" + ID1) //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());

        SecurityContextHolder.clearContext();
    }

    @Test
    void getALLAds() throws Exception {
        String jsonResult = objectMapper.writeValueAsString(RESPONSE_WRAPPER_AD_DTO_1);

        when(adRepository.findAll()).thenReturn(List.of(AD_1));
        when(adDtoMapper.toDtos(anyList())).thenReturn(List.of(AD_DTO_1));
        when(responseWrapperAdDtoMapper.toDto(1, List.of(AD_DTO_1))).thenReturn(RESPONSE_WRAPPER_AD_DTO_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads") //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void getAds() throws Exception {
        String jsonResult = objectMapper.writeValueAsString(FULL_AD_DTO_1);

        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD_1));
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(USER_1));
        when(fullAdDtoMapper.toDto(any(Ad.class), any(User.class))).thenReturn(FULL_AD_DTO_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/" + ID1) //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void getAdsMeUsingGET() throws Exception {
        String jsonResult = objectMapper.writeValueAsString(RESPONSE_WRAPPER_AD_DTO_1);

        when(auth.getName()).thenReturn(USERNAME_1);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));
        when(adRepository.findByAuthor(any(User.class))).thenReturn(List.of(AD_1));
        when(adDtoMapper.toDtos(anyList())).thenReturn(List.of(AD_DTO_1));
        when(responseWrapperAdDtoMapper.toDto(1, List.of(AD_DTO_1))).thenReturn(RESPONSE_WRAPPER_AD_DTO_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/me") //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));

        SecurityContextHolder.clearContext();
    }

    @Test
    void getComments() throws Exception {
        String jsonResult = objectMapper.writeValueAsString(RESPONSE_WRAPPER_COMMENT_DTO_1);

        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD_1));
        when(commentDtoMapper.toDtos(anyList())).thenReturn(List.of(COMMENT_DTO_1));
        when(responseWrapperCommentDtoMapper.toDto(1, List.of(COMMENT_DTO_1))).thenReturn(RESPONSE_WRAPPER_COMMENT_DTO_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/" + ID1 + "/comments") //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void getComments1() throws Exception {
        String jsonResult = objectMapper.writeValueAsString(COMMENT_DTO_1);

        when(commentRepository.findById(any(Integer.class))).thenReturn(Optional.of(COMMENT_1));
        when(commentDtoMapper.toDto(any(Comment.class))).thenReturn(COMMENT_DTO_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/" + ID1 + "/comments/" + ID1) //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void removeAds() throws Exception {
        when(auth.getName()).thenReturn(USERNAME_1);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));
        when(adDtoMapper.toDto(AD_1)).thenReturn(AD_DTO_1);
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD_1));
        doNothing().when(adRepository).delete(AD_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ads/" + ID1) //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());

        SecurityContextHolder.clearContext();
    }

    @Test
    void updateAds() throws Exception {
        String jsonResult = objectMapper.writeValueAsString(AD_DTO_1);

        when(auth.getName()).thenReturn(USERNAME_1);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD_1));
        when(adDtoMapper.toDto(AD_1)).thenReturn(AD_DTO_1);
        when(adRepository.save(any(Ad.class))).thenReturn(AD_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/ads/" + ID1) //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CREATE_AD_DTO_1))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));

        SecurityContextHolder.clearContext();
    }

    @Test
    void updateComments() throws Exception {
        String jsonResult = objectMapper.writeValueAsString(COMMENT_DTO_1);

        when(auth.getName()).thenReturn(USERNAME_1);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));
        when(commentRepository.findById(any(Integer.class))).thenReturn(Optional.of(COMMENT_1));
        when(commentDtoMapper.toDto(COMMENT_1)).thenReturn(COMMENT_DTO_1);
        when(commentRepository.save(any(Comment.class))).thenReturn(COMMENT_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/ads/" + ID1 + "/comments/" + ID1) //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(COMMENT_DTO_1))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));

        SecurityContextHolder.clearContext();
    }
}