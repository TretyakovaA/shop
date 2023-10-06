package io.swagger.services.impl;

import io.swagger.dto.NewPasswordDto;
import io.swagger.dto.UserDto;
import io.swagger.exceptions.UserNotFoundException;
import io.swagger.mapper.UserDtoMapper;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import io.swagger.services.api.AdService;
import io.swagger.services.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    Logger logger = LoggerFactory.getLogger(AdService.class);

    public UserServiceImpl(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public UserDto getUser(Integer id) {
        User foundUser = userRepository.findById(id).orElseThrow(() -> {
            logger.info("Пользователь с id " + id + " не найден");
            throw new UserNotFoundException(id);
        });

        logger.info("Пользователь с id " + id + " найден");
        return userDtoMapper.toDto(foundUser);
    }

    @Override
    public NewPasswordDto setPassword(NewPasswordDto body) {
        return null;
    }

    @Override
    public UserDto updateUser(UserDto body) {
        return null;
    }

    @Override
    public Void updateUserImage(MultipartFile image) {
        return null;
    }
}
