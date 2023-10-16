package io.swagger.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.controllers.interfaces.UsersApi;
import io.swagger.dto.NewPasswordDto;
import io.swagger.dto.UserDto;
import io.swagger.services.api.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-08-23T14:28:14.858562274Z[GMT]")
@RestController
@CrossOrigin(value = "http://localhost:3000")
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request, UserService userService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.userService = userService;
    }

    public ResponseEntity<UserDto> getUser1(Integer id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    public ResponseEntity<NewPasswordDto> setPassword(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true,
            schema = @Schema()) @Valid @RequestBody NewPasswordDto body) {
        return ResponseEntity.ok(userService.setPassword(body));
    }

    public ResponseEntity<UserDto> updateUser(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true,
            schema = @Schema()) @Valid @RequestBody UserDto body) {
        return ResponseEntity.ok(userService.updateUser(body));
    }

    public ResponseEntity<Void> updateUserImage(@Parameter(description = "file detail")
                                                @Valid @RequestPart("file") MultipartFile image) {
        return ResponseEntity.ok(userService.updateUserImage(image));
    }

}
