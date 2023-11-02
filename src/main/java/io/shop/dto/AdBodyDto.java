package io.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.shop.model.StoredImage;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * AdBodyDto
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-08-23T14:28:14.858562274Z[GMT]")


public class AdBodyDto {
  @JsonProperty("properties")
  private CreateAdDto properties = null;

  @JsonProperty("image")
  private StoredImage storedImage = null;

  public AdBodyDto() {
  }

  public AdBodyDto properties(CreateAdDto properties) {
    this.properties = properties;
    return this;
  }

  public AdBodyDto(CreateAdDto properties, StoredImage storedImage) {
    this.properties = properties;
    this.storedImage = storedImage;
  }

  /**
   * Get properties
   * @return properties
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public CreateAdDto getProperties() {
    return properties;
  }

  public void setProperties(CreateAdDto properties) {
    this.properties = properties;
  }

  public AdBodyDto image(StoredImage storedImage) {
    this.storedImage = storedImage;
    return this;
  }

  /**
   * Get storedImage
   * @return storedImage
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public StoredImage getImage() {
    return storedImage;
  }

  public void setImage(StoredImage storedImage) {
    this.storedImage = storedImage;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AdBodyDto adBodyDto = (AdBodyDto) o;
    return Objects.equals(this.properties, adBodyDto.properties) &&
        Objects.equals(this.storedImage, adBodyDto.storedImage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(properties, storedImage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AdBodyDto {\n");
    
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    storedImage: ").append(toIndentedString(storedImage)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
