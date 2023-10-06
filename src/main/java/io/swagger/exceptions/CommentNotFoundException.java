package io.swagger.exceptions;

public class CommentNotFoundException extends RuntimeException {
    private final Integer id;

    public CommentNotFoundException(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
