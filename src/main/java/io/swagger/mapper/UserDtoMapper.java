package io.swagger.mapper;

import io.swagger.dto.UserDto;
import io.swagger.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    List<UserDto> toDtos(List<User> userList);
}
