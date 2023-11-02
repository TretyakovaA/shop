package io.shop.services.impl;

import io.shop.mapper.UserDtoMapper;
import io.shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl out;

    @Mock
    private UserDtoMapper userDtoMapper;

    @Test
    void getUser() {
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