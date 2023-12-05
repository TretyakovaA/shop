package io.shop.services.impl;


import io.shop.dto.*;
import io.shop.model.Ad;
import io.shop.model.Comment;
import io.shop.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Constants {
    public static final Boolean TRUE = Boolean.TRUE;
    public static final Boolean FALSE = Boolean.FALSE;
    public static final Integer ID1 = 1;
    public static final Integer ID2 = 2;

    public static final String LAST_NAME_1 = "LAST_NAME_1";
    public static final String LAST_NAME_2 = "LAST_NAME_2";

    public static final String FIRST_NAME_1 = "FIRST_NAME_1";
    public static final String FIRST_NAME_2 = "FIRST_NAME_2";
    public static final String PHONE_1 = "PHONE_1";
    public static final String PHONE_2 = "PHONE_2";

    public static final String EMAIL_1 = "EMAIL_1";
    public static final String EMAIL_2 = "EMAIL_2";

    public static final String CITY_1 = "CITY_1";
    public static final String CITY_2 = "CITY_2";

    public static final String IMAGE_1 = "IMAGE_1";
    public static final String IMAGE_2 = "IMAGE_2";

    public static final LocalDateTime REG_DATE_1 = LocalDateTime.parse("2023-10-01T12:00:00.00000");
    public static final LocalDateTime REG_DATE_2 = LocalDateTime.parse("2024-10-01T12:00:00.00000");

    public static final String USERNAME_1 = "USERNAME_1";
    public static final String USERNAME_2 = "USERNAME_2";

    public static final String PASSWORD_1 = "PASSWORD_1";
    public static final String PASSWORD_2 = "PASSWORD_2";

    public static final String NEW_PASSWORD_1 = "NEW_PASSWORD_1";
    public static final String NEW_PASSWORD_2 = "NEW_PASSWORD_2";

    public static final RoleEnum ROLE_ENUM_1 = RoleEnum.USER;

    public static final RoleEnum ROLE_ENUM_2 = RoleEnum.ADMIN;

    public static final String TITLE_1 = "TITLE_1";
    public static final String TITLE_2 = "TITLE_2";

    public static final String DESCRIPTION_1 = "DESCRIPTION_1";
    public static final String DESCRIPTION_2 = "DESCRIPTION_2";
    public static final Integer PRICE_1 = 10000;
    public static final Integer PRICE_2 = 20000;

    public static final String TEXT_AD_1 = "TEXT_AD_1";
    public static final String TEXT_AD_2 = "TEXT_AD_2";

    public static final String TEXT_COMMENT_1 = "TEXT_COMMENT_1";
    public static final String TEXT_COMMENT_2 = "TEXT_COMMENT_2";

    public static final LocalDateTime CREATED_AT_1 = LocalDateTime.parse("2023-10-01T12:00:00.00000");
    public static final LocalDateTime CREATED_AT_2 = LocalDateTime.parse("2024-10-01T12:00:00.00000");

    public static final String PATH_1 = "PATH_1";
    public static final String PATH_2 = "PATH_2";

    public static final User USER_1 =  new User (ID1, LAST_NAME_1, FIRST_NAME_1, PHONE_1,
            EMAIL_1, CITY_1, IMAGE_1, REG_DATE_1, PASSWORD_1, USERNAME_1, ROLE_ENUM_1);
    public static final User USER_2 =  new User (ID2, LAST_NAME_2, FIRST_NAME_2, PHONE_2,
            EMAIL_2, CITY_2, IMAGE_2, REG_DATE_2, PASSWORD_2, USERNAME_2, ROLE_ENUM_2);

    public static final UserDto USER_DTO_1 = new UserDto(EMAIL_1, FIRST_NAME_1, ID1,
            LAST_NAME_1, PHONE_1, REG_DATE_1, CITY_1, IMAGE_1);
    public static final UserDto USER_DTO_2 = new UserDto(EMAIL_2, FIRST_NAME_2, ID2,
            LAST_NAME_2, PHONE_2, REG_DATE_2, CITY_2, IMAGE_2);

    public static final LoginReqDto LOGIN_REQ_DTO_1 = new LoginReqDto(PASSWORD_1, USERNAME_1);
    public static final LoginReqDto LOGIN_REQ_DTO_2 = new LoginReqDto(PASSWORD_2, USERNAME_2);

    public static final NewPasswordDto NEW_PASSWORD_DTO_1 = new NewPasswordDto(PASSWORD_1, NEW_PASSWORD_1);
    public static final NewPasswordDto NEW_PASSWORD_DTO_2 = new NewPasswordDto(PASSWORD_2, NEW_PASSWORD_2);

    public static final RegisterReqDto REGISTER_REQ_DTO_1 = new RegisterReqDto(USERNAME_1, PASSWORD_1, FIRST_NAME_1,
            LAST_NAME_1, PHONE_1, ROLE_ENUM_1);
    public static final RegisterReqDto REGISTER_REQ_DTO_2 = new RegisterReqDto(USERNAME_2, PASSWORD_2, FIRST_NAME_2,
            LAST_NAME_2, PHONE_2, ROLE_ENUM_2);

    public static final Ad AD_1 =  new Ad (ID1, TITLE_1, DESCRIPTION_1, PRICE_1, USER_1, Arrays.asList(
            new Comment (ID1, TEXT_COMMENT_1, USER_1,
                    CREATED_AT_1, null)
    ));
    public static final Ad AD_2 =  new Ad (ID2, TITLE_2, DESCRIPTION_2, PRICE_2, USER_2, Arrays.asList(
            new Comment (ID1, TEXT_COMMENT_2, USER_2,
                    CREATED_AT_2, null)
    ));

    public static final AdDto AD_DTO_1 = new AdDto(USER_1.getId(), ID1, PRICE_1, TITLE_1);
    public static final AdDto AD_DTO_2 = new AdDto(USER_2.getId(), ID2, PRICE_2, TITLE_2);

    public static final ResponseWrapperAdDto RESPONSE_WRAPPER_AD_DTO_1 = new ResponseWrapperAdDto(1,
            Arrays.asList(AD_DTO_1));
    public static final ResponseWrapperAdDto RESPONSE_WRAPPER_AD_DTO_2 = new ResponseWrapperAdDto(1,
            Arrays.asList(AD_DTO_2));

    public static final CreateAdDto CREATE_AD_DTO_1 = new CreateAdDto(DESCRIPTION_1, PRICE_1, TITLE_1);
    public static final CreateAdDto CREATE_AD_DTO_2 = new CreateAdDto(DESCRIPTION_2, PRICE_2, TITLE_2);

    public static final AdBodyDto AD_BODY_DTO_1 = new AdBodyDto(CREATE_AD_DTO_1, null);
    public static final AdBodyDto AD_BODY_DTO_2 = new AdBodyDto(CREATE_AD_DTO_2, null);

    public static final FullAdDto FULL_AD_DTO_1 = new FullAdDto(FIRST_NAME_1, LAST_NAME_1,
            DESCRIPTION_1, EMAIL_1, null, PHONE_1, ID1, PRICE_1, TITLE_1);
    public static final FullAdDto FULL_AD_DTO_2 = new FullAdDto(FIRST_NAME_2, LAST_NAME_2,
            DESCRIPTION_2, EMAIL_2, null, PHONE_2, ID2, PRICE_2, TITLE_2);

    public static final Comment COMMENT_1 = new Comment (ID1, TEXT_COMMENT_1, USER_1,
            CREATED_AT_1, AD_1);
    public static final Comment COMMENT_2 = new Comment (ID2, TEXT_COMMENT_2, USER_2,
            CREATED_AT_2, AD_2);

    public static final CommentDto COMMENT_DTO_1 = new CommentDto (USER_1.getId(), CREATED_AT_1, ID1, TEXT_COMMENT_1);
    public static final CommentDto COMMENT_DTO_2 = new CommentDto (USER_2.getId(), CREATED_AT_2, ID2, TEXT_COMMENT_2);

    public static final ResponseWrapperCommentDto RESPONSE_WRAPPER_COMMENT_DTO_1 = new ResponseWrapperCommentDto(1,
            Arrays.asList(COMMENT_DTO_1));
    public static final ResponseWrapperCommentDto RESPONSE_WRAPPER_COMMENT_DTO_2 = new ResponseWrapperCommentDto(1,
            Arrays.asList(COMMENT_DTO_2));
}
