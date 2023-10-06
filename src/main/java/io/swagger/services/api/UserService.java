package io.swagger.services.api;

import io.swagger.dto.NewPasswordDto;
import io.swagger.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserDto getUser(Integer id);

    NewPasswordDto setPassword(NewPasswordDto body);

    UserDto updateUser(UserDto body);

    Void updateUserImage(MultipartFile image);
}
