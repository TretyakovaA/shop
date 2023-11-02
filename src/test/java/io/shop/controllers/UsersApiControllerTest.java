package io.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.shop.dto.RoleEnum;
import io.shop.mapper.UserDtoMapper;
import io.shop.model.User;
import io.shop.repository.UserRepository;
import io.shop.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@WebMvcTest(controllers = UsersApiController.class)
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
    void getUser1() {
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