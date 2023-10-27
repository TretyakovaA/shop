package io.shop.services.api;

import io.shop.dto.RegisterReqDto;
import io.shop.dto.RoleEnum;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReqDto registerReqDto, RoleEnum role);
}
