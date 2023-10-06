package io.swagger.model;

import lombok.Data;

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

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "ad_id")
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
