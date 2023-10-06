package io.swagger.mapper;

import io.swagger.dto.AdDto;
import io.swagger.dto.ResponseWrapperAdDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ResponseWrapperAdDtoMapper {
    @Mapping(source = "count", target = "count")
    @Mapping(source = "ads", target = "results")
    ResponseWrapperAdDto toDto(Integer count, List<AdDto> ads);
}
