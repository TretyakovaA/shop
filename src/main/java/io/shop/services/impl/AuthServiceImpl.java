package io.shop.services.impl;

import io.shop.dto.RegisterReqDto;
import io.shop.dto.RoleEnum;
import io.shop.exceptions.UserNotFoundException;
import io.shop.mapper.RegisterReqDtoMapper;
import io.shop.model.User;
import io.shop.repository.UserRepository;
import io.shop.services.api.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RegisterReqDtoMapper registerReqDtoMapper;

    public AuthServiceImpl(UserRepository userRepository, RegisterReqDtoMapper registerReqDtoMapper) {
        this.userRepository = userRepository;
        this.registerReqDtoMapper = registerReqDtoMapper;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean login(String userName, String password) {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> {
                    throw new UserNotFoundException("Пользователя с таким логином нет в базе");
                }
        );

        String encryptedPassword = user.getPassword();
        boolean okLogin = encoder.matches(password, encryptedPassword);
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
            throw new RuntimeException("Пользователь с таким логином уже есть в базе");
        }

        user = registerReqDtoMapper.toEntity(registerReqDto);
        user.setPassword(encoder.encode(registerReqDto.getPassword()));
        user.setRole(roleEnum);
        user.setRegDate(LocalDateTime.now());
        userRepository.save(user);

        return true;
    }
}