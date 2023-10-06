package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.dto.RoleEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "city")
    private String city;

    @Column(name = "image")
    private String image;

    @Column(name = "reg_date")
    private String regDate;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    List<Ad> ads;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    List<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
