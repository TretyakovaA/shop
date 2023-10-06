package io.swagger.mapper;

import io.swagger.dto.LoginReqDto;
import io.swagger.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoginReqDtoMapper {

    User toEntity(LoginReqDto loginReqDto);

    LoginReqDto toDto(User user);

    List<LoginReqDto> toDtos(List<User> userList);
}
