package io.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * UserDto
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-08-23T14:28:14.858562274Z[GMT]")


public class UserDto {
  @JsonProperty("email")
  private String email = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("phone")
  private String phone = null;

  @JsonProperty("regDate")
  private LocalDateTime regDate = null;

  @JsonProperty("city")
  private String city = null;

  @JsonProperty("image")
  private String image = null;

  public UserDto email(String email) {
    this.email = email;
    return this;
  }

  public UserDto(String email, String firstName, Integer id, String lastName, String phone, LocalDateTime regDate, String city, String image) {
    this.email = email;
    this.firstName = firstName;
    this.id = id;
    this.lastName = lastName;
    this.phone = phone;
    this.regDate = regDate;
    this.city = city;
    this.image = image;
  }

  /**
   * Get email
   * @return email
   **/
  @Schema(description = "")
  
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserDto firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
   **/
  @Schema(description = "")
  
    public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public UserDto id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(description = "")
  
    public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public UserDto lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
   **/
  @Schema(description = "")
  
    public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public UserDto phone(String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * Get phone
   * @return phone
   **/
  @Schema(description = "")
  
    public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public UserDto regDate(LocalDateTime regDate) {
    this.regDate = regDate;
    return this;
  }

  /**
   * Get regDate
   * @return regDate
   **/
  @Schema(description = "")
  
    public LocalDateTime getRegDate() {
    return regDate;
  }

  public void setRegDate(LocalDateTime regDate) {
    this.regDate = regDate;
  }

  public UserDto city(String city) {
    this.city = city;
    return this;
  }

  public UserDto() {
  }

  /**
   * Get city
   * @return city
   **/
  @Schema(description = "")
  
    public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public UserDto image(String image) {
    this.image = image;
    return this;
  }

  /**
   * Get image
   * @return image
   **/
  @Schema(description = "")
  
    public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDto userDto = (UserDto) o;
    return Objects.equals(this.id, userDto.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserDto {\n");
    
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
    sb.append("    regDate: ").append(toIndentedString(regDate)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
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
