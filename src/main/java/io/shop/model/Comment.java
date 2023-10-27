package io.shop.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
@Entity
@Data
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer pk;

    @Column(name = "text")
    private String text;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @ToString.Exclude
    private User author;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "ad_id")
    @ToString.Exclude
    private Ad ad;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(getPk(), comment.getPk());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPk());
    }
}
