package io.swagger.services.api;

import io.swagger.dto.RegisterReq;
import io.swagger.dto.RoleEnum;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, RoleEnum role);
}
