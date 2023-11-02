package io.shop.services.impl;

import io.shop.mapper.*;
import io.shop.model.Ad;
import io.shop.repository.AdRepository;
import io.shop.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.shop.services.impl.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdServiceImplTest {

    @Mock
    private AdRepository adRepository;

    @Mock
    private CommentRepository commentRepository;

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
    void addAds() {
//        when(reportsDtoMapper.toDto(REPORTS_1)).thenReturn(REPORTS_DTO_1);
//        when(reportsDtoMapper.toEntity(REPORTS_DTO_1)).thenReturn(REPORTS_1);
//        when(reportsRepository.save(any(Reports.class))).thenReturn(REPORTS_1);
//
//        assertEquals(out.addReports(REPORTS_DTO_1), REPORTS_DTO_1);
         when(createAdDtoMapper.toDto(AD_1)).thenReturn(CREATE_AD_DTO_1);
         when (createAdDtoMapper.toEntity(CREATE_AD_DTO_1)).thenReturn(AD_1);
         when(adRepository.save(any(Ad.class))).thenReturn((AD_1));
         when(adDtoMapper.toDto(AD_1)).thenReturn(AD_DTO_1);

         assertEquals(out.addAds(CREATE_AD_DTO_1, null), AD_DTO_1);
    }

    @Test
    void addComments() {
    }

    @Test
    void deleteComments() {
    }

    @Test
    void getAllAds() {
    }

    @Test
    void getAds() {
    }

    @Test
    void getAdsMeUsingGET() {
    }

    @Test
    void getComments() {
    }

    @Test
    void getComments1() {
    }

    @Test
    void removeAds() {
    }

    @Test
    void updateAds() {
    }

    @Test
    void updateComments() {
    }
}