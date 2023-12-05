package io.shop.services.impl;

import io.shop.dto.RegisterReqDto;
import io.shop.dto.RoleEnum;
import io.shop.exceptions.UserNotFoundException;
import io.shop.mapper.RegisterReqDtoMapper;
import io.shop.model.User;
import io.shop.repository.UserRepository;
import io.shop.services.api.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RegisterReqDtoMapper registerReqDtoMapper;

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthServiceImpl(UserRepository userRepository, RegisterReqDtoMapper registerReqDtoMapper) {
        this.userRepository = userRepository;
        this.registerReqDtoMapper = registerReqDtoMapper;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean login(String userName, String password) {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> {
                    logger.info("Неудачная попытка входа. " + "Пользователь с именем " + userName + " не найден в базе");
                    throw new UserNotFoundException("Пользователя с таким логином нет в базе");
                }
        );

        String encryptedPassword = user.getPassword();
        boolean okLogin = encoder.matches(password, encryptedPassword);
        logger.info("Пользователь: " + userName + " : Вошел в систему ");
        return okLogin;
    }

    @Override
    public boolean register(RegisterReqDto registerReqDto, RoleEnum roleEnum) {
        User user = null;
        try {
            user = userRepository.findByUsername(registerReqDto.getUsername()).orElseThrow();
        } catch (RuntimeException e){
        }

        if (user != null) {
            logger.info("Неудачная попытка регистрации. " + "Пользователь с именем " + registerReqDto.getUsername() + " уже есть в базе");
            throw new RuntimeException("Пользователь с таким логином уже есть в базе");
        }

        user = registerReqDtoMapper.toEntity(registerReqDto);
        user.setPassword(encoder.encode(registerReqDto.getPassword()));
        user.setRole(roleEnum);
        user.setRegDate(LocalDateTime.now());
        userRepository.save(user);

        logger.info("Пользователь: " + registerReqDto.getUsername() + " : Зарегистрирован в системе ");
        return true;
    }
}