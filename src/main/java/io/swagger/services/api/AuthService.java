package io.swagger.services.api;

import io.swagger.dto.RegisterReqDto;
import io.swagger.dto.RoleEnum;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReqDto registerReqDto, RoleEnum role);
}
