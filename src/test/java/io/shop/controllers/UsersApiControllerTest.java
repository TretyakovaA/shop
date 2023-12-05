package io.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.shop.dto.UserDto;
import io.shop.mapper.UserDtoMapper;
import io.shop.model.User;
import io.shop.repository.UserRepository;
import io.shop.services.api.ImageService;
import io.shop.services.impl.UserServiceImpl;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static io.shop.services.impl.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UsersApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @SpyBean
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserDtoMapper userDtoMapper;

    @Mock
    private Authentication auth;

    @Mock
    private ImageService imageService;

    @Test
    @WithMockUser (username = USERNAME_1)
    void getUser1() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setCity(USER_DTO_1.getCity());
        userDto.setImage("/files/user_avatar_test.jpg/download");
        userDto.setId(USER_DTO_1.getId());
        userDto.setEmail(USER_DTO_1.getEmail());
        userDto.setPhone(USER_DTO_1.getPhone());
        userDto.setFirstName(USER_DTO_1.getFirstName());
        userDto.setLastName(USER_DTO_1.getLastName());
        userDto.regDate(USER_DTO_1.getRegDate());
        String jsonResult = objectMapper.writeValueAsString(userDto);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));
        when(userDtoMapper.toDto(any(User.class))).thenReturn(USER_DTO_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/me") //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    @WithMockUser (username = USERNAME_1)
    void setPassword() throws Exception {
        String jsonResult = objectMapper.writeValueAsString(NEW_PASSWORD_DTO_1);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));
        when(userRepository.save(any(User.class))).thenReturn(USER_1);

        mockMvc.perform(MockMvcRequestBuilders
                                .post("/users/set_password") //посылаем
                                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(NEW_PASSWORD_DTO_1))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    @WithMockUser (username = USERNAME_1)
    void updateUser() throws Exception {
        String jsonResult = objectMapper.writeValueAsString(USER_DTO_1);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));
        when(userDtoMapper.toDto(any(User.class))).thenReturn(USER_DTO_1);
        when(userRepository.save(any(User.class))).thenReturn(USER_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/users/me") //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_DTO_1))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    @WithMockUser (username = USERNAME_1)
    void updateUserImage() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setCity(USER_DTO_1.getCity());
        userDto.setImage("/files/user_avatar_test.jpg/download");
        userDto.setId(USER_DTO_1.getId());
        userDto.setEmail(USER_DTO_1.getEmail());
        userDto.setPhone(USER_DTO_1.getPhone());
        userDto.setFirstName(USER_DTO_1.getFirstName());
        userDto.setLastName(USER_DTO_1.getLastName());
        userDto.regDate(USER_DTO_1.getRegDate());
        String jsonResult = objectMapper.writeValueAsString(userDto);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));
        when(userRepository.save(any(User.class))).thenReturn(USER_1);
        when(userDtoMapper.toDto(any(User.class))).thenReturn(USER_DTO_1);
        when(imageService.uploadFile(any())).thenReturn(Path.of("/files/user_avatar_test.jpg/download"));

        MockMultipartHttpServletRequestBuilder patchMultipart = (MockMultipartHttpServletRequestBuilder)
                MockMvcRequestBuilders.multipart("/users/me/image")
                        .with(rq -> { rq.setMethod("PATCH"); return rq; });

        MockMultipartFile image = new MockMultipartFile("image", "user_avatar_test.jpg", MediaType.IMAGE_JPEG_VALUE,
        Files.readAllBytes(Path.of("src/test/resources/user_avatar_test.jpg")) );

        mockMvc.perform(patchMultipart
                        .file(image)
                        .accept("application/json")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }
}