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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        //cоздать объявление может только зарегистрированный пользователь или админ
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(()
                -> {
            throw new UserNotFoundException("Пользователь с именем " + auth.getName() + " не найден в базе");
        });
        ad.setAuthor(user);

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

        //добавить комментарий может только зарегистрированный пользователь или админ
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(()
                -> {
            throw new UserNotFoundException("Пользователь с именем " + auth.getName() + " не найден в базе");
        });
        comment.setAuthor(user);

        logger.info("Комментарий добавлен: " + comment.getPk());
        return commentDtoMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto deleteComments(Integer adPk, Integer id) {
        //удалить комментарий может только зарегистрированный пользователь или админ
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(()
                -> {
            throw new UserNotFoundException("Пользователь с именем " + auth.getName() + " не найден в базе");
        });

        Comment comment = commentRepository.findById(id).orElseThrow(()
                -> {
            throw new CommentNotFoundException(id);
        });

        // пользователь может удалить только свой комментарий
        // админ может удалить любой комментарий
        if (comment.getAuthor().getId() == user.getId() ||
                user.getRole().equals(RoleEnum.ADMIN)) {
            commentRepository.delete(comment);
            logger.info("Комментарий с id " + id + " удален");
            return commentDtoMapper.toDto(comment);
        } else {
            logger.info("Комментарий с id " + id + " не удален, нет прав");
            return null;
        }
    }

    @Override
    public ResponseWrapperAdDto getAllAds() {
        // любые пользователи видят объявления
        List<Ad> ads = adRepository.findAll();
        if (ads == null) {
            return null;
        } else {
            return responseWrapperAdDtoMapper.toDto(ads.size(), adDtoMapper.toDtos(ads));
        }
    }

    @Override
    public FullAdDto getAds(Integer id) {
        //любые пользователи видят объявление
        Ad foundAd = adRepository.findById(id).orElseThrow(() -> {
            logger.info("Объявление с id " + id + " не найден");
            throw new AdNotFoundException(id);
        });

        logger.info("Объявление с id " + id + " найден");
        User user = userRepository.findById(foundAd.getAuthor().getId()).orElseThrow(
                () -> {
                    logger.info("Пользователь с id " + id + " не найден");
                    throw new UserNotFoundException(id);
                }
        );
        return fullAdDtoMapper.toDto(foundAd, user);
    }

    @Override
    public ResponseWrapperAdDto getAdsMeUsingGET(Boolean authenticated, String authorities0Authority,
                                                 Object credentials, Object details, Object principal) {
        //в личном кабинете пользователь видит только свои объявления
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(()
                -> {
            throw new UserNotFoundException("Пользователь с именем " + auth.getName() + " не найден в базе");
        });

        List<Ad> myAds = adRepository.findByAuthor(user);
        //List<Ad> myAds = user.getAds();

        return responseWrapperAdDtoMapper.toDto(myAds.size(), adDtoMapper.toDtos(myAds));
    }

    @Override
    public ResponseWrapperCommentDto getComments(Integer adPk) {
        // видимо на фронте зашито, что незарегистрированный пользователь не может видеть комментарии?
        Ad foundAd = adRepository.findById(adPk).orElseThrow(() -> {
            logger.info("Объявление с id " + adPk + " не найден");
            throw new AdNotFoundException(adPk);
        });

        List<Comment> comments = foundAd.getComments();
        if (comments == null) {
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

        //удалить объявление может только зарегистрированный пользователь или админ
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(()
                -> {
            throw new UserNotFoundException("Пользователь с именем " + auth.getName() + " не найден в базе");
        });

        // пользователь может удалить только свое объявление
        // админ может удалить любое объявление
        if (ad.getAuthor().getId() == user.getId() ||
                user.getRole().equals(RoleEnum.ADMIN)) {
            adRepository.delete(ad);
            logger.info("Объявление с id " + id + " удалено");
            return adDtoMapper.toDto(ad);
        } else {
            logger.info("Объявление с id " + id + " не удалено, нет прав");
            return null;
        }
    }

    @Override
    public AdDto updateAds(Integer id, CreateAdDto body) {
        Ad oldAd = adRepository.findById(id).orElseThrow(()
                -> {
            throw new AdNotFoundException(id);
        });

        //изменить объявление может только зарегистрированный пользователь
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(()
                -> {
            throw new UserNotFoundException("Пользователь с именем " + auth.getName() + " не найден в базе");
        });

        //пользователь может отредактировать только свое объявление
        if (oldAd.getAuthor().getId() == user.getId()) {
            if (body.getPrice() != 0) {
                oldAd.setPrice(body.getPrice());
            }
            if (body.getDescription() != null) {
                oldAd.setDescription(body.getDescription());
            }
            if (body.getTitle() != null) {
                oldAd.setTitle(body.getTitle());
            }
            return adDtoMapper.toDto(adRepository.save(oldAd));
        } else {
            return null;
        }
    }

    @Override
    public CommentDto updateComments(Integer adPk, Integer id, CommentDto body) {
        Comment oldComment = commentRepository.findById(id).orElseThrow(()
                -> {
            throw new CommentNotFoundException(id);
        });

        //комментарий может изменить только зарегистрированный пользователь
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(()
                -> {
            throw new UserNotFoundException("Пользователь с именем " + auth.getName() + " не найден в базе");
        });

        //пользователь может изменить только свой комментарий
        if (oldComment.getAuthor().getId() == user.getId()) {
            if (body.getText() != null) {
                oldComment.setText(body.getText());
            }
            return commentDtoMapper.toDto(commentRepository.save(oldComment));
        } else {
            return null;
        }
    }
}
