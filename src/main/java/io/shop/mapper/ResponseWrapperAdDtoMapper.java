package io.shop.mapper;

import io.shop.dto.AdDto;
import io.shop.dto.ResponseWrapperAdDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ResponseWrapperAdDtoMapper {
    @Mapping(source = "count", target = "count")
    @Mapping(source = "ads", target = "results")
    ResponseWrapperAdDto toDto(Integer count, List<AdDto> ads);
}
