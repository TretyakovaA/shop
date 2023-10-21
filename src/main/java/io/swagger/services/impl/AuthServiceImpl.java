package io.swagger.services.impl;

import io.swagger.dto.RegisterReqDto;
import io.swagger.dto.RoleEnum;
import io.swagger.mapper.RegisterReqDtoMapper;
import io.swagger.mapper.UserDtoMapper;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import io.swagger.services.api.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RegisterReqDtoMapper registerReqDtoMapper;

    public AuthServiceImpl(UserDetailsManager manager, UserRepository userRepository, UserDtoMapper userDtoMapper, RegisterReqDtoMapper registerReqDtoMapper) {
        this.manager = manager;
        this.userRepository = userRepository;
        this.registerReqDtoMapper = registerReqDtoMapper;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean login(String userName, String password) {
        User user = userRepository.findByUsername(userName).get();
        if (user == null) {
            throw new RuntimeException("Пользователя с таким логином нет в базе");
        }

        String encryptedPassword = user.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword; //.substring(8);
        //return encoder.matches(password, encryptedPasswordWithoutEncryptionType);
        return encryptedPassword.equals(encryptedPasswordWithoutEncryptionType);

//        if (!manager.userExists(userName)) {
//            return false;
//        }
//        UserDetails userDetails = manager.loadUserByUsername(userName);
//        String encryptedPassword = userDetails.getPassword();
//        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
//        return encoder.matches(password, encryptedPasswordWithoutEncryptionType);
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
        user.setRole(roleEnum);
        userRepository.save(user);


//        if (manager.userExists(registerReqDto.getUsername())) {
//           return false;
//        }
//        manager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .password(registerReqDto.getPassword())
//                        .username(registerReqDto.getUsername())
//                        .roles(roleEnum.name())
//                        .build()
//        );
        return true;
    }
}