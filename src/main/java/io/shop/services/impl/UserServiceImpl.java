package io.shop.services.impl;

import io.shop.dto.UserDto;
import io.shop.exceptions.UserNotFoundException;
import io.shop.dto.NewPasswordDto;
import io.shop.mapper.UserDtoMapper;
import io.shop.model.User;
import io.shop.repository.UserRepository;
import io.shop.services.api.AdService;
import io.shop.services.api.ImageService;
import io.shop.services.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final ImageService imageService;

    Logger logger = LoggerFactory.getLogger(AdService.class);

    public UserServiceImpl(UserRepository userRepository, UserDtoMapper userDtoMapper, ImageService imageService) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
        this.imageService = imageService;
    }

    @Override
    public UserDto getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(()
                -> {
            throw new UserNotFoundException("Пользователь с именем "+ auth.getName()+" не найден в базе");});
//        Integer id = user.getId();
//
//        User foundUser = userRepository.findById(id).orElseThrow(() -> {
//            logger.info("Пользователь с id " + id + " не найден");
//            throw new UserNotFoundException(id);
//        });

        logger.info("Пользователь с логином " + auth.getName() + " найден");
        return userDtoMapper.toDto(user);
    }

    @Override
    public NewPasswordDto setPassword(NewPasswordDto body) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(()
                -> {
            throw new UserNotFoundException("Пользователь с именем "+ auth.getName()+" не найден в базе");});
        if (user.getPassword().equals(body.getCurrentPassword())) {
            user.setPassword(body.getNewPassword());
            userRepository.save(user);
            return body;
        }
        return null;
    }

    @Override
    public UserDto updateUser(UserDto body) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User oldUser = userRepository.findByUsername(auth.getName()).orElseThrow(()
                -> {
            throw new UserNotFoundException("Пользователь с именем "+ auth.getName()+" не найден в базе");});
//        Integer id = user.getId();
//
//        User oldUser = userRepository.findById(id).orElseThrow(()
//                -> {
//            throw new UserNotFoundException(id);
//        });
        if (body.getCity() != null) {
            oldUser.setCity(body.getCity());
        }
        if (body.getLastName() != null) {
            oldUser.setLastName(body.getLastName());
        }
        if (body.getImage() != null) {
            oldUser.setImage(body.getImage());
        }
        if (body.getPhone() != null) {
            oldUser.setPhone(body.getPhone());
        }
        if (body.getEmail() != null) {
            oldUser.setEmail(body.getEmail());
        }
        if (body.getFirstName() != null) {
            oldUser.setFirstName(body.getFirstName());
        }
        if (body.getRegDate() != null) {
            oldUser.setRegDate(body.getRegDate());
        }
        return userDtoMapper.toDto(userRepository.save(oldUser));
    }
/*
2) Поле image в DTO это не путь к файлу в файловой системе, а путь к эндпоинту,
с помощью которого фронтеэнд это изображение может запросить. То есть не
"src/main/resources/pictures/01.png"

а
"/files/01.png/download"
 */
    @Override
    public UserDto updateUserImage(MultipartFile image) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(()
                -> {
            throw new UserNotFoundException("Пользователь с именем "+ auth.getName()+" не найден в базе");});
        Path path = imageService.uploadFile(image);
        //user.setImage(path.toString());
        user.setImage("/files/"+path.getFileName().toString()+"/download");
        userRepository.save(user);
        return userDtoMapper.toDto(user);
    }
}
