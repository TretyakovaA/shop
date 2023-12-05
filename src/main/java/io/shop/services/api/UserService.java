package io.shop.services.api;

import io.shop.dto.UserDto;
import io.shop.dto.NewPasswordDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    UserDto getUser();

    NewPasswordDto setPassword(NewPasswordDto body);

    UserDto updateUser(UserDto body);

    UserDto updateUserImage(MultipartFile image) throws IOException;
}
