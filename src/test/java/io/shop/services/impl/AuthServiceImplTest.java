package io.shop.services.impl;

import io.shop.mapper.LoginReqDtoMapper;
import io.shop.mapper.RegisterReqDtoMapper;
import io.shop.mapper.UserDtoMapper;
import io.shop.repository.UserRepository;
import io.shop.services.api.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService out;

    @Mock
    private LoginReqDtoMapper loginAdDtoMapper;

    @Mock
    private RegisterReqDtoMapper registerReqDtoMapper;

    @Mock
    private UserDtoMapper userDtoMapper;

    @Test
    void login() {
    }

    @Test
    void register() {
    }
}