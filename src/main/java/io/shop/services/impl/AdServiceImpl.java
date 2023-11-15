package io.shop.services.impl;

import io.shop.dto.*;
import io.shop.exceptions.AdNotFoundException;
import io.shop.exceptions.CommentNotFoundException;
import io.shop.exceptions.UserNotFoundException;
import io.shop.mapper.*;
import io.shop.model.StoredImage;
import io.shop.model.Ad;
import io.shop.model.Comment;
import io.shop.model.User;
import io.shop.repository.AdRepository;
import io.shop.repository.CommentRepository;
import io.shop.repository.StoredImageRepository;
import io.shop.repository.UserRepository;
import io.shop.services.api.AdService;
import io.shop.services.api.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final CreateAdDtoMapper createAdDtoMapper;

    private final ImageService imageService;
    private final StoredImageRepository storedImageRepository;

    private final AdDtoMapper adDtoMapper;
    Logger logger = LoggerFactory.getLogger(AdService.class);

    private final CommentDtoMapper commentDtoMapper;
    private final CommentRepository commentRepository;
    private final ResponseWrapperAdDtoMapper responseWrapperAdDtoMapper;

    private final FullAdDtoMapper fullAdDtoMapper;
    private final UserRepository userRepository;

    private final ResponseWrapperCommentDtoMapper responseWrapperCommentDtoMapper;
    public AdServiceImpl(AdRepository adRepository, CreateAdDtoMapper createAdDtoMapper, ImageService imageService, StoredImageRepository storedImageRepository, AdDtoMapper adDtoMapper, CommentDtoMapper commentDtoMapper, CommentRepository commentRepository, ResponseWrapperAdDtoMapper responseWrapperAdDtoMapper, FullAdDtoMapper fullAdDtoMapper, UserRepository userRepository, ResponseWrapperCommentDtoMapper responseWrapperCommentDtoMapper) {
        this.adRepository = adRepository;
        this.createAdDtoMapper = createAdDtoMapper;
        this.imageService = imageService;
        this.storedImageRepository = storedImageRepository;
        this.adDtoMapper = adDtoMapper;
        this.commentDtoMapper = commentDtoMapper;
        this.commentRepository = commentRepository;
        this.responseWrapperAdDtoMapper = responseWrapperAdDtoMapper;
        this.fullAdDtoMapper = fullAdDtoMapper;
        this.userRepository = userRepository;
        this.responseWrapperCommentDtoMapper = responseWrapperCommentDtoMapper;
    }

    @Override
    public AdDto addAds(CreateAdDto properties, MultipartFile image) {
        Ad ad = createAdDtoMapper.toEntity(properties);
        try {
            Path imagePath = imageService.uploadFile(image);
            if (imagePath != null) {
                StoredImage storedImage = new StoredImage();
                storedImage.setPath(imagePath.toString());
                storedImage.setAd(ad);
                storedImage.setId(storedImageRepository.save(storedImage).getId());
                List<StoredImage> storedImages = new ArrayList<>();
                storedImages.add(storedImage);
                ad.setStoredImage(storedImages);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("Объявление добавлено: " + ad.getPk());
        return adDtoMapper.toDto(adRepository.save(ad));
    }

    @Override
    public CommentDto addComments(Integer adPk, CommentDto body) {
        Comment comment = commentDtoMapper.toEntity(body);
        comment.setAd(adRepository.findById(adPk).orElseThrow(() -> {
            logger.info("Объявление с id " + adPk + " не найден");
            throw new AdNotFoundException(adPk);
        }));
        logger.info("Комментарий добавлен: " + comment.getPk());
        return commentDtoMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto deleteComments(Integer adPk, Integer id) {
        Comment comment = commentRepository.findById(id).orElseThrow(()
                -> {
            throw new CommentNotFoundException(id);
        });
        commentRepository.delete(comment);
        logger.info("Комментарий с id " + id + " удален");
        return commentDtoMapper.toDto(comment);
    }

    @Override
    public ResponseWrapperAdDto getAllAds() {
        List<Ad> ads = adRepository.findAll();
        if (ads == null) {
            return null;
        } else {
            return responseWrapperAdDtoMapper.toDto(ads.size(), adDtoMapper.toDtos(ads));
        }
    }

    @Override
    public FullAdDto getAds(Integer id) {
        Ad foundAd = adRepository.findById(id).orElseThrow(() -> {
            logger.info("Объявление с id " + id + " не найден");
            throw new AdNotFoundException(id);
        });

        logger.info("Объявление с id " + id + " найден");
        User user = userRepository.findById(foundAd.getAuthor().getId()).orElseThrow(
                () ->{
                    logger.info("Пользователь с id " + id + " не найден");
                    throw new UserNotFoundException(id);
                }
        );
        return fullAdDtoMapper.toDto(foundAd, user);
    }

    @Override
    public ResponseWrapperAdDto getAdsMeUsingGET(Boolean authenticated, String authorities0Authority,
                                                 Object credentials, Object details, Object principal) {
        return null;
    }

    @Override
    public ResponseWrapperCommentDto getComments(Integer adPk) {
        Ad foundAd = adRepository.findById(adPk).orElseThrow(() -> {
            logger.info("Объявление с id " + adPk + " не найден");
            throw new AdNotFoundException(adPk);
        });

        List<Comment> comments = foundAd.getComments();
        if (comments == null){
            return null;
        } else {
            return responseWrapperCommentDtoMapper.toDto(comments.size(), commentDtoMapper.toDtos(comments));
        }
    }

    @Override
    public CommentDto getComments1(Integer adPk, Integer id) {
        Comment foundComment = commentRepository.findById(id).orElseThrow(() -> {
            logger.info("Комментарий с id " + id + " не найден");
            throw new CommentNotFoundException(id);
        });

        logger.info("Комментарий с id " + id + " найден");
        return commentDtoMapper.toDto(foundComment);

    }

    @Override
    public AdDto removeAds(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow(()
                -> {
            throw new AdNotFoundException(id);
        });
        adRepository.delete(ad);
        logger.info("Объявление с id " + id + " удалено");
        return adDtoMapper.toDto(ad);
    }

    @Override
    public AdDto updateAds(Integer id, CreateAdDto body) {
        Ad oldAd = adRepository.findById(id).orElseThrow(()
                -> { throw new AdNotFoundException(id);});
        if (body.getPrice() != 0) {
            oldAd.setPrice(body.getPrice());
        }
        if (body.getDescription() != null){
            oldAd.setDescription(body.getDescription());
        }
        if (body.getTitle() != null) {
            oldAd.setTitle(body.getTitle());
        }
        return adDtoMapper.toDto(adRepository.save(oldAd));
    }

    @Override
    public CommentDto updateComments(Integer adPk, Integer id, CommentDto body) {
        Comment oldComment = commentRepository.findById(id).orElseThrow(()
                -> { throw new CommentNotFoundException(id);});
        if (body.getText() != null){
            oldComment.setText(body.getText());
        }
        return commentDtoMapper.toDto(commentRepository.save(oldComment));
    }
}
