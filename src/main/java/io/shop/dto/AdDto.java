package io.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * AdDto
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-08-23T14:28:14.858562274Z[GMT]")


public class AdDto {
  @JsonProperty("author")
  private Integer author = null;

  @JsonProperty("image")
  @Valid
  private List<String> image = null;

  @JsonProperty("pk")
  private Integer pk = null;

  @JsonProperty("price")
  private Integer price = null;

  @JsonProperty("title")
  private String title = null;

  public AdDto author(Integer author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
   **/
  @Schema(description = "")
  
    public Integer getAuthor() {
    return author;
  }

  public void setAuthor(Integer author) {
    this.author = author;
  }

  public AdDto() {
  }

  public AdDto image(List<String> image) {
    this.image = image;
    return this;
  }

  public AdDto(Integer author, Integer pk, Integer price, String title) {
    this.author = author;
    this.pk = pk;
    this.price = price;
    this.title = title;
  }

  public AdDto addImageItem(String imageItem) {
    if (this.image == null) {
      this.image = new ArrayList<String>();
    }
    this.image.add(imageItem);
    return this;
  }

  /**
   * Get image
   * @return image
   **/
  @Schema(description = "")
  
    public List<String> getImage() {
    return image;
  }

  public void setImage(List<String> image) {
    this.image = image;
  }

  public AdDto pk(Integer pk) {
    this.pk = pk;
    return this;
  }

  /**
   * Get pk
   * @return pk
   **/
  @Schema(description = "")
  
    public Integer getPk() {
    return pk;
  }

  public void setPk(Integer pk) {
    this.pk = pk;
  }

  public AdDto price(Integer price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
   **/
  @Schema(description = "")
  
    public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public AdDto title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
   **/
  @Schema(description = "")
  
    public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AdDto adDto = (AdDto) o;
    return Objects.equals(this.author, adDto.author) &&
        Objects.equals(this.image, adDto.image) &&
        Objects.equals(this.pk, adDto.pk) &&
        Objects.equals(this.price, adDto.price) &&
        Objects.equals(this.title, adDto.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(author, image, pk, price, title);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AdDto {\n");
    
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    pk: ").append(toIndentedString(pk)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
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
