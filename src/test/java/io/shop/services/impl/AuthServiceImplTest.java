package io.shop.services.impl;

import io.shop.dto.RegisterReqDto;
import io.shop.mapper.LoginReqDtoMapper;
import io.shop.mapper.RegisterReqDtoMapper;
import io.shop.mapper.UserDtoMapper;
import io.shop.exceptions.UserNotFoundException;
import io.shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static io.shop.services.impl.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl out;

    @Mock
    private LoginReqDtoMapper loginAdDtoMapper;

    @Mock
    private RegisterReqDtoMapper registerReqDtoMapper;

    @Mock
    private UserDtoMapper userDtoMapper;

    @Test
    void login() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));

        assertEquals(out.login(USERNAME_1, PASSWORD_1), true);
    }

    @Test
    void userNotFoundExceptionWhenLogin() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> out.login(USERNAME_1, PASSWORD_1));
    }

    @Test
    void register() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        when(registerReqDtoMapper.toEntity(any(RegisterReqDto.class))).thenReturn(USER_1);

        assertEquals(out.register(REGISTER_REQ_DTO_1, ROLE_ENUM_1), true);
    }
}