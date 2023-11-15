package io.shop.services.impl;

import io.shop.exceptions.AdNotFoundException;
import io.shop.exceptions.CommentNotFoundException;
import io.shop.exceptions.UserNotFoundException;
import io.shop.mapper.*;
import io.shop.model.Ad;
import io.shop.model.Comment;
import io.shop.model.User;
import io.shop.repository.AdRepository;
import io.shop.repository.CommentRepository;
import io.shop.repository.UserRepository;
import io.shop.services.api.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static io.shop.services.impl.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdServiceImplTest {

    @Mock
    private AdRepository adRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdServiceImpl out;

    @Mock
    private AdBodyDtoMapper adBodyDtoMapper;

    @Mock
    private AdDtoMapper adDtoMapper;

    @Mock
    private CommentDtoMapper commentDtoMapper;

    @Mock
    private CreateAdDtoMapper createAdDtoMapper;

    @Mock
    private FullAdDtoMapper fullAdDtoMapper;

    @Mock
    private ResponseWrapperAdDtoMapper responseWrapperAdDtoMapper;

    @Mock
    private ResponseWrapperCommentDtoMapper responseWrapperCommentDtoMapper;

    @Test
    void addAds() throws IOException {
        //when(createAdDtoMapper.toDto(AD_1)).thenReturn(CREATE_AD_DTO_1);
        when(createAdDtoMapper.toEntity(CREATE_AD_DTO_1)).thenReturn(AD_1);
        when(adRepository.save(any(Ad.class))).thenReturn((AD_1));
        when(imageService.uploadFile(any())).thenReturn(null);
        when(adDtoMapper.toDto(AD_1)).thenReturn(AD_DTO_1);

        assertEquals(out.addAds(CREATE_AD_DTO_1, null), AD_DTO_1);
    }

    @Test
    void addComments() {
        when(commentDtoMapper.toEntity(COMMENT_DTO_1)).thenReturn(COMMENT_1);
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD_1));
        when(commentRepository.save(any(Comment.class))).thenReturn(COMMENT_1);
        when(commentDtoMapper.toDto(COMMENT_1)).thenReturn(COMMENT_DTO_1);

        assertEquals(out.addComments(1, COMMENT_DTO_1), COMMENT_DTO_1);
    }

    @Test
    void adNotFoundExceptionWhenAdComments (){
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(AdNotFoundException.class, () -> out.addComments(1, COMMENT_DTO_1));
    }

    @Test
    void deleteComments() {
        when(commentDtoMapper.toDto(COMMENT_1)).thenReturn(COMMENT_DTO_1);
        when(commentRepository.findById(any(Integer.class))).thenReturn(Optional.of(COMMENT_1));
        doNothing().when(commentRepository).delete(COMMENT_1);

        assertEquals(out.deleteComments(0, ID1), COMMENT_DTO_1);
    }

    @Test
    void commentNotFoundExceptionWhenDeleteComments (){
        when(commentRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> out.deleteComments(0, ID1));
    }

    @Test
    void getAllAds() {
        when(adRepository.findAll()).thenReturn(List.of(AD_1));
        when(adDtoMapper.toDtos(anyList())).thenReturn(List.of(AD_DTO_1));
        when(responseWrapperAdDtoMapper.toDto(1, List.of(AD_DTO_1))).thenReturn(RESPONSE_WRAPPER_AD_DTO_1);

        assertEquals(out.getAllAds(), RESPONSE_WRAPPER_AD_DTO_1);
    }

    @Test
    void getAds() {
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD_1));
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(USER_1));
        when(fullAdDtoMapper.toDto(any(Ad.class), any(User.class))).thenReturn(FULL_AD_DTO_1);

        assertEquals(out.getAds(ID1), FULL_AD_DTO_1);
    }

    @Test
    void adNotFoundExceptionWhenGetAds (){
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(AdNotFoundException.class, () -> out.getAds(ID1));
    }

    @Test
    void userNotFoundExceptionWhenGetAds (){
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD_1));
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> out.getAds(ID1));
    }

    @Test
    void getAdsMeUsingGET() {
    }

    @Test
    void getComments() {
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD_1));
       // when(commentDtoMapper.toDtos(AD_1.getComments())).thenReturn(List.of(COMMENT_DTO_1));
        when(commentDtoMapper.toDtos(anyList())).thenReturn(List.of(COMMENT_DTO_1));
        when(responseWrapperCommentDtoMapper.toDto(1, List.of(COMMENT_DTO_1))).thenReturn(RESPONSE_WRAPPER_COMMENT_DTO_1);

        assertEquals(out.getComments(ID1), RESPONSE_WRAPPER_COMMENT_DTO_1);
    }

    @Test
    void adNotFoundExceptionWhenGetComments (){
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(AdNotFoundException.class, () -> out.getComments(ID1));
    }

    @Test
    void getComments1() {
        when(commentRepository.findById(any(Integer.class))).thenReturn(Optional.of(COMMENT_1));
        // when(commentDtoMapper.toDtos(AD_1.getComments())).thenReturn(List.of(COMMENT_DTO_1));
        when(commentDtoMapper.toDto(any(Comment.class))).thenReturn(COMMENT_DTO_1);

        assertEquals(out.getComments1(0, ID1), COMMENT_DTO_1);
    }

    @Test
    void commentNotFoundExceptionWhenGetComment1 (){
        when(commentRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> out.getComments1(0, ID1));
    }

    @Test
    void removeAds() {
        when(adDtoMapper.toDto(AD_1)).thenReturn(AD_DTO_1);
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD_1));
        doNothing().when(adRepository).delete(AD_1);

        assertEquals(out.removeAds(ID1), AD_DTO_1);
    }

    @Test
    void adNotFoundExceptionWhenRemoveAd (){
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(AdNotFoundException.class, () -> out.removeAds(ID1));
    }

    @Test
    void updateAds() {
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD_1));
        when(adDtoMapper.toDto(AD_1)).thenReturn(AD_DTO_1);
        when(adRepository.save(any(Ad.class))).thenReturn(AD_1);

        assertEquals(out.updateAds(ID1, CREATE_AD_DTO_1), AD_DTO_1);
    }

    @Test
    void adNotFoundExceptionWhenUpdateAd (){
        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(AdNotFoundException.class, () -> out.updateAds(ID1, CREATE_AD_DTO_1));
    }

    @Test
    void updateComments() {
        when(commentRepository.findById(any(Integer.class))).thenReturn(Optional.of(COMMENT_1));
        when(commentDtoMapper.toDto(COMMENT_1)).thenReturn(COMMENT_DTO_1);
        when(commentRepository.save(any(Comment.class))).thenReturn(COMMENT_1);

        assertEquals(out.updateComments(0, ID1, COMMENT_DTO_1), COMMENT_DTO_1);
    }

    @Test
    void commentNotFoundExceptionWhenUpdateComment (){
        when(commentRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> out.updateComments(0, ID1, COMMENT_DTO_1));
    }
}