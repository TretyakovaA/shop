package io.swagger.mapper;

import io.swagger.dto.CommentDto;
import io.swagger.dto.ResponseWrapperCommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ResponseWrapperCommentDtoMapper {
    @Mapping(source = "count", target = "count")
    @Mapping(source = "comments", target = "results")
    ResponseWrapperCommentDto toDto(Integer count, List<CommentDto> comments);
}
