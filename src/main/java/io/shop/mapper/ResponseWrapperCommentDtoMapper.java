package io.shop.mapper;

import io.shop.dto.CommentDto;
import io.shop.dto.ResponseWrapperCommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ResponseWrapperCommentDtoMapper {
    @Mapping(source = "count", target = "count")
    @Mapping(source = "comments", target = "results")
    ResponseWrapperCommentDto toDto(Integer count, List<CommentDto> comments);
}
