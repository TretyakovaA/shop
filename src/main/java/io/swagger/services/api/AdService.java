package io.swagger.services.api;

import io.swagger.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface AdService {
    AdDto addAds(CreateAdDto properties, MultipartFile image);

    CommentDto addComments(Integer adPk, CommentDto body);

    CommentDto deleteComments(Integer adPk, Integer id);

    ResponseWrapperAdDto getAllAds();

    FullAdDto getAds(Integer id);

    ResponseWrapperAdDto getAdsMeUsingGET(Boolean authenticated, String authorities0Authority, Object credentials, Object details, Object principal);

    ResponseWrapperCommentDto getComments(Integer adPk);

    CommentDto getComments1(Integer adPk, Integer id);

    AdDto removeAds(Integer id);

    AdDto updateAds(Integer id, CreateAdDto body);

    CommentDto updateComments(Integer adPk, Integer id, CommentDto body);
}
