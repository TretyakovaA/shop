package io.shop.mapper;

import io.shop.dto.RegisterReqDto;
import io.shop.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegisterReqDtoMapper {

    User toEntity(RegisterReqDto loginReqDto);

    RegisterReqDto toDto(User user);

    List<RegisterReqDto> toDtos(List<User> userList);
}
