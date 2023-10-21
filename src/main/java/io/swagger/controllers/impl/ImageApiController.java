package io.swagger.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.controllers.interfaces.ImageApi;
import io.swagger.services.api.ImageService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-08-23T14:28:14.858562274Z[GMT]")
@RestController
@CrossOrigin(value = "http://localhost:3000")
public class ImageApiController implements ImageApi {

    private static final Logger log = LoggerFactory.getLogger(ImageApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    private final ImageService imageService;

    @org.springframework.beans.factory.annotation.Autowired
    public ImageApiController(ObjectMapper objectMapper, HttpServletRequest request, ImageService imageService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.imageService = imageService;
    }

    public ResponseEntity<List<byte[]>> updateImage(@Parameter(in = ParameterIn.PATH, description = "", required=true,
            schema=@Schema()) @PathVariable("id") Integer id,
            @Parameter(description = "file detail") @Valid @RequestPart("file") MultipartFile image) throws IOException {
        return ResponseEntity.ok(imageService.updateImage(id, image));
    }

}
