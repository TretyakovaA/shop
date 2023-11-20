package io.shop.services.impl;

import io.shop.exceptions.UserNotFoundException;
import io.shop.mapper.UserDtoMapper;
import io.shop.model.User;
import io.shop.repository.UserRepository;
import io.shop.services.api.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static io.shop.services.impl.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl out;

    @Mock
    private UserDtoMapper userDtoMapper;

    @Mock
    private Authentication auth;

    @Mock
    private ImageService imageService;

    @Test
    void getUser() {
        when(auth.getName()).thenReturn(USERNAME_1);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(USER_1));
        when(userDtoMapper.toDto(any(User.class))).thenReturn(USER_DTO_1);

        assertEquals(out.getUser(), USER_DTO_1);
        SecurityContextHolder.clearContext();
    }

    @Test
    void userNotFoundExceptionWhenGetUser() {
        when(auth.getName()).thenReturn(USERNAME_1);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> out.getUser());
        SecurityContextHolder.clearContext();
    }

    @Test
    void setPassword() {
        when(auth.getName()).thenReturn(USERNAME_1);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));
        when(userRepository.save(any(User.class))).thenReturn(USER_1);

        assertEquals(out.setPassword(NEW_PASSWORD_DTO_1), NEW_PASSWORD_DTO_1);
        SecurityContextHolder.clearContext();
    }

    @Test
    void userNotFoundExceptionWhenSetPassword() {
        when(auth.getName()).thenReturn(USERNAME_1);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> out.setPassword(NEW_PASSWORD_DTO_1));
        SecurityContextHolder.clearContext();
    }

    @Test
    void updateUser() {
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(USER_1));
        when(userDtoMapper.toDto(any(User.class))).thenReturn(USER_DTO_1);
        when(userRepository.save(any(User.class))).thenReturn(USER_1);

        assertEquals(out.updateUser(USER_DTO_1), USER_DTO_1);
    }

    @Test
    void userNotFoundExceptionWhenUpdateUser() {
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> out.updateUser(USER_DTO_1));
    }

    @Test
    void updateUserImage() throws IOException {
        when(auth.getName()).thenReturn(USERNAME_1);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER_1));
        when(userRepository.save(any(User.class))).thenReturn(USER_1);
        when(userDtoMapper.toDto(any(User.class))).thenReturn(USER_DTO_1);
        when(imageService.uploadFile(null)).thenReturn(Path.of(IMAGE_1));

        assertEquals(out.updateUserImage(null), USER_DTO_1);
        SecurityContextHolder.clearContext();
    }

    @Test
    void userNotFoundExceptionWhenUpdateUserImage() {
        when(auth.getName()).thenReturn(USERNAME_1);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> out.updateUserImage(null));
        SecurityContextHolder.clearContext();
    }
}