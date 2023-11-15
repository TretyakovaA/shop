package io.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.shop.dto.RoleEnum;
import io.shop.mapper.UserDtoMapper;
import io.shop.model.User;
import io.shop.repository.UserRepository;
import io.shop.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

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

    @SpyBean
    private UserDtoMapper userDtoMapper;

    @Mock
    private Authentication auth;

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

  /*  @WithMockUser(username = USERNAME_1)
    @Test
    void getUser1() throws Exception {
        Integer id = 1;
        String password = "password";
        String firstName = "Timofei";
        String lastName = "Timofeev";
        String city = "Moscow";
        String email = "timofeev@gmail.com";
        String phone = "+12345678";
        LocalDateTime regDate = LocalDateTime.parse("2023-10-01T12:00:00.00000");
        RoleEnum role = RoleEnum.USER;
        String userName = "timofeev@gmail.com";

        String jsonResult = objectMapper.writeValueAsString(userDtoMapper.toDto(resultUser()));

        User foundUser = new User();
        foundUser.setId(id);
        foundUser.setUsername(userName);
        foundUser.setEmail(email);
        foundUser.setPhone(phone);
        foundUser.setRole(role);
        foundUser.setCity(city);
        foundUser.setComments(null);
        foundUser.setAds(null);
        foundUser.setPassword(password);
        foundUser.setLastName(lastName);
        foundUser.setFirstName(firstName);
        foundUser.setRegDate(regDate);
        foundUser.setImage(null);

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(foundUser));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/me/" + id)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));

//        long id = 1L;
//        String name = "Valya";
//        String address = "г. Москва, Таймырская ул., д.6";
//
//        String jsonResult = objectMapper.writeValueAsString(maternityHospitalDtoMapper.toDto(resultMaternityHospital()));
//
//        MaternityHospital foundMaternityHospital = new MaternityHospital();
//        foundMaternityHospital.setId(id);
//        foundMaternityHospital.setName(name);
//        foundMaternityHospital.setAddress(address);
//
//        when(maternityHospitalRepository.findById(any(long.class))).thenReturn(Optional.of(foundMaternityHospital));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/maternity/hospital/" + id)
//                ).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json(jsonResult));
    }
   */

    @Test
    void setPassword() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void updateUserImage() {
    }
}