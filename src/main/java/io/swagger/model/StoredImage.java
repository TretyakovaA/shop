package io.swagger.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
public class StoredImage {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "path")
    private String path;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    public StoredImage(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoredImage)) return false;
        StoredImage storedImage = (StoredImage) o;
        return Objects.equals(getId(), storedImage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
