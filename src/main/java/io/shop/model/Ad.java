package io.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
@Entity
@Data
@Table(name = "ads")
public class Ad {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer pk;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ad")
    @ToString.Exclude
    private List<StoredImage> storedImage;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @ToString.Exclude
    private User author;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "ad")
    @ToString.Exclude
    List<Comment> comments;

    public Ad() {
    }

    public Ad(Integer pk, String title, String description, Integer price, User author) {
        this.pk = pk;
        this.title = title;
        this.description = description;
        this.price = price;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ad)) return false;
        Ad ad = (Ad) o;
        return Objects.equals(getPk(), ad.getPk());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPk());
    }
}
