package io.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.shop.dto.*;
import io.shop.services.api.AdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-08-23T14:28:14.858562274Z[GMT]")
@RestController
@CrossOrigin(value = "http://localhost:3000")
@Validated
public class AdsApiController  {

    private static final Logger log = LoggerFactory.getLogger(AdsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final AdService adService;

    @org.springframework.beans.factory.annotation.Autowired
    public AdsApiController(ObjectMapper objectMapper, HttpServletRequest request, AdService adService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.adService = adService;
    }

    @Operation(summary = "addAds", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "*/*", schema = @Schema(implementation = AdDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads",
            produces = {"application/json"},
            consumes = {"multipart/form-data"},
            method = RequestMethod.POST)
    @PreAuthorize("(hasRole('USER') or hasRole('ADMIN')) and (#username == authentication.principal.username)")
    public ResponseEntity<AdDto> addAds(@Parameter(in = ParameterIn.DEFAULT, description = "",
            schema = @Schema()) @RequestPart(value = "properties", required = false) CreateAdDto properties,
                                        //schema = @Schema()) @RequestParam(value = "properties", required = false) CreateAdDto properties,
                                        @Parameter(description = "image detail") @Valid @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok(adService.addAds(properties, image));
    }

//    @RequestMapping(path = "/requestpart/employee", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
//    public ResponseEntity<Object> saveEmployee(@RequestPart Employee employee, @RequestPart MultipartFile document) {

    @Operation(summary = "addComments", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads/{ad_pk}/comments",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    @PreAuthorize("(hasRole('USER') or hasRole('ADMIN')) and (#username == authentication.principal.username)")
    public ResponseEntity<CommentDto> addComments(@Parameter(in = ParameterIn.PATH, description = "", required = true,
            schema = @Schema()) @PathVariable("ad_pk") String adPk,
                                                  @Parameter(in = ParameterIn.DEFAULT, description = "", required = true,
                                                          schema = @Schema()) @Valid @RequestBody CommentDto body) {
        return ResponseEntity.ok(adService.addComments(Integer.parseInt(adPk), body));
    }

    @Operation(summary = "deleteComments", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads/{ad_pk}/comments/{id}",
            method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteComments(@Parameter(in = ParameterIn.PATH, description = "", required = true,
            schema = @Schema()) @PathVariable("ad_pk") String adPk,
                                               @Parameter(in = ParameterIn.PATH, description = "", required = true,
                                                       schema = @Schema()) @PathVariable("id") Integer id) {
        CommentDto result = adService.deleteComments(Integer.parseInt(adPk), id);
        if (result == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
        } else {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    @Operation(summary = "getAllAds", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperAdDto.class)))})
    @RequestMapping(value = "/ads",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<ResponseWrapperAdDto> getALLAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @Operation(summary = "getFullAd", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*", schema = @Schema(implementation = FullAdDto.class))),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<FullAdDto> getAds(@Parameter(in = ParameterIn.PATH, description = "", required = true,
            schema = @Schema()) @PathVariable("id") Integer id) {
        return ResponseEntity.ok(adService.getAds(id));
    }

    @Operation(summary = "getAdsMe", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperAdDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads/me",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @PreAuthorize("(hasRole('USER') or hasRole('ADMIN')) and (#username == authentication.principal.username)")
    public ResponseEntity<ResponseWrapperAdDto> getAdsMeUsingGET(@Parameter(in = ParameterIn.QUERY, description = "",
            schema = @Schema()) @Valid @RequestParam(value = "authenticated", required = false) Boolean authenticated,
                                                                 @Parameter(in = ParameterIn.QUERY, description = "",
                                                                         schema = @Schema()) @Valid
                                                                 @RequestParam(value = "authorities[0].authority",
                                                                         required = false) String authorities0Authority,
                                                                 @Parameter(in = ParameterIn.QUERY, description = "",
                                                                         schema = @Schema()) @Valid
                                                                 @RequestParam(value = "credentials", required = false) Object credentials,
                                                                 @Parameter(in = ParameterIn.QUERY, description = "",
                                                                         schema = @Schema()) @Valid
                                                                 @RequestParam(value = "details", required = false) Object details,
                                                                 @Parameter(in = ParameterIn.QUERY, description = "",
                                                                         schema = @Schema()) @Valid
                                                                 @RequestParam(value = "principal", required = false) Object principal) {
        return ResponseEntity.ok(adService.getAdsMeUsingGET(authenticated, authorities0Authority, credentials, details, principal));
    }

    @Operation(summary = "getComments", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperCommentDto.class))),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads/{ad_pk}/comments",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<ResponseWrapperCommentDto> getComments(@Parameter(in = ParameterIn.PATH,
            description = "", required = true, schema = @Schema()) @PathVariable("ad_pk") String adPk) {
        return ResponseEntity.ok(adService.getComments(Integer.parseInt(adPk)));
    }

    @Operation(summary = "getComments", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads/{ad_pk}/comments/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<CommentDto> getComments1(@Parameter(in = ParameterIn.PATH, description = "", required = true,
            schema = @Schema()) @PathVariable("ad_pk") String adPk,
                                                   @Parameter(in = ParameterIn.PATH, description = "", required = true,
                                                           schema = @Schema()) @PathVariable("id") Integer id) {
        return ResponseEntity.ok(adService.getComments1(Integer.parseInt(adPk), id));
    }

    @Operation(summary = "removeAds", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @RequestMapping(value = "/ads/{id}",
            method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> removeAds(@Parameter(in = ParameterIn.PATH, description = "", required = true,
            schema = @Schema()) @PathVariable("id") Integer id) {
        AdDto result = adService.removeAds(id);
        if (result == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
        } else {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    @Operation(summary = "updateAds", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = AdDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PATCH)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AdDto> updateAds(@Parameter(in = ParameterIn.PATH, description = "", required = true,
            schema = @Schema()) @PathVariable("id") Integer id,
                                           @Parameter(in = ParameterIn.DEFAULT, description = "", required = true,
                                                   schema = @Schema()) @Valid @RequestBody CreateAdDto body) {
        return ResponseEntity.ok(adService.updateAds(id, body));
    }

    @Operation(summary = "updateComments", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @RequestMapping(value = "/ads/{ad_pk}/comments/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PATCH)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CommentDto> updateComments(@Parameter(in = ParameterIn.PATH, description = "", required = true,
            schema = @Schema()) @PathVariable("ad_pk") String adPk,
                                                     @Parameter(in = ParameterIn.PATH, description = "", required = true,
                                                             schema = @Schema()) @PathVariable("id") Integer id,
                                                     @Parameter(in = ParameterIn.DEFAULT, description = "", required = true,
                                                             schema = @Schema()) @Valid @RequestBody CommentDto body) {
        return ResponseEntity.ok(adService.updateComments(Integer.parseInt(adPk), id, body));
    }
}
