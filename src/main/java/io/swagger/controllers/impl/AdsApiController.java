package io.swagger.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.controllers.interfaces.AdsApi;
import io.swagger.dto.*;
import io.swagger.services.api.AdService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-08-23T14:28:14.858562274Z[GMT]")
@RestController
@CrossOrigin(value = "http://localhost:3000")
public class AdsApiController implements AdsApi {

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

    public ResponseEntity<AdDto> addAds(@Parameter(in = ParameterIn.DEFAULT, description = "",
            schema=@Schema()) @RequestParam(value="properties", required=false) CreateAdDto properties,
                                        @Parameter(description = "file detail") @Valid @RequestPart("file") MultipartFile image) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return ResponseEntity.ok(adService.addAds(properties, image));
           }

        return new ResponseEntity<AdDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<CommentDto> addComments(@Parameter(in = ParameterIn.PATH, description = "", required=true,
            schema=@Schema()) @PathVariable("ad_pk") String adPk,
                                                  @Parameter(in = ParameterIn.DEFAULT, description = "", required=true,
                                                          schema=@Schema()) @Valid @RequestBody CommentDto body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return ResponseEntity.ok(adService.addComments(Integer.parseInt(adPk), body));
            }

        return new ResponseEntity<CommentDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteComments(@Parameter(in = ParameterIn.PATH, description = "", required=true,
            schema=@Schema()) @PathVariable("ad_pk") String adPk,
                                               @Parameter(in = ParameterIn.PATH, description = "", required=true,
                                                       schema=@Schema()) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");
        CommentDto result = adService.deleteComments(Integer.parseInt(adPk), id);
        if (result == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
        } else {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }

    }

    public ResponseEntity getALLAds() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return ResponseEntity.ok(adService.getAllAds());
            }

        return new ResponseEntity<ResponseWrapperAdDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<FullAdDto> getAds(@Parameter(in = ParameterIn.PATH, description = "", required=true,
            schema=@Schema()) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return ResponseEntity.ok(adService.getAds(id));
             }

        return new ResponseEntity<FullAdDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ResponseWrapperAdDto> getAdsMeUsingGET(@Parameter(in = ParameterIn.QUERY, description = "" ,
            schema=@Schema()) @Valid @RequestParam(value = "authenticated", required = false) Boolean authenticated,
                                                                 @Parameter(in = ParameterIn.QUERY, description = "" ,
                                                                          schema=@Schema()) @Valid
                                                                  @RequestParam(value = "authorities[0].authority",
                                                                          required = false) String authorities0Authority,
                                                                 @Parameter(in = ParameterIn.QUERY, description = "" ,
                                                                          schema=@Schema()) @Valid
                                                                  @RequestParam(value = "credentials", required = false) Object credentials,
                                                                 @Parameter(in = ParameterIn.QUERY, description = "" ,
                                                                          schema=@Schema()) @Valid
                                                                  @RequestParam(value = "details", required = false) Object details,
                                                                 @Parameter(in = ParameterIn.QUERY, description = "" ,
                                                                          schema=@Schema()) @Valid
                                                                  @RequestParam(value = "principal", required = false) Object principal) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return ResponseEntity.ok(adService.getAdsMeUsingGET(authenticated, authorities0Authority, credentials, details, principal));
             }

        return new ResponseEntity<ResponseWrapperAdDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ResponseWrapperCommentDto> getComments(@Parameter(in = ParameterIn.PATH,
            description = "", required=true, schema=@Schema()) @PathVariable("ad_pk") String adPk) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return ResponseEntity.ok(adService.getComments(Integer.parseInt(adPk)));
           }

        return new ResponseEntity<ResponseWrapperCommentDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<CommentDto> getComments1(@Parameter(in = ParameterIn.PATH, description = "", required=true,
            schema=@Schema()) @PathVariable("ad_pk") String adPk,
                                                   @Parameter(in = ParameterIn.PATH, description = "", required=true,
                                                           schema=@Schema()) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return ResponseEntity.ok(adService.getComments1(Integer.parseInt(adPk), id));
             }

        return new ResponseEntity<CommentDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> removeAds(@Parameter(in = ParameterIn.PATH, description = "", required=true,
            schema=@Schema()) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");
        AdDto result = adService.removeAds(id);
        if (result == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
        } else {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    public ResponseEntity<AdDto> updateAds(@Parameter(in = ParameterIn.PATH, description = "", required=true,
            schema=@Schema()) @PathVariable("id") Integer id,
                                           @Parameter(in = ParameterIn.DEFAULT, description = "", required=true,
                                                    schema=@Schema()) @Valid @RequestBody CreateAdDto body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return ResponseEntity.ok(adService.updateAds(id, body));
        }

        return new ResponseEntity<AdDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<CommentDto> updateComments(@Parameter(in = ParameterIn.PATH, description = "", required=true,
            schema=@Schema()) @PathVariable("ad_pk") String adPk,
                                                     @Parameter(in = ParameterIn.PATH, description = "", required=true,
                                                             schema=@Schema()) @PathVariable("id") Integer id,
                                                     @Parameter(in = ParameterIn.DEFAULT, description = "", required=true,
                                                             schema=@Schema()) @Valid @RequestBody CommentDto body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return ResponseEntity.ok(adService.updateComments(Integer.parseInt(adPk), id, body));
           }

        return new ResponseEntity<CommentDto>(HttpStatus.NOT_IMPLEMENTED);
    }
}
