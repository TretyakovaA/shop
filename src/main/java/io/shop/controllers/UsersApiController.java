package io.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.shop.dto.NewPasswordDto;
import io.shop.dto.UserDto;
import io.shop.services.api.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-08-23T14:28:14.858562274Z[GMT]")
@RestController
@CrossOrigin(value = "http://localhost:3000")
@Validated
public class UsersApiController {

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

    @Operation(summary = "getUser", description = "", tags = {"Пользователи"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/users/me",
            produces = {"application/json"},
            method = RequestMethod.GET)
   //@PreAuthorize("(hasRole('USER') or hasRole('ADMIN')) ")
   @PreAuthorize("(hasRole('USER') or hasRole('ADMIN')) and (#username == authentication.principal.username)")
    public ResponseEntity<UserDto> getUser1() {
        return ResponseEntity.ok(userService.getUser());
    }

    @Operation(summary = "setPassword", description = "", tags = {"Пользователи"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = NewPasswordDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/users/set_password",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    @PreAuthorize("(hasRole('USER') or hasRole('ADMIN')) and (#username == authentication.principal.username)")
    public ResponseEntity<NewPasswordDto> setPassword(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true,
            schema = @Schema()) @Valid @RequestBody NewPasswordDto body) {
        return ResponseEntity.ok(userService.setPassword(body));
    }

    @Operation(summary = "updateUser", description = "", tags = {"Пользователи"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/users/me",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PATCH)
    @PreAuthorize("(hasRole('USER') or hasRole('ADMIN')) and (#username == authentication.principal.username)")
    public ResponseEntity<UserDto> updateUser(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true,
            schema = @Schema()) @Valid @RequestBody UserDto body) {
        return ResponseEntity.ok(userService.updateUser(body));
    }

    @Operation(summary = "updateUserImage", description = "UpdateUserImage", tags = {"Пользователи"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/users/me/image",
            produces = {"application/json"},
            consumes = {"multipart/form-data"},
            method = RequestMethod.PATCH)
    @PreAuthorize("(hasRole('USER') or hasRole('ADMIN')) and (#username == authentication.principal.username) ")
    public ResponseEntity<UserDto> updateUserImage(@Parameter(description = "image detail")
                                               @Valid @RequestPart("image") MultipartFile image) throws IOException {
        return ResponseEntity.ok(userService.updateUserImage(image));
    }

}
