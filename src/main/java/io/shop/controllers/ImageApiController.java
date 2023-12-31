package io.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.shop.services.api.AdService;
import io.shop.services.api.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-08-23T14:28:14.858562274Z[GMT]")
@RestController
@CrossOrigin(value = "http://localhost:3000")
@Validated
public class ImageApiController {

    private static final Logger log = LoggerFactory.getLogger(ImageApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    private final ImageService imageService;

    private final AdService adService;

    @org.springframework.beans.factory.annotation.Autowired
    public ImageApiController(ObjectMapper objectMapper, HttpServletRequest request, ImageService imageService, AdService adService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.imageService = imageService;
        this.adService = adService;
    }

    @Operation(summary = "updateAdsImage", description = "", tags={ "Изображения" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/octet-stream",
                    array = @ArraySchema(schema = @Schema(implementation = byte[].class)))),
            @ApiResponse(responseCode = "404", description = "Not Found") })
    //@RequestMapping(value = "/image/{id}",
    @RequestMapping(value = "/ads/{id}/image",
            produces = { "application/octet-stream" },
            consumes = { "multipart/form-data" },
            method = RequestMethod.PATCH)
    public ResponseEntity<List<byte[]>> updateImage(@Parameter(in = ParameterIn.PATH, description = "", required=true,
            schema=@Schema()) @PathVariable("id") Integer adId,
            @Parameter(description = "image detail") @Valid @RequestPart("image") MultipartFile image) throws IOException {
        return ResponseEntity.ok(imageService.updateImage(adId, image));
    }

//    @Operation(summary = "get ad image", tags={ "Изображения" })
//    @GetMapping(value = "/ads/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
//    ResponseEntity<byte[]> getAdImage(@PathVariable("name") String name) {
//        return ResponseEntity.ok(adService.getImageById(name));
//    }

}
