package io.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.shop.dto.RegisterReqDto;
import io.shop.mapper.LoginReqDtoMapper;
import io.shop.mapper.RegisterReqDtoMapper;
import io.shop.mapper.UserDtoMapper;
import io.shop.model.User;
import io.shop.repository.UserRepository;
import io.shop.services.api.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static io.shop.services.impl.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @SpyBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private LoginReqDtoMapper loginAdDtoMapper;

    @SpyBean
    private RegisterReqDtoMapper registerReqDtoMapper;

    @SpyBean
    private UserDtoMapper userDtoMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    void login() throws Exception {
        encoder = new BCryptPasswordEncoder();
        User user1 = new User(ID1, LAST_NAME_1, FIRST_NAME_1, PHONE_1,
                EMAIL_1, CITY_1, IMAGE_1, REG_DATE_1, encoder.encode(PASSWORD_1), USERNAME_1, ROLE_ENUM_1);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user1));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/login") //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(LOGIN_REQ_DTO_1))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void register() throws Exception {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        when(registerReqDtoMapper.toEntity(any(RegisterReqDto.class))).thenReturn(USER_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/register") //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(REGISTER_REQ_DTO_1))
                ).andDo(print())
                .andExpect(status().isOk());
    }
}