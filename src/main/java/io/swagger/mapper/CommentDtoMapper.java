package io.swagger.mapper;

import io.swagger.dto.CommentDto;
import io.swagger.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentDtoMapper {
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "ad", ignore = true)
    @Mapping(target ="author.id", source = "author")
    @Mapping(target="createdAt", source="createdAt",
            dateFormat="dd-MM-yyyy HH:mm:ss")
    Comment toEntity(CommentDto adsComment);

    @Mapping(target ="author", source = "author.id")
    @Mapping(target="createdAt", source = "createdAt",
            dateFormat = "dd-MM-yyyy HH:mm:ss")
    CommentDto toDto(Comment adComment);

    List<CommentDto> toDtos(List<Comment> adCommentList);
}
