package io.shop.mapper;

import io.shop.dto.CreateAdDto;
import io.shop.model.Ad;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreateAdDtoMapper {
    Ad toEntity(CreateAdDto adsComment);

    CreateAdDto toDto(Ad ad);

    List<CreateAdDto> toDtos(List<Ad> adList);
}
